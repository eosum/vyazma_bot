package main.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CommandsUtils {

    public static SendMessage getErrorMessage(Long chatId) {
        return new SendMessage(chatId.toString(), "Go away!");
    }

    public static SendMessage getParamErrorMessage(String chatId) {
        return new SendMessage(chatId, "Ошибка в формате параметров. Попробуйте ввести еще раз.");
    }

    public static SendMessage getRightsErrorMessage(Long chatId) {
        return new SendMessage(String.valueOf(chatId), "У вас недостаточно прав для выполнения этой команды.");
    }
}
