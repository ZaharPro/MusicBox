package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.psr.PageSearchResult;
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

    private static <T> T getNull() {
        return null;
    }

    private static <T> void assertEmpty(PageSearchResult<T> psr) {
        Assertions.assertNotNull(psr);
        Assertions.assertEquals(0, psr.getCount());
        Assertions.assertNotNull(psr.getElements());
        Assertions.assertTrue(psr.getElements().isEmpty());
        Assertions.assertFalse(psr.hasElements());
    }

    @Test
    public void findByLoginIfNullFalse() {
        Optional<User> optionalUser = Assertions.assertDoesNotThrow(() -> userService.findByLogin(getNull()));
        Assertions.assertNotNull(optionalUser);
        Assertions.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void findByLoginIfBadFalse() {
        Optional<User> optionalUser = Assertions.assertDoesNotThrow(() -> userService.findByLogin(BAD_LOGIN));
        Assertions.assertNotNull(optionalUser);
        Assertions.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void findByEmailIfNullFalse() {
        Optional<User> optionalUser = Assertions.assertDoesNotThrow(() -> userService.findByEmail(getNull()));
        Assertions.assertNotNull(optionalUser);
        Assertions.assertFalse(optionalUser.isPresent());
    }

    @Test
    public void findByEmailIfBadFalse() {
        Optional<User> optionalUser = Assertions.assertDoesNotThrow(() -> userService.findByEmail(BAD_EMAIL));
        Assertions.assertNotNull(optionalUser);
        Assertions.assertFalse(optionalUser.isPresent());
    }


    @Test
    public void countByRoleIfBadZero() {
        long count = Assertions.assertDoesNotThrow(() -> userService.countByRole(BAD_ID));
        Assertions.assertEquals(0, count);
    }

    @Test
    public void findByRoleIfBadEmpty() {
        PageSearchResult<User> psr = Assertions.assertDoesNotThrow(() -> {
            return userService.findByRole(BAD_ID, BAD_PAGE, BAD_PAGE_SIZE);
        });
        assertEmpty(psr);
    }

    @Test
    public void countLikedTracksIfBadZero() {
        long count = Assertions.assertDoesNotThrow(() -> userService.countLikedTracks(BAD_ID));
        Assertions.assertEquals(0, count);
    }

    @Test
    public void getLikedTracksIfBadEmpty() {
        PageSearchResult<Track> psr = Assertions.assertDoesNotThrow(() -> {
            return userService.getLikedTracks(BAD_ID, BAD_PAGE, BAD_PAGE_SIZE);
        });
        assertEmpty(psr);
    }

    @Test
    public void isLikedTrackIfBadFalse() {
        boolean isLiked = Assertions.assertDoesNotThrow(() -> {
            return userService.isLikedTrack(BAD_ID, BAD_ID);
        });
        Assertions.assertFalse(isLiked);
    }
}
