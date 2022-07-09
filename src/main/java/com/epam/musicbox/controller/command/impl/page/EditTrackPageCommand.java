package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditTrackPageCommand extends PageCommand {

    private static final String TRACK_NOT_FOUND_MSG = "Track not found";
    private static final String ALBUM_NOT_FOUND_MSG = "Album not found";

    private static final char URL_ATTRIBUTE_DELIMITER = '&';
    private static final char URL_ATTRIBUTE_EQ = '=';
    private static final String NAV_COMMAND = "navcommand";
    private static final String ALBUM_CHOOSE_COMMAND = "albumchoosecommand";

    private final TrackService trackService = TrackServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    public EditTrackPageCommand() {
        super(PagePath.EDIT_TRACK);
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            StringBuilder navCommandBuilder = new StringBuilder();
            StringBuilder albumChooseCommandBuilder = new StringBuilder();
            navCommandBuilder.append(CommandType.EDIT_TRACK_PAGE.getName());
            albumChooseCommandBuilder.append(CommandType.EDIT_TRACK_PAGE.getName());

            Optional<Long> optionalAlbumId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);

            Optional<Long> optionalTrackId = ParameterTaker.getOptionalLong(req, Parameter.TRACK_ID);
            if (optionalTrackId.isPresent()) {
                long trackId = optionalTrackId.get();
                Track track = trackService.findById(trackId)
                        .orElseThrow(() -> new CommandException(TRACK_NOT_FOUND_MSG));
                req.setAttribute(Parameter.TRACK, track);

                if (optionalAlbumId.isEmpty()) {
                    optionalAlbumId = Optional.of(track.getAlbumId());
                }
                navCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                        .append(Parameter.TRACK_ID)
                        .append(URL_ATTRIBUTE_EQ)
                        .append(trackId);

                albumChooseCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                        .append(Parameter.TRACK_ID)
                        .append(URL_ATTRIBUTE_EQ)
                        .append(trackId);
            }

            if (optionalAlbumId.isPresent()) {
                long albumId = optionalAlbumId.get();
                Album album = albumService.findById(albumId)
                        .orElseThrow(() -> new CommandException(ALBUM_NOT_FOUND_MSG));
                req.setAttribute(Parameter.ALBUM, album);
                navCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                        .append(Parameter.ALBUM_ID)
                        .append(URL_ATTRIBUTE_EQ)
                        .append(albumId);
            }

            String navCommand = navCommandBuilder.toString();
            req.setAttribute(NAV_COMMAND, navCommand);

            int page = ParameterTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> pageSearchResult = albumService.findPage(page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, pageSearchResult);

            albumChooseCommandBuilder.append(URL_ATTRIBUTE_DELIMITER)
                    .append(Parameter.ALBUM_PAGE_INDEX)
                    .append(URL_ATTRIBUTE_EQ)
                    .append(pageSearchResult.getPage());
            String albumChooseCommand = albumChooseCommandBuilder.toString();
            req.setAttribute(ALBUM_CHOOSE_COMMAND, albumChooseCommand);

            Router router = super.execute(req);
            router.setCache(false);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
