package com.epam.musicbox.util;

import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Pages {
    public static void forward(HttpServletRequest req, HttpServletResponse resp, String page) throws HttpException {
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new HttpException(e);
        }
    }
    public static void redirect(HttpServletResponse resp, String page) throws HttpException {
        try {
            resp.sendRedirect(page);
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
}
