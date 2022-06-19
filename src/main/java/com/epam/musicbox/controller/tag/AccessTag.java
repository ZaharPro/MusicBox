package com.epam.musicbox.controller.tag;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.entity.Role;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.tagext.TagSupport;

public class AccessTag extends TagSupport {

    public static final String NOT_GUEST = "not-guest";

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        String roleName = (String) session.getAttribute(Parameter.ROLE);
        return testName(roleName) ?
                EVAL_BODY_INCLUDE :
                SKIP_BODY;
    }

    private boolean testName(String roleName) {
        if (roleName == null) {
            roleName = Role.GUEST.getValue();
        }
        return NOT_GUEST.equals(role) ?
                !roleName.equals(Role.GUEST.getValue()) :
                roleName.equals(role);
    }
}