package main.taskshandler;

import main.bot.TelegramBot;
import main.commands.Command;
import main.commands.UserCommandsStore;
import main.utils.CommandsUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TasksHandler implements Runnable{
    private TelegramBot bot;
    private ExecutorService pool;
    private Map<String, Command> commands;

    public TasksHandler(TelegramBot bot) {
        this.bot = bot;
        pool = Executors.newCachedThreadPool();
        commands = CommandsUtils.fillCommandNames();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Update event = bot.receiveQueue.take();
                String text = event.hasMessage() ? event.getMessage().getText() : event.getCallbackQuery().getData();
                Long userId = event.hasMessage() ? event.getMessage().getFrom().getId() : event.getCallbackQuery().getFrom().getId();

                Command command = commands.getOrDefault(text, null);
                Command lastCommand = UserCommandsStore.lastUserCommand.getOrDefault(userId, null);
                Command resultCommand = command == null ? lastCommand : command;

                if (resultCommand == null) {
                    bot.sendQueue.add(CommandsUtils.getErrorMessage(event.getMessage().getChatId()));
                    continue;
                }

                pool.submit(() -> {
                   SendMessage message = resultCommand.execute(event);
                   bot.sendQueue.add(message);
                });
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
