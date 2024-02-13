package main.commands.student;

import main.commands.common.Command;
import main.commands.common.UserCommandsStore;
import main.core.User;
import main.database.DatabaseManager;
import main.services.ValidationService;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static main.constantdata.DepartureConstData.FORMAT_MESSAGE;
import static main.constantdata.DepartureConstData.ROW_COUNT;

public class DepartureApplication implements Command {
    private User user;
    @Override
    public SendMessage execute(Update event) {
        if (user == null) user = setUserSettings(event);
        UserCommandsStore.lastUserCommand.put(user.userId(), this);

        if (event.hasMessage()) return completeQuery(event);
        return getAdditionInfo();
    }

    public SendMessage getAdditionInfo() {
        return new SendMessage(user.chatId(), FORMAT_MESSAGE);
    }


    public SendMessage completeQuery(Update event) {
        String data = event.getMessage().getText();

        String[] param = data.split("\n");

        SendMessage error = validateInput(param);
        if (error != null) return error;

        boolean success = DatabaseManager.createDepartureApplication(user.userId(), param[0]);

        UserCommandsStore.lastUserCommand.remove(user.userId());

        String result = success ? "Ваш заявка успешно обработана": "Ошибка в базе данных";

        return new SendMessage(user.chatId(), result);
    }

    private SendMessage validateInput(String[] param) {
        if (param.length < ROW_COUNT || !ValidationService.checkDateFormat(param[0]))
            return CommandsUtils.getParamErrorMessage(user.chatId());

        return null;
    }

}