package main.commands.employee;

import main.commands.common.Command;
import main.commands.common.UserCommandsStore;
import main.core.Application;
import main.database.DatabaseManager;
import main.keyboards.Keyboard;
import main.services.ValidationService;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import static main.taskshandler.TasksHandler.massMailing;
import static main.utils.CommandsUtils.getRightsErrorMessage;

public class GuestApproving implements Command {
    private int iteration = 0;
    private ArrayList<Application> applications;

    @Override
    public Object execute(Update event) {
        iteration++;
        return switch (iteration) {
            case 1 -> getAllApplications(event);
            case 2 -> confirmGuests(event);
            default -> null;
        };
    }

    private SendMessage getAllApplications(Update event) {
        Long chatId = event.getCallbackQuery().getMessage().getChatId();
        Long userId = event.getCallbackQuery().getFrom().getId();
        String role = "заведующий общежитием";

        UserCommandsStore.lastUserCommand.put(userId, this);

        if (!ValidationService.hasRights(userId, role)) {
            UserCommandsStore.lastUserCommand.remove(userId);
            return getRightsErrorMessage(chatId);
        }

        applications = DatabaseManager.getApplicationWaitingForConfirmation();

        StringBuilder str = new StringBuilder();
        for (Application i : applications) {
            str.append(i);
            str.append("\n\n");
        }

        if (str.isEmpty()) {
            UserCommandsStore.lastUserCommand.remove(userId);
            return new SendMessage(String.valueOf(chatId), "Заявок нет");
        }

        SendMessage message = new SendMessage(String.valueOf(chatId), str.toString());

        message.setReplyMarkup(new Keyboard(new ArrayList<>(List.of("Одобрить"))).getMarkup());

        return message;
    }

    private SendMessage confirmGuests(Update event) {
        boolean isConfirmationSuccess = DatabaseManager.confirmApplications();
        Long chatId = event.getCallbackQuery().getMessage().getChatId();

        if (!isConfirmationSuccess) return CommandsUtils.getErrorMessage(chatId);

        ArrayList<SendMessage> messages = new ArrayList<>();
        for (Application i: applications) {
            Long studentChatId = DatabaseManager.chatIdByCardNumber(i.cardNumber());
            messages.add(new SendMessage(String.valueOf(studentChatId), "Ваше заявление на провод гостя одобрено!"));
        }
        UserCommandsStore.lastUserCommand.remove(chatId);

        massMailing(messages);
        return new SendMessage(String.valueOf(chatId), "Подтверждение прошло успешно");
    }
}
