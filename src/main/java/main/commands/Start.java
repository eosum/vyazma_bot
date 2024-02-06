package main.commands;

import main.keyboards.StudentStartKeyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Start implements Command {
    @Override
    public SendMessage execute(Update event) {
        SendMessage message = new SendMessage(event.getMessage().getChatId().toString(), "Hello");
        message.setReplyMarkup(new StudentStartKeyboard().getMarkup());

        return message;
    }
}
