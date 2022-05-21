package com.epam.musicbox.controller.command;

import com.epam.musicbox.controller.command.impl.LoginCommand;
import com.epam.musicbox.controller.command.impl.LogoutCommand;
import com.epam.musicbox.controller.command.impl.SingUpCommand;

import java.util.EnumMap;
import java.util.Map;

public class CommandProvider {
    public static final CommandProvider instance = new CommandProvider();

    public static CommandProvider getInstance() {
        return instance;
    }

    private final Map<CommandType, Command> commands;

    private CommandProvider() {
        this.commands = new EnumMap<>(CommandType.class);
        this.commands.put(CommandType.SING_UP, new SingUpCommand());
        this.commands.put(CommandType.LOGIN, new LoginCommand());
        this.commands.put(CommandType.LOGOUT, new LogoutCommand());
    }

    public Command get(CommandType type) {
        return commands.get(type);
    }
}
