package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

public class TrackSaveCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?command=%s&%s=%%s&%s=%%s&%s=%%s",
                    CommandType.EDIT_TRACK_PAGE.getName(),
                    Parameter.TRACK_ID,
                    Parameter.ALBUM_ID,
                    Parameter.ALBUM_PAGE_SIZE);

    public static final String TRACK_AUDIO = Parameter.TRACK + Parameter.AUDIO;

    private final TrackService trackService = TrackServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            boolean deleteOnFail = false;
            Long trackId = ParamTaker.getNullableLong(req, Parameter.TRACK_ID);

            String name = req.getParameter(Parameter.NAME);
            Long albumId = ParamTaker.getLong(req, Parameter.ALBUM_ID);
            if (trackId == null) {
                Track track = new Track(null, name, null, albumId);
                trackId = trackService.save(track);
                deleteOnFail = true;
            }
            String audio;
            try {
                String key = FileServiceImpl.generateKey(TRACK_AUDIO, trackId);
                audio = fileService.put(req, key, Parameter.AUDIO, false,
                        Parameter.AUDIO_DIR, validator::isValidAudioFileName);
            } catch (ServiceException e) {
                if (deleteOnFail) {
                    trackService.deleteById(trackId);
                }
                throw new CommandException(e);
            }
            Track track = new Track(trackId, name, audio, albumId);
            trackService.save(track);

            int albumPage = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            String url = String.format(REDIRECT_URL_FORMAT,
                    track.getId(),
                    albumId,
                    albumPage);
            return CommandResult.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
