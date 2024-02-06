package main.commands;

import main.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GuestInvitation implements Command {
    public SendMessage execute(Update event) {
        if (event.hasMessage()) return completeQuery(event);
        return getAdditionInfo(event);
    }

    public SendMessage getAdditionInfo(Update event) {
        Long userId = event.getCallbackQuery().getFrom().getId();
        String chatId = String.valueOf(event.getCallbackQuery().getMessage().getChatId());

        UserCommandsStore.lastUserCommand.put(userId, this);

        return new SendMessage(chatId, "Введите данные в заданном формате:\nФИО\nСерия и номер паспорта\nКем выдан\nКогда\nКод подразделения");
    }


    public SendMessage completeQuery(Update event) {
        Long userId = event.getMessage().getFrom().getId();
        String data = event.getMessage().getText();

        String[] param = data.split("\n");

        boolean success = DatabaseManager.createGuestApplication(userId, param);

        UserCommandsStore.lastUserCommand.remove(userId);

        String result = "Ошибка в базе данных";

        if(success) result = "Ваш запрос успешно обработан";

        return new SendMessage(event.getMessage().getChatId().toString(), result);
    }

}
