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

        return new SendMessage(user.getChatId(), FORMAT_MESSAGE);
    }


    private SendMessage completeQuery(Update event) {
        String data = event.getMessage().getText();
        String[] param = data.split("\n");

        SendMessage error = validateInput(param);
        if (error != null) return error;

        boolean success = DatabaseManager.createGuestApplication(user.getUserId(), param);

        UserCommandsStore.lastUserCommand.remove(user.getUserId());

        String result = success ? "Ваш запрос успешно обработан": "Ошибка в базе данных";

        return new SendMessage(event.getMessage().getChatId().toString(), result);
    }

    private SendMessage validateInput(String[] param) {
        if (param.length < ROW_COUNT) return CommandsUtils.getParamErrorMessage(user.getChatId());
        if (!ValidationService.checkDateFormat(param[5]) ||
                !ValidationService.checkTimeFormat(param[6]) ||
                !ValidationService.checkTimeFormat(param[7])
        ) return CommandsUtils.getParamErrorMessage(user.getChatId());

        return null;
    }

}
