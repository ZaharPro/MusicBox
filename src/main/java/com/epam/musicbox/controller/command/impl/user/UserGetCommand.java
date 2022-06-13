package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetCommand;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.impl.UserServiceImpl;

public class UserGetCommand extends GetCommand<User, Long> {

    public UserGetCommand() {
        super(UserServiceImpl.getInstance(), Parameter.USER_PAGE, Parameter.USER_LIST, PagePath.USERS);
    }
}
