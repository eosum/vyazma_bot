package main.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Keyboard {
    InlineKeyboardMarkup getMarkup();
}
