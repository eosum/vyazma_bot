package main.commands;

import main.core.User;
import main.database.DatabaseManager;
import main.services.ValidationService;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static main.constantdata.GuestInvitationConstData.FORMAT_MESSAGE;
import static main.constantdata.GuestInvitationConstData.ROW_COUNT;

public class GuestInvitation implements Command {
    private User user;
    @Override
    public SendMessage execute(Update event) {
        if (user == null) user = setUserSettings(event);

        if (event.hasMessage()) return completeQuery(event);
        return getAdditionInfo();
    }

    private SendMessage getAdditionInfo() {
        UserCommandsStore.lastUserCommand.put(user.userId(), this);

        return new SendMessage(user.chatId(), FORMAT_MESSAGE);
    }


    private SendMessage completeQuery(Update event) {
        String data = event.getMessage().getText();
        String[] param = data.split("\n");

        SendMessage error = validateInput(param);
        if (error != null) return error;

        boolean success = DatabaseManager.createGuestApplication(user.userId(), param);

        UserCommandsStore.lastUserCommand.remove(user.userId());

        String result = success ? "Ваш запрос успешно обработан. Ответ администрации придет в ближайшие сутки": "Ошибка в базе данных";

        return new SendMessage(event.getMessage().getChatId().toString(), result);
    }

    private SendMessage validateInput(String[] param) {
        if (param.length < ROW_COUNT) return CommandsUtils.getParamErrorMessage(user.chatId());
        if (!ValidationService.checkDateFormat(param[5]) ||
                !ValidationService.checkTimeFormat(param[6]) ||
                !ValidationService.checkTimeFormat(param[7])
        ) return CommandsUtils.getParamErrorMessage(user.chatId());

        return null;
    }

}
