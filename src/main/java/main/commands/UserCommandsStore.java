package main.commands;

import java.util.concurrent.ConcurrentHashMap;

public class UserCommandsStore {
    public static ConcurrentHashMap<Long, Command> lastUserCommand = new ConcurrentHashMap<>();

}
