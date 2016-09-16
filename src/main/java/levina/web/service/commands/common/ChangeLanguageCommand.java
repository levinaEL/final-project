package levina.web.service.commands.common;

import levina.web.service.commands.interfaces.ActionCommand;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Alexey on 14.09.2016.
 */
public class ChangeLanguageCommand implements ActionCommand {

	public static final String LANGUAGE_KEY_NAME = "language";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String targLang = request.getParameter(LANGUAGE_KEY_NAME);
		if (session != null && !StringUtils.isEmpty(targLang)) {
			session.setAttribute(LANGUAGE_KEY_NAME, targLang);
			request.setAttribute(LANGUAGE_KEY_NAME, targLang);
		}
		return "RD:"+request.getRequestURL() + "?command=" + session.getAttribute("lastCommand");
	}
}
