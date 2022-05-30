package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.User;

public class UserDeleteCommand extends DeleteCommand<User> {
    public UserDeleteCommand() {
        super(Parameter.USER_ID);
    }
}
