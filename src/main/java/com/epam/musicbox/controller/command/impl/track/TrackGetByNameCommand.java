package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class TrackGetByNameCommand implements Command {

    private static final String COMMAND = String.format("%s&%s=",
            CommandType.TRACK_GET_BY_NAME.getName(),
            Parameter.NAME);

    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<String> optionalName = ParameterTaker.getName(req);
            int page = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
            PageSearchResult<Track> pageSearchResult = null;
            if (optionalName.isPresent()) {
                pageSearchResult = trackService.findByName(optionalName.get(), page, pageSize);
            }
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, pageSearchResult);
            req.setAttribute(Parameter.COMMAND, COMMAND + optionalName.orElse(""));
            return Router.forward(PagePath.TRACKS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
