package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;

public class GoToLoginPageCommand extends GoToPageCommand {
    public GoToLoginPageCommand() {
        super(PagePath.LOGIN);
    }
}
