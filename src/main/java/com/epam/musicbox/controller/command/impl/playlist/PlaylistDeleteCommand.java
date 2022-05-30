package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.Parameters;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class PlaylistDeleteCommand implements Command {
    @Inject
    private PlaylistService playlistService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        HttpSession session = req.getSession();
        long userId = Parameters.get(session, Parameter.USER_ID);
        long playlistId = Parameters.getLong(req, Parameter.PLAYLIST_ID);

        Optional<Playlist> optionalPlaylist = playlistService.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();
            Long playlistUserId = playlist.getUserId();
            if (playlistUserId != null && userId == playlistUserId) {
                playlistService.deleteById(playlistId);
            }
        }
    }
}
