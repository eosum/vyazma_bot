package main.commands;

import main.constantdata.BookingConstData;
import main.core.Room;
import main.core.User;
import main.database.DatabaseManager;
import main.keyboards.Keyboard;
import main.keyboards.TwoButtonsRowKeyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;

public class Booking implements Command{
    private HashMap<String, Room> rooms;
    private int iterationNumber = 0;
    private User user;
    private String roomId;
    private String date;
    private String hour;

    public Booking() {
        rooms = BookingConstData.rooms;
    }

    public ArrayList<String> getRoomsDescription() {
        ArrayList<String> res = new ArrayList<>();
        for (Room i: rooms.values()) {
            res.add(i.toString());
        }
        return res;
    }

    @Override
    public Object execute(Update event) {
        if (user == null) user = setUserSettings(event);
        UserCommandsStore.lastUserCommand.put(user.getUserId(), this);
        iterationNumber++;
        return switch (iterationNumber) {
            case 1 -> chooseRoom(event);
            case 2 -> getAvailableDates(event);
            case 3 -> getAvailableHours(event);
            case 4 -> makeBooking(event);
            default -> null;
        };
    }

    private EditMessageReplyMarkup chooseRoom(Update event) {
        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder().chatId(user.getChatId()).messageId(event.getCallbackQuery().getMessage().getMessageId()).build();
        newKb.setReplyMarkup(new Keyboard(getRoomsDescription()).getMarkup());

        return newKb;
    }

    private EditMessageReplyMarkup getAvailableDates(Update event) {
        int messageId = event.getCallbackQuery().getMessage().getMessageId();

        roomId = event.getCallbackQuery().getData().split(" - ")[0];

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder().chatId(user.getChatId()).messageId(messageId).build();
        newKb.setReplyMarkup(new TwoButtonsRowKeyboard(rooms.get(roomId).getAvailableDate()).getMarkup());

        return newKb;
    }

    private EditMessageReplyMarkup getAvailableHours(Update event) {
        int messageId = event.getCallbackQuery().getMessage().getMessageId();

        date = event.getCallbackQuery().getData();
        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder().chatId(user.getChatId()).messageId(messageId).build();
        newKb.setReplyMarkup(new TwoButtonsRowKeyboard(rooms.get(roomId).getAvailableHours(date)).getMarkup());

        return newKb;
    }

    private SendMessage makeBooking(Update event) {
        hour = event.getCallbackQuery().getData();

        if (!rooms.get(roomId).addHour(date, hour)) {
            return new SendMessage(event.getMessage().getChatId().toString(), "Что - то пошло не так, попробуйте еще раз");
        }

        boolean success = DatabaseManager.createBooking(user.getUserId(), roomId, date, hour);

        UserCommandsStore.lastUserCommand.remove(user.getUserId());

        String result = success ? getApprovedMessage(): "Ошибка в базе данных";

        return new SendMessage(user.getChatId(), result);
    }

    private String getApprovedMessage() {
        return "Ваше бронирование оформлено.\nДетали бронирования:\nКомната: " +
                rooms.get(roomId).toString() + "\nДата и время: " + date + " " + hour;
    }
}
