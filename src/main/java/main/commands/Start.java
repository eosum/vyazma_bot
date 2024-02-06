package main.commands;

import main.constantdata.StartButtonsName;
import main.keyboards.TwoButtonsRowKeyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Start implements Command {
    @Override
    public SendMessage execute(Update event) {
        SendMessage message = new SendMessage(event.getMessage().getChatId().toString(), "Hello");
        message.setReplyMarkup(new TwoButtonsRowKeyboard(StartButtonsName.getButtonsNames()).getMarkup());

        return message;
    }
}
