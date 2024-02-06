package main.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StudentStartKeyboard implements Keyboard {
    private InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    private ArrayList<String> buttonsNames = new ArrayList<>();

    public StudentStartKeyboard() {
        fillButtonsNames();
        createInlineKeyboardMarkup();
    }

    private void fillButtonsNames() {
        buttonsNames.add("Выбор сервиса");
        buttonsNames.add("Бронь комнаты");
        buttonsNames.add("Гостевой пропуск");
        buttonsNames.add("Мои заявки");
        buttonsNames.add("Оформление выезда");
    }

    @Override
    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    private void createInlineKeyboardMarkup() {
        markup.setKeyboard(fillStore());
    }

    private List<List<InlineKeyboardButton>> fillStore() {
        List<List<InlineKeyboardButton>> store = new ArrayList<>();
        for (int i = 0;i < (buttonsNames.size() + 1) / 2; i++) {
            store.add(getRow(i));
        }
        return store;
    }

    private List<InlineKeyboardButton> getRow(int rowNumber) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        int it = rowNumber * 2;
        while (it < buttonsNames.size() && row.size() < 2) {
            InlineKeyboardButton button = new InlineKeyboardButton(buttonsNames.get(it));
            button.setCallbackData(buttonsNames.get(it++));
            row.add(button);
        }

        return row;
    }

}
