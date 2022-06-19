package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class GoToEditTrackPageCommand extends GoToPageCommand {

    private static final char URL_ATTRIBUTE_DELIMITER = '&';
    private static final char URL_ATTRIBUTE_EQ = '=';
    private static final String NAV_COMMAND = "navcommand";
    private static final String ALBUM_CHOOSE_COMMAND = "albumchoosecommand";

    private final TrackService trackService = TrackServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    public GoToEditTrackPageCommand() {
        super(PagePath.EDIT_TRACK);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            StringBuilder navCommandBuilder = new StringBuilder();
            StringBuilder albumChooseCommandBuilder = new StringBuilder();
            navCommandBuilder.append(CommandType.GO_TO_EDIT_TRACK_PAGE.getName());
            albumChooseCommandBuilder.append(CommandType.GO_TO_EDIT_TRACK_PAGE.getName());

            Long trackId = ParamTaker.getNullableLong(req, Parameter.TRACK_ID);
            if (trackId != null) {
                Optional<Track> optionalTrack = trackService.findById(trackId);
                Track track = optionalTrack.orElse(null);
                req.setAttribute(Parameter.TRACK, track);
                navCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                        .append(Parameter.TRACK_ID)
                        .append(URL_ATTRIBUTE_EQ)
                        .append(trackId);

                albumChooseCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                        .append(Parameter.TRACK_ID)
                        .append(URL_ATTRIBUTE_EQ)
                        .append(trackId);
            }

            Long albumId = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);
            if (albumId != null) {
                Optional<Album> optional = albumService.findById(albumId);
                Album album = optional.orElse(null);
                req.setAttribute(Parameter.ALBUM, album);
                navCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                        .append(Parameter.ALBUM_ID)
                        .append(URL_ATTRIBUTE_EQ)
                        .append(albumId);
            }

            String navCommand = navCommandBuilder.toString();
            req.setAttribute(NAV_COMMAND, navCommand);

            int page = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int pageSize = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> pageSearchResult = albumService.findPage(page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, pageSearchResult);

            albumChooseCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                    .append(Parameter.ALBUM_PAGE_INDEX)
                    .append(URL_ATTRIBUTE_EQ)
                    .append(pageSearchResult.getPage());
            String albumChooseCommand = albumChooseCommandBuilder.toString();
            req.setAttribute(ALBUM_CHOOSE_COMMAND, albumChooseCommand);

            return super.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
