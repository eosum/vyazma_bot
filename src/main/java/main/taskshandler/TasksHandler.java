package main.taskshandler;

import main.bot.TelegramBot;
import main.commands.Command;
import main.commands.UserCommandsStore;
import main.utils.CommandFabric;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TasksHandler implements Runnable{
    private TelegramBot bot;
    private ExecutorService pool;
    private CommandFabric commandFabric;

    public TasksHandler(TelegramBot bot) {
        this.bot = bot;
        pool = Executors.newCachedThreadPool();
        commandFabric = new CommandFabric();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Update event = bot.receiveQueue.take();
                String text = event.hasMessage() ? event.getMessage().getText() : event.getCallbackQuery().getData();
                Long userId = event.hasMessage() ? event.getMessage().getFrom().getId() : event.getCallbackQuery().getFrom().getId();

                Command command = commandFabric.getCommand(text);
                Command lastCommand = UserCommandsStore.lastUserCommand.getOrDefault(userId, null);
                Command resultCommand = command == null ? lastCommand : command;

                if (resultCommand == null) {
                    bot.sendQueue.add(CommandsUtils.getErrorMessage(event.getMessage().getChatId()));
                    continue;
                }

                pool.submit(() -> {
                    Object message = resultCommand.execute(event);
                    bot.sendQueue.add(message);
                });
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
