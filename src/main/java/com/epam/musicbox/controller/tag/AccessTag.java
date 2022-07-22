package com.epam.musicbox.controller.tag;

import com.epam.musicbox.controller.Parameter;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * The type Access tag. Include body if actual role equals expected.
 */
public class AccessTag extends TagSupport {

    private String role;

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        String userRole = (String) session.getAttribute(Parameter.ROLE);
        return role.equals(userRole) ?
                EVAL_BODY_INCLUDE :
                SKIP_BODY;
    }
}