package com.epam.musicbox.controller.command;

import com.epam.musicbox.controller.command.impl.auth.LoginCommand;
import com.epam.musicbox.controller.command.impl.auth.LogoutCommand;
import com.epam.musicbox.controller.command.impl.auth.SingUpCommand;
import jakarta.inject.Singleton;

import java.util.EnumMap;
import java.util.Map;

@Singleton
public class CommandProvider {
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
