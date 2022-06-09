package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.util.constant.PagePath;

public class GoToHomePageCommand extends GoToPageCommand {
    public GoToHomePageCommand() {
        super(PagePath.HOME);
    }
}
