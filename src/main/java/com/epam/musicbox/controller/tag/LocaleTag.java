package com.epam.musicbox.controller.tag;

import com.epam.musicbox.controller.Parameter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The type Locale tag. Print locale name.
 */
public class LocaleTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger();

    private static final String LOCALE_EN = "en_EN";
    private static final String LOCALE_RU = "ru_RU";
    private static final String EN = "En";
    private static final String RU = "Ru";

    @Override
    public int doStartTag() throws JspTagException {
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(Parameter.LOCALE);
        String label = toLabel(locale);
        if (label == null) {
            return SKIP_BODY;
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(label);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }

    private static String toLabel(String locale) {
        if (LOCALE_EN.equals(locale)) {
            return EN;
        }
        if (LOCALE_RU.equals(locale)) {
            return RU;
        }
        return null;
    }
}