package main.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command{
    Object execute(Update event);

    default void setUserSettings(Update event) {

    }
}
