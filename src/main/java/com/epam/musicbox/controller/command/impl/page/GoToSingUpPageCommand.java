package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;

public class GoToSingUpPageCommand extends GoToPageCommand {
    public GoToSingUpPageCommand() {
        super(PagePath.SIGN_UP);
    }
}
