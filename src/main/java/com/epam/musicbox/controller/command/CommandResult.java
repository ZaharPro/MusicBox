package com.epam.musicbox.controller.command;

public class CommandResult {
    private final CommandResultType type;
    private final String page;

    private CommandResult(CommandResultType type, String page) {
        this.type = type;
        this.page = page;
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(CommandResultType.FORWARD, page);
    }

    public static CommandResult forward(String page) {
        return new CommandResult(CommandResultType.REDIRECT, page);
    }

    public static CommandResult refresh() {
        return new CommandResult(CommandResultType.REFRESH, null);
    }

    public CommandResultType getType() {
        return type;
    }

    public String getPage() {
        return page;
    }
}