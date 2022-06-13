package com.epam.musicbox.util.tag;

import com.epam.musicbox.util.constant.Parameter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LocaleTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger();

    public static final String LOCALE_EN = "en_EN";
    public static final String LOCALE_RU = "ru_RU";
    public static final String EN = "En";
    public static final String RU = "Ru";

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