package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

public class PlaylistSaveCommand implements Command {

    private static final String REDIRECT_URL = String.format("controller?command=%s&%s=",
            CommandType.EDIT_PLAYLIST_PAGE.getName(),
            Parameter.PLAYLIST_ID);

    public static final String PLAYLIST_PICTURE = Parameter.PLAYLIST + Parameter.PICTURE;

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            boolean deleteOnFail = false;
            Long playlistId = ParamTaker.getNullableLong(req, Parameter.PLAYLIST_ID);

            String name = req.getParameter(Parameter.NAME);
            if (playlistId == null) {
                Playlist playlist = new Playlist(null, name, null);
                playlistId = playlistService.save(playlist);
                deleteOnFail = true;
            }
            String picture;
            try {
                String key = FileServiceImpl.generateKey(PLAYLIST_PICTURE, playlistId);
                picture = fileService.put(req, key, Parameter.PICTURE, false,
                        FileService.IMG_DIR, validator::isValidImageFileName);
            } catch (ServiceException e) {
                if (deleteOnFail) {
                    playlistService.deleteById(playlistId);
                }
                throw new CommandException(e);
            }
            Playlist playlist = new Playlist(playlistId, name, picture);
            playlistService.save(playlist);
            return CommandResult.redirect(REDIRECT_URL + playlistId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
