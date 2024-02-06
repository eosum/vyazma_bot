package main.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CommandsUtils {

    public static SendMessage getErrorMessage(Long chatId) {
        return new SendMessage(chatId.toString(), "Я вас не понимаю.");
    }
}
