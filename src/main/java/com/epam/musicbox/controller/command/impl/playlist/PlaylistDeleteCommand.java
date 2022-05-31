package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.impl.common.DeleteCommand;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.AuthUtils;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class PlaylistDeleteCommand extends DeleteCommand<Playlist> {

    public PlaylistDeleteCommand() {
        super(PlaylistServiceImpl.getInstance(), Parameter.USER_ID);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Jws<Claims> claimsJws = AuthUtils.getClaimsJws(req);
        Claims body = claimsJws.getBody();

        long userId = Parameters.get(body, Parameter.USER_ID);
        long playlistId = Parameters.getLong(req, Parameter.PLAYLIST_ID);

        Role role = Parameters.get(body, Parameter.NAME);
        if (role == Role.ADMIN) {
            service.deleteById(playlistId);
            return;
        }
        Optional<Playlist> optionalPlaylist = service.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();
            Long playlistUserId = playlist.getUserId();
            if (playlistUserId != null && userId == playlistUserId) {
                service.deleteById(playlistId);
            }
        }
    }
}
