package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.User;

public class UserGetCommand extends GetCommand<User> {

    public UserGetCommand() {
        super(Parameter.LIST, PagePath.USER);
    }
}
