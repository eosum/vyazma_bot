package main.commands.common;

import main.commands.common.Command;

import java.util.concurrent.ConcurrentHashMap;

public class UserCommandsStore {
    public static ConcurrentHashMap<Long, Command> lastUserCommand = new ConcurrentHashMap<>();

}
