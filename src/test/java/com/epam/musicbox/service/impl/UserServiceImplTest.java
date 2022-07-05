package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserServiceImplTest {

    private static final UserServiceImpl userService = UserServiceImpl.getInstance();

    private static final String BAD_LOGIN = "!@#$%^&*(){}[]";
    private static final String BAD_EMAIL = "!@#$%^&*(){}[]";
    private static final int BAD_ID = -1;
    private static final int BAD_PAGE = -1;
    private static final int BAD_PAGE_SIZE = 0;

    @Test
    public void findByLoginIfNullDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> userService.findByLogin(null));
    }

    @Test
    public void findByLoginIfNullFalse() throws ServiceException {
        Optional<User> optionalUser = userService.findByLogin(null);
        Assertions.assertFalse(optionalUser.isPresent());
    }


    @Test
    public void findByLoginIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> userService.findByLogin(BAD_LOGIN));
    }

    @Test
    public void findByLoginIfBadFalse() throws ServiceException {
        Optional<User> optionalUser = userService.findByLogin(BAD_LOGIN);
        Assertions.assertFalse(optionalUser.isPresent());
    }


    @Test
    public void findByEmailIfNullDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> userService.findByEmail(null));
    }

    @Test
    public void findByEmailIfNullFalse() throws ServiceException {
        Optional<User> optionalUser = userService.findByEmail(null);
        Assertions.assertFalse(optionalUser.isPresent());
    }


    @Test
    public void findByEmailIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> userService.findByEmail(BAD_EMAIL));
    }

    @Test
    public void findByEmailIfBadFalse() throws ServiceException {
        Optional<User> optionalUser = userService.findByEmail(BAD_EMAIL);
        Assertions.assertFalse(optionalUser.isPresent());
    }


    @Test
    public void countByRoleIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> userService.countByRole(BAD_ID));
    }

    @Test
    public void countByRoleIfBadZero() throws ServiceException {
        long count = userService.countByRole(BAD_ID);
        Assertions.assertEquals(0, count);
    }


    @Test
    public void findByRoleIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            return userService.findByRole(BAD_ID, BAD_PAGE, BAD_PAGE_SIZE);
        });
    }

    @Test
    public void findByRoleIfBadEmpty() throws ServiceException {
        PageSearchResult<User> psr = userService.findByRole(BAD_ID, BAD_PAGE, BAD_PAGE_SIZE);
        Assertions.assertFalse(psr.hasElements());
    }


    @Test
    public void countLikedTracksIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> userService.countLikedTracks(BAD_ID));
    }

    @Test
    public void countLikedTracksIfBadZero() throws ServiceException {
        long count = userService.countLikedTracks(BAD_ID);
        Assertions.assertEquals(0, count);
    }


    @Test
    public void getLikedTracksIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            return userService.getLikedTracks(BAD_ID, BAD_PAGE, BAD_PAGE_SIZE);
        });
    }

    @Test
    public void getLikedTracksIfBadEmpty() throws ServiceException {
        PageSearchResult<Track> psr = userService.getLikedTracks(BAD_ID, BAD_PAGE, BAD_PAGE_SIZE);
        Assertions.assertFalse(psr.hasElements());
    }


    @Test
    public void isLikedTrackIfBadDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            return userService.isLikedTrack(BAD_ID, BAD_ID);
        });
    }

    @Test
    public void isLikedTrackIfBadFalse() throws ServiceException {
        boolean isLiked = userService.isLikedTrack(BAD_ID, BAD_ID);
        Assertions.assertFalse(isLiked);
    }
}
