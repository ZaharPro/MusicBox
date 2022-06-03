package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.hasher.PasswordHasher;
import com.epam.musicbox.hasher.impl.PBKDF2PasswordHasher;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.AuthUtils;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class ChangePasswordCommand implements Command {

    private final PasswordHasher passwordHasher = PBKDF2PasswordHasher.getInstance();

    private final Validator validator = ValidatorImpl.getInstance();

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String currentPassword = req.getParameter(Parameter.CURRENT_PASSWORD);
        String newPassword = req.getParameter(Parameter.NEW_PASSWORD);

        if (validator.isValidPassword(currentPassword))
            throw new HttpException("Invalid current password", HttpServletResponse.SC_BAD_REQUEST);
        if (validator.isValidPassword(newPassword))
            throw new HttpException("Invalid new password", HttpServletResponse.SC_BAD_REQUEST);

        Jws<Claims> token = AuthUtils.getClaimsJws(req);
        Claims body = token.getBody();
        long userId = Parameters.get(body, Parameter.USER_ID);
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty())
            throw new HttpException("User not exist", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        User user = optionalUser.get();

        if (!passwordHasher.checkPassword(currentPassword, user.getPassword()))
            throw new HttpException("Invalid current password", HttpServletResponse.SC_BAD_REQUEST);
        user.setPassword(newPassword);
        userService.save(user);
        return CommandResult.refresh();
    }
}
