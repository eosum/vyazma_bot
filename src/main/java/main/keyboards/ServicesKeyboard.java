package main.keyboards;

import main.database.DatabaseManager;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ServicesKeyboard implements Keyboard {
    private List<String> buttonsNames;

    private InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

    public ServicesKeyboard() {
        //buttonsNames = DatabaseManager.getServicesNames();
        createInlineKeyboardMarkup();
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
        for (int i = 0;i < buttonsNames.size(); i++) {
            store.add(getRow(i));
        }
        return store;
    }

    private List<InlineKeyboardButton> getRow(int rowNumber) {
        InlineKeyboardButton button = new InlineKeyboardButton(buttonsNames.get(rowNumber));
        button.setCallbackData(buttonsNames.get(rowNumber));

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

}
