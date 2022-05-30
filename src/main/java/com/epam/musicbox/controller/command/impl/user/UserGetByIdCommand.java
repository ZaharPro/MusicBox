package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.GetByIdCommand;
import com.epam.musicbox.entity.User;

public class UserGetByIdCommand extends GetByIdCommand<User> {

    public UserGetByIdCommand() {
        super(Parameter.USER_ID, Parameter.OBJECT, PagePath.USER);
    }
}
