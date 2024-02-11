package main.commands;

import main.core.DeparturePersonInfo;
import main.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

public class ViewingWhoHasLeft implements Command{

    @Override
    public SendMessage execute(Update event) {
        Long userId = event.getCallbackQuery().getFrom().getId();
        ArrayList<DeparturePersonInfo> ans = DatabaseManager.getWhoHasLeft();

        StringBuilder str = new StringBuilder();
        for (DeparturePersonInfo i : ans) {
            str.append(i);
            str.append("\n\n");
        }

        UserCommandsStore.lastUserCommand.remove(userId);

        return new SendMessage(String.valueOf(event.getCallbackQuery().getMessage().getChatId()), str.toString());
    }
}
