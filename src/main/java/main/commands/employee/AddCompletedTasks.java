package main.commands.employee;

import main.commands.common.Command;
import main.commands.common.UserCommandsStore;
import main.core.User;
import main.database.DatabaseManager;
import main.services.ValidationService;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static main.constantdata.GuestInvitationConstData.FORMAT_MESSAGE;

public class AddCompletedTasks implements Command {
    private User user;
    int iteration = 0;
    @Override
    public SendMessage execute(Update event) {
        if (user == null) user = setUserSettings(event);
        if (ValidationService.hasRights(user.userId(), "заведующий общежитием")) return CommandsUtils.getRightsErrorMessage(user.userId());
        iteration++;
        return switch(iteration) {
            case 1 -> getCompletedTasks();
            case 2 -> addCompletedTasks(event);
            default ->  null;
        };
    }

    private SendMessage getCompletedTasks() {
        UserCommandsStore.lastUserCommand.put(user.userId(), this);
        return new SendMessage(user.chatId(), "Введите данные в формате:\nНомер телефона сотрудника\nID выполненных задач через запятую\n\nПример:\n+79221334545\n5, 7, 10, 20\n\n+79865321212\n1, 2, 9");
    }

    private SendMessage addCompletedTasks(Update event) {
        String[] tasksByEmployee = event.getMessage().getText().split("\n\n");

        int cnt = 1;
        StringBuilder str = new StringBuilder();
        StringBuilder errorInsert = new StringBuilder();
        for (String i: tasksByEmployee) {
            if (str.isEmpty()) str.append("Ошибочные данные в строках: ");
            String[] task = i.split("\n");
            if (!ValidationService.validateTaskInput(task)) {
                str.append(cnt);
                continue;
            }

            for (String j: task[1].split(", ")) {
                if (errorInsert.isEmpty()) errorInsert.append("О сотрудниках, указанных на этих строках, не удалось добавить информацию в базу данных. Попробуйте позже: ");
                boolean success = DatabaseManager.insertIntoCompletedTasks(task[0], Integer.valueOf(j));
                if (!success) errorInsert.append(cnt);
            }
        }

        UserCommandsStore.lastUserCommand.remove(user.userId(), this);
        if (str.isEmpty() && errorInsert.isEmpty()) return new SendMessage(user.chatId(), "Ваш запрос успешно обработан");

        return new SendMessage((user.chatId()), str + "\n" + errorInsert);
    }
}
