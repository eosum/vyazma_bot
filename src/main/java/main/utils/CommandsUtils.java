package main.utils;

import main.commands.Command;
import main.commands.GuestInvitation;
import main.commands.Request;
import main.commands.Start;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class CommandsUtils {
    public static Map<String, Command> fillCommandNames() {
        HashMap<String, Command> commands = new HashMap<>();

        commands.put("Гостевой пропуск", new GuestInvitation());
        commands.put("/start", new Start());
        commands.put("Мои заявки", new Request());

        return commands;
    }

    public static SendMessage getErrorMessage(Long chatId) {
        return new SendMessage(chatId.toString(), "Я вас не понимаю.");
    }
}
