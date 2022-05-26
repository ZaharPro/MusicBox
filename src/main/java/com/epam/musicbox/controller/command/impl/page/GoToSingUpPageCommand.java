package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;

public class GoToSingUpPageCommand extends GoToPageCommand {
    public GoToSingUpPageCommand() {
        super(PagePath.SIGN_UP);
    }
}
