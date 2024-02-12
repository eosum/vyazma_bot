package main.commands;

import main.constantdata.EmployeeOrStudentButtons;
import main.constantdata.StartEmployeeButtonsName;
import main.constantdata.StartStudentButtonsName;
import main.core.User;
import main.database.DatabaseManager;
import main.keyboards.TwoButtonsRowKeyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

import static main.utils.CommandsUtils.getErrorMessage;

public class Start implements Command {
    private User user;
    private int iterationNumber = 0;
    @Override
    public Object execute(Update event) {
        if (user == null) user = setUserSettings(event);
        UserCommandsStore.lastUserCommand.put(user.userId(), this);
        iterationNumber++;
        return switch(iterationNumber) {
            case 1 -> whichRights(event);
            case 2 -> sendOptions(event);
            default -> null;
        };
    }

    private SendMessage whichRights(Update event) {
        SendMessage message = new SendMessage(event.getMessage().getChatId().toString(), "Привет");
        message.setReplyMarkup(new TwoButtonsRowKeyboard(EmployeeOrStudentButtons.getButtonsNames()).getMarkup());

        return message;
    }

    private Object sendOptions(Update event) {
        Long chatId = event.getCallbackQuery().getMessage().getChatId();
        int messageId = event.getCallbackQuery().getMessage().getMessageId();
        String role = event.getCallbackQuery().getData();

        String chosenRole = DatabaseManager.isStudentOrEmployee(user.userId());

        if (!role.equals(chosenRole)) return getErrorMessage(chatId);

        ArrayList<String> buttons;

        if (chosenRole.equals("Студент")) buttons = StartStudentButtonsName.getButtonsNames();
        else buttons = StartEmployeeButtonsName.getButtonsNames();


        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder().chatId(user.chatId()).messageId(messageId).build();
        newKb.setReplyMarkup(new TwoButtonsRowKeyboard(buttons).getMarkup());

        UserCommandsStore.lastUserCommand.remove(user.userId());
        return newKb;
    }
}
