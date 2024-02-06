package main.commands;

import main.core.User;
import main.database.DatabaseManager;
import main.services.ValidationService;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static main.constantdata.DepartureConstData.FORMAT_MESSAGE;
import static main.constantdata.DepartureConstData.ROW_COUNT;

public class DepartureApplication implements Command{
    private User user;
    @Override
    public SendMessage execute(Update event) {
        if (user == null) setUserSettings(event);
        UserCommandsStore.lastUserCommand.put(user.getUserId(), this);

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

    public SendMessage getAdditionInfo() {
        return new SendMessage(user.getChatId(), FORMAT_MESSAGE);
    }


    public SendMessage completeQuery(Update event) {
        String data = event.getMessage().getText();

        String[] param = data.split("\n");

        SendMessage error = validateInput(param);
        if (error != null) return error;

        boolean success = DatabaseManager.createDepartureApplication(user.getUserId(), param[0]);

        UserCommandsStore.lastUserCommand.remove(user.getUserId());

        String result = success ? "Ваш заявка успешно обработана. Ответ администрации придет в ближайшие сутки": "Ошибка в базе данных";

        return new SendMessage(user.getChatId(), result);
    }

    private SendMessage validateInput(String[] param) {
        if (param.length < ROW_COUNT || !ValidationService.checkDateFormat(param[0]))
            return CommandsUtils.getParamErrorMessage(user.getChatId());

        return null;
    }

}
