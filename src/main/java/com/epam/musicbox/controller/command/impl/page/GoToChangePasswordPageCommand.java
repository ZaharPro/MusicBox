package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;

public class GoToChangePasswordPageCommand extends GoToPageCommand {
    public GoToChangePasswordPageCommand() {
        super(PagePath.CHANGE_PASSWORD);
    }
}
