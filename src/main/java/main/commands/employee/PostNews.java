package main.commands.employee;

import main.commands.common.Command;
import main.commands.common.UserCommandsStore;
import main.database.DatabaseManager;
import main.services.ValidationService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static main.utils.CommandsUtils.getDatabaseError;
import static main.utils.CommandsUtils.getRightsErrorMessage;

public class PostNews implements Command {
    int iteration = 0;
    @Override
    public Object execute(Update event) {
        iteration++;
        return switch(iteration) {
            case 1 -> getNews(event);
            case 2 -> sendNews(event);
            default -> null;
        };
    }

    private SendMessage getNews(Update event) {
        Long userId = event.getCallbackQuery().getFrom().getId();
        UserCommandsStore.lastUserCommand.put(userId, this);

        if (!ValidationService.hasRights(userId, "заведующий общежитием")) {
            UserCommandsStore.lastUserCommand.remove(userId);
            return getRightsErrorMessage(userId);
        }

        return new SendMessage(String.valueOf(userId), "Введите новость в следующем формате:\nЗаголовок\nСодержание новости");
    }

    private SendMessage sendNews(Update event) {
        Long userId = event.getMessage().getFrom().getId();
        String news = event.getMessage().getText();

        StringBuilder header = new StringBuilder();
        for (int i = 0; i < news.length(); i++) {
            if (news.charAt(i) == '\n') continue;
            header.append(news.charAt(i));
        }

        boolean success = DatabaseManager.addNews(userId, header.toString(), news);

        UserCommandsStore.lastUserCommand.remove(userId);
        if (success) {
            return new SendMessage("@dorm_vyazma_news", event.getMessage().getText());
        }

        return getDatabaseError(userId);
    }
}
