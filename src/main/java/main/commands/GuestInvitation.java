package main.commands;

import main.core.User;
import main.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GuestInvitation implements Command {
    private User user;
    @Override
    public SendMessage execute(Update event) {
        if (user == null) setUserSettings(event);

        if (event.hasMessage()) return completeQuery(event);
        return getAdditionInfo();
    }

    @Override
    public void setUserSettings(Update event) {
        if (event.hasCallbackQuery()) {
            user = new User(
                    event.getCallbackQuery().getFrom().getId(),
                    String.valueOf(event.getCallbackQuery().getMessage().getChatId())
            );
        } else {
            user = new User(
                    event.getMessage().getFrom().getId(),
                    String.valueOf(event.getMessage().getChatId())
            );
        }
    }

    private SendMessage getAdditionInfo() {
        UserCommandsStore.lastUserCommand.put(user.getUserId(), this);

        return new SendMessage(user.getChatId(), "Введите данные в заданном формате:\nФИО\nСерия и номер паспорта\nКем выдан\nКогда\nКод подразделения");
    }


    private SendMessage completeQuery(Update event) {
        String data = event.getMessage().getText();
        String[] param = data.split("\n");

        boolean success = DatabaseManager.createGuestApplication(user.getUserId(), param);

        UserCommandsStore.lastUserCommand.remove(user.getUserId());

        String result = success ? "Ваш запрос успешно обработан": "Ошибка в базе данных";

        return new SendMessage(event.getMessage().getChatId().toString(), result);
    }

}
