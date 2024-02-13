package main.utils;

import main.commands.common.Command;
import main.commands.common.Start;
import main.commands.employee.*;
import main.commands.student.*;

import java.util.HashMap;

public class CommandFabric {
    private final HashMap<String, CreateObject> commands = new HashMap<>();


    public CommandFabric() {
        commands.put("Гостевой пропуск", this::getGuestInvitation);
        commands.put("/start", this::getStart);
        commands.put("Мои заявки", this::getRequest);
        commands.put("Оформление выезда", this::getDepartureApplication);
        commands.put("Бронь комнаты", this::getBooking);
        commands.put("Выбор сервиса", this::getService);
        commands.put("Просмотреть выехавших", this::getWhoHaveLeft);
        commands.put("Новые задачи", this::getTasks);
        commands.put("Подтвердить гостя", this::getGuestApproving);
        commands.put("Добавить новость", this::getPostNews);
        commands.put("Завершенные задачи", this::getCompletedTask);
    }

    public Command getCommand(String command) {
        if (!commands.containsKey(command)) return null;
        return commands.get(command).getCommand();
    }

    private ServiceChoice getService() {
        return new ServiceChoice();
    }

    private Booking getBooking() {
        return new Booking();
    }

    private Request getRequest() {
        return new Request();
    }

    private DepartureApplication getDepartureApplication() {
        return new DepartureApplication();
    }

    private GuestInvitation getGuestInvitation() {
        return new GuestInvitation();
    }

    private Start getStart() {
        return new Start();
    }

    private ViewingWhoHasLeft getWhoHaveLeft() {
        return new ViewingWhoHasLeft();
    }

    private TasksChecking getTasks() {
        return new TasksChecking();
    }

    private GuestApproving getGuestApproving() {
        return new GuestApproving();
    }

    private PostNews getPostNews() {
        return new PostNews();
    }

    private AddCompletedTasks getCompletedTask() {
        return new AddCompletedTasks();
    }
}
