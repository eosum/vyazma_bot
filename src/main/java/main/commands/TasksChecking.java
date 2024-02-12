package main.commands;

import main.core.EmployeeTask;
import main.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

public class TasksChecking implements Command {
    @Override
    public Object execute(Update event) {
        Long userId = event.getCallbackQuery().getFrom().getId();
        ArrayList<EmployeeTask> ans = DatabaseManager.getTasks(userId);

        StringBuilder str = new StringBuilder();
        for (EmployeeTask i : ans) {
            str.append(i);
            str.append("\n\n");
        }

        UserCommandsStore.lastUserCommand.remove(userId);

        if (str.isEmpty()) str = new StringBuilder("У вас нет никаких назначенных задач. Отдыхайте!");

        return new SendMessage(String.valueOf(event.getCallbackQuery().getMessage().getChatId()), str.toString());
    }
}
