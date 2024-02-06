package main.utils;

import main.commands.*;
import main.core.Service;

import java.util.HashMap;

public class CommandFabric {
    private HashMap<String, CreateObject> commands = new HashMap<>();

    public CommandFabric() {
        commands.put("Гостевой пропуск", this::getGuestInvitation);
        commands.put("/start", this::getStart);
        commands.put("Мои заявки", this::getRequest);
        commands.put("Оформление выезда", this::getDepartureApplication);
        commands.put("Бронь комнаты", this::getBooking);
        commands.put("Выбор сервиса", this::getService);

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

}