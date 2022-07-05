package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.FileServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.validator.FileValidator;
import com.epam.musicbox.util.validator.impl.FileValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class TrackSaveCommand implements Command {

    private static final String TRACK_NOT_FOUND_MSG = "Track not found";
    private static final String INVALID_NAME_MSG = "Invalid name";

    private static final String DEFAULT_TRACK_NAME = "Track";
    public static final String TRACK_AUDIO = Parameter.TRACK + Parameter.AUDIO;

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?%s=%s&%s=%%s&%s=%%s&%s=%%s&%s=%%s",
                    Parameter.COMMAND,
                    CommandType.EDIT_TRACK_PAGE.getName(),
                    Parameter.TRACK_ID,
                    Parameter.ALBUM_ID,
                    Parameter.ALBUM_PAGE_INDEX,
                    Parameter.ALBUM_PAGE_SIZE);

    private final TrackService trackService = TrackServiceImpl.getInstance();
    private final FileService fileService = FileServiceImpl.getInstance();
    private final FileValidator fileValidator = FileValidatorImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Track track;
            Optional<Long> optionalId = ParameterTaker.getOptionalLong(req, Parameter.TRACK_ID);
            if (optionalId.isPresent()) {
                long id = optionalId.get();
                track = trackService.findById(id).
                        orElseThrow(() -> new CommandException(TRACK_NOT_FOUND_MSG));
                Optional<Long> albumId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
                albumId.ifPresent(track::setAlbumId);
                fillTrack(req, track);
            } else {
                long albumId = ParameterTaker.getLong(req, Parameter.ALBUM_ID);
                track = new Track(null, DEFAULT_TRACK_NAME, null, albumId);
                long id = trackService.save(track);
                track.setId(id);
                try {
                    fillTrack(req, track);
                } catch (Exception e) {
                    trackService.deleteById(id);
                    throw new CommandException(e.getMessage(), e);
                }
            }
            trackService.save(track);
            int albumPage = ParameterTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int albumPageSize = ParameterTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);
            String url = String.format(REDIRECT_URL_FORMAT,
                    track.getId(),
                    track.getAlbumId(),
                    albumPage,
                    albumPageSize);
            return Router.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    private void fillTrack(HttpServletRequest req, Track track) throws ServiceException {
        Optional<String> optionalName = ParameterTaker.getName(req);
        String name = optionalName.orElseThrow(() -> new ServiceException(INVALID_NAME_MSG));
        track.setName(name);
        String key = FileServiceImpl.generateKey(TRACK_AUDIO, track.getId());
        String audio = fileService.put(req, key, Parameter.AUDIO, false,
                Parameter.AUDIO_DIR, fileValidator::isValidAudioFileName);
        if (audio != null) {
            track.setAudio(audio);
        }
    }
}
