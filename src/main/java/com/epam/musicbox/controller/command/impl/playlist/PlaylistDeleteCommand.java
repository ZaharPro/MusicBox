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
        super(PlaylistServiceImpl.getInstance(), Parameter.PLAYLIST_ID);
    }
}
