CREATE SCHEMA IF NOT EXISTS `music_schema` DEFAULT CHARACTER SET utf8;
USE `music_schema`;

CREATE TABLE IF NOT EXISTS `music_schema`.`albums`
(
    `album_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(32)  NOT NULL,
    `picture`  VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS `music_schema`.`artists`
(
    `artist_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`      VARCHAR(32)  NOT NULL,
    `avatar`    VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS `music_schema`.`roles`
(
    `role_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`    VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS `music_schema`.`tracks`
(
    `track_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(32)  NOT NULL,
    `path`     VARCHAR(128) NOT NULL,
    `album_id` BIGINT       NOT NULL,
    CONSTRAINT `tracks_album_id`
    FOREIGN KEY (`album_id`) REFERENCES albums (`album_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `music_schema`.`artist_tracks`
(
    `artist_track_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `artist_id`       BIGINT NOT NULL,
    `track_id`        BIGINT NOT NULL,
    CONSTRAINT `artist_tracks_artist_id`
    FOREIGN KEY (`artist_id`) REFERENCES artists (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `artist_tracks_track_id`
    FOREIGN KEY (`track_id`) REFERENCES tracks (`track_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `music_schema`.`users`
(
    `user_id`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `login`        VARCHAR(32)                         NOT NULL,
    `password`     VARCHAR(128)                        NOT NULL,
    `email`        VARCHAR(32)                         NOT NULL,
    `banned`       tinyint(1)                          NOT NULL,
    `registration` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS `music_schema`.`playlists`
(
    `playlist_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`        VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS `music_schema`.`playlist_tracks`
(
    `playlist_track_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `playlist_id`       BIGINT NOT NULL,
    `track_id`          BIGINT NOT NULL,
    CONSTRAINT `playlist_tracks_playlist_id`
    FOREIGN KEY (`playlist_id`) REFERENCES playlists (`playlist_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `playlist_tracks_track_id`
    FOREIGN KEY (`track_id`) REFERENCES tracks (`track_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `music_schema`.`user_liked_albums`
(
    `user_liked_album_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`             BIGINT NOT NULL,
    `album_id`            BIGINT NOT NULL,
    CONSTRAINT `user_liked_albums_user_id`
    FOREIGN KEY (`user_id`) REFERENCES albums (`album_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_liked_albums_album_id`
    FOREIGN KEY (`album_id`) REFERENCES users (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `music_schema`.`user_liked_tracks`
(
    `user_liked_track_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`             BIGINT NOT NULL,
    `track_id`            BIGINT NOT NULL,
    CONSTRAINT `user_liked_tracks_user_id`
    FOREIGN KEY (`user_id`) REFERENCES users (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_liked_tracks_track_id`
    FOREIGN KEY (`track_id`) REFERENCES tracks (`track_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `music_schema`.`user_liked_artists`
(
    `user_liked_artist_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`              BIGINT NOT NULL,
    `artist_id`            BIGINT NOT NULL,
    CONSTRAINT `user_liked_artists_user_id`
    FOREIGN KEY (`user_id`) REFERENCES users (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_liked_artists_artist_id`
    FOREIGN KEY (`artist_id`) REFERENCES artists (`artist_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `music_schema`.`user_playlists`
(
    `user_playlists_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`           BIGINT NOT NULL,
    `playlist_id`       BIGINT NOT NULL,
    CONSTRAINT `user_playlists_user_id`
    FOREIGN KEY (`user_id`) REFERENCES users (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_playlists_track_id`
    FOREIGN KEY (`playlist_id`) REFERENCES playlists (`playlist_id`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS `music_schema`.`user_roles`
(
    `user_role_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`      BIGINT NOT NULL,
    `role_id`      BIGINT NOT NULL,
    CONSTRAINT `user_roles_user_id`
    FOREIGN KEY (`user_id`) REFERENCES users (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_roles_role_id`
    FOREIGN KEY (`role_id`) REFERENCES roles (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE UNIQUE INDEX roles_name_uindex on roles (`name`);
CREATE UNIQUE INDEX user_login_uindex on users (`login`);
CREATE UNIQUE INDEX user_email_uindex on users (`email`);

/*INSERT INTO roles VALUES (0, 'user');
INSERT INTO roles VALUES (1, 'admin');*/