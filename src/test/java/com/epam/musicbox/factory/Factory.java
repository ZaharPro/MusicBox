package com.epam.musicbox.factory;

import com.epam.musicbox.entity.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Factory {
    private static final Random random = new Random();

    public static Track createTrack() {
        return new Track(random.nextLong(),
                createString(),
                createString(),
                random.nextLong());
    }

    public static Album createAlbum() {
        return new Album(random.nextLong(),
                createString(),
                createString());
    }

    public static Artist createArtist() {
        return new Artist(random.nextLong(),
                createString(),
                createString());
    }

    public static Playlist createPlaylist() {
        return new Playlist(random.nextLong(),
                createString(),
                createString(),
                random.nextLong());
    }

    public static Role createRole() {
        Role[] roles = Role.values();
        int index = random.nextInt(roles.length);
        return roles[index];
    }

    public static User createUser() {
        return new User(random.nextLong(),
                createString(),
                createString(),
                createString(),
                createRole(),
                random.nextBoolean(),
                Timestamp.from(Instant.now()));
    }

    public static String createString() {
        int length = Math.max(32, random.nextInt(64));
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static <T> List<T> create(Supplier<T> factory, int count) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T t = factory.get();
            list.add(t);
        }
        return list;
    }
}
