package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class TrackGetByNameCommand implements Command {

    private static final String COMMAND = String.format("%s&%s=",
            CommandType.TRACK_GET_BY_NAME.getName(),
            Parameter.NAME);

    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            String name = req.getParameter(Parameter.NAME);
            int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int pageSize = ParamTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
            PageSearchResult<Track> pageSearchResult = trackService.findByName(name, page, pageSize);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, pageSearchResult);
            req.setAttribute(Parameter.COMMAND, COMMAND + name);
            return CommandResult.forward(PagePath.TRACKS);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
