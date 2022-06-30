package com.epam.musicbox.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        logger.info("attributeAdded: sessionId={} {}={}",
                event.getSession().getId(),
                event.getName(),
                event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        logger.info("attributeRemoved: sessionId={} {}={}",
                event.getSession().getId(),
                event.getName(),
                event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        logger.info("attributeReplaced: sessionId={} {}={}",
                event.getSession().getId(),
                event.getName(),
                event.getValue());
    }
}
