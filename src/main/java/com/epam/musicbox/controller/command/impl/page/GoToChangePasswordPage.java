package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.util.constant.PagePath;

public class GoToChangePasswordPage extends GoToPageCommand {
    public GoToChangePasswordPage() {
        super(PagePath.CHANGE_PASSWORD);
    }
}
