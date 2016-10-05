package levina.web.service.commands.common;

import levina.web.service.commands.interfaces.ActionCommand;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *ChangeLanguageCommand use for set localization param
 */
public class ChangeLanguageCommand implements ActionCommand {

	public static final String LANGUAGE_KEY_NAME = "language";

    /**
     * used for set localization param
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String targetLang = request.getParameter(LANGUAGE_KEY_NAME);
		if (session != null && !StringUtils.isEmpty(targetLang)) {
			session.setAttribute(LANGUAGE_KEY_NAME, targetLang);
			request.setAttribute(LANGUAGE_KEY_NAME, targetLang);
		}
		return "RD:"+request.getRequestURL() + "?command=" + session.getAttribute("lastCommand");
	}
}
