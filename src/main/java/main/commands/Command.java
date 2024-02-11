package main.commands;

import main.core.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command{
    Object execute(Update event);

    default User setUserSettings(Update event) {
        if (event.hasCallbackQuery()) {
            return new User(
                    event.getCallbackQuery().getFrom().getId(),
                    String.valueOf(event.getCallbackQuery().getMessage().getChatId())
            );
        }

        return new User(
                event.getMessage().getFrom().getId(),
                String.valueOf(event.getMessage().getChatId())
        );
    }
}
