package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.impl.UserServiceImpl;

public class UserGetCommand extends GetCommand<User> {

    public UserGetCommand() {
        super(UserServiceImpl.getInstance(), Parameter.LIST, PagePath.USER);
    }
}
