create table if not exists albums
(
    album_id int auto_increment
    primary key,
    name     varchar(32)  not null,
    picture  varchar(128) not null
);

create table if not exists artists
(
    artist_id int auto_increment
    primary key,
    name      varchar(32)  not null,
    avatar    varchar(128) not null
);

create table if not exists roles
(
    role_id int auto_increment
    primary key,
    name    varchar(32) not null,
    constraint roles_name_uindex
    unique (name)
);

create table if not exists tracks
(
    track_id int auto_increment
    primary key,
    name     varchar(32)  not null,
    path     varchar(128) not null,
    album_id int          not null,
    constraint tracks_path_uindex
    unique (path),
    constraint tracks_albums_album_id_fk
    foreign key (track_id) references albums (album_id),
    constraint tracks_albums_album_id_fk_2
    foreign key (track_id) references albums (album_id)
);

create table if not exists artist_tracks
(
    artist_track_id int auto_increment
    primary key,
    artist_id       int not null,
    track_id        int not null,
    constraint artist_tracks_artist_id_uindex
    unique (artist_id),
    constraint artist_tracks_track_id_uindex
    unique (track_id),
    constraint artist_tracks_artists_artist_id_fk
    foreign key (artist_track_id) references artists (artist_id),
    constraint artist_tracks_tracks_track_id_fk
    foreign key (artist_track_id) references tracks (track_id)
);

create table if not exists users
(
    user_id      int auto_increment
    primary key,
    login        varchar(32)                         not null,
    password     varchar(64)                         not null,
    email        varchar(32)                         not null,
    banned       tinyint(1)                          not null,
    registration timestamp default CURRENT_TIMESTAMP not null,
    constraint users_email_uindex
    unique (email),
    constraint users_nickname_uindex
    unique (login)
);

create table if not exists playlists
(
    playlist_id int auto_increment
    primary key,
    name        varchar(32) not null,
    user_id     int         not null,
    constraint playlists_user_id_uindex
    unique (user_id),
    constraint playlists_users_user_id_fk
    foreign key (playlist_id) references users (user_id)
);

create table if not exists playlist_tracks
(
    playlist_track_id int auto_increment
    primary key,
    playlist_id       int not null,
    track_id          int not null,
    constraint playlist_tracks_playlist_id_uindex
    unique (playlist_id),
    constraint playlist_tracks_track_id_uindex
    unique (track_id),
    constraint playlist_tracks_playlists_playlist_id_fk
    foreign key (playlist_track_id) references playlists (playlist_id),
    constraint playlist_tracks_tracks_track_id_fk
    foreign key (playlist_track_id) references tracks (track_id)
);

create table if not exists user_liked_albums
(
    user_liked_album_id int auto_increment
    primary key,
    user_id             int not null,
    album_id            int not null,
    constraint user_liked_albums_album_id_uindex
    unique (album_id),
    constraint user_liked_albums_user_id_uindex
    unique (user_id),
    constraint user_liked_albums_albums_album_id_fk
    foreign key (user_liked_album_id) references albums (album_id),
    constraint user_liked_albums_users_user_id_fk
    foreign key (user_liked_album_id) references users (user_id)
);

create table if not exists user_liked_artists
(
    user_liked_artist_id int auto_increment
    primary key,
    user_id              int not null,
    artist_id            int not null,
    constraint user_liked_artists_artist_id_uindex
    unique (artist_id),
    constraint user_liked_artists_user_id_uindex
    unique (user_id),
    constraint user_liked_artists_artists_artist_id_fk
    foreign key (user_liked_artist_id) references artists (artist_id),
    constraint user_liked_artists_users_user_id_fk
    foreign key (user_liked_artist_id) references users (user_id)
);

create table if not exists user_liked_tracks
(
    user_liked_track_id int auto_increment
    primary key,
    user_id             int not null,
    track_id            int not null,
    constraint user_liked_tracks_track_id_uindex
    unique (track_id),
    constraint user_liked_tracks_user_id_uindex
    unique (user_id),
    constraint user_liked_tracks_tracks_track_id_fk
    foreign key (user_liked_track_id) references tracks (track_id),
    constraint user_liked_tracks_users_user_id_fk
    foreign key (user_liked_track_id) references users (user_id)
);

create table if not exists user_roles
(
    user_role_id int auto_increment
    primary key,
    user_id      int not null,
    role_id      int not null,
    constraint user_roles_roles_role_id_fk
    foreign key (user_role_id) references roles (role_id),
    constraint user_roles_users_user_id_fk
    foreign key (user_role_id) references users (user_id)
);
