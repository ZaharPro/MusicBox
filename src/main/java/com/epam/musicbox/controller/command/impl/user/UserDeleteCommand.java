package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.impl.UserServiceImpl;

public class UserDeleteCommand extends DeleteCommand<User> {

    public UserDeleteCommand() {
        super(UserServiceImpl.getInstance(), Parameter.USER_ID);
    }
}
