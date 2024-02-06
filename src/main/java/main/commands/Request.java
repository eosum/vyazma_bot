package main.commands;

import main.core.Task;
import main.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;


public class Request implements Command{
    @Override
    public SendMessage execute(Update event) {
        ArrayList<Task> ans = DatabaseManager.getRequests(event.getCallbackQuery().getFrom().getId());

        StringBuilder str = new StringBuilder();
        for (Task i : ans) {
            str.append(i);
            str.append("\n");
        }

        return new SendMessage(String.valueOf(event.getCallbackQuery().getMessage().getChatId()), str.toString());
    }
}
