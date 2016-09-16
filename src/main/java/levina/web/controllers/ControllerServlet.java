package levina.web.controllers;

import levina.web.service.ActionFactory;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.utils.ConfigurationManager;
import levina.web.utils.MessageManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by MY on 10.08.2016.
 */
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    public static final String LANGUAGE_DEFAULT = "en";
    public static final String LANGUAGE_KEY_NAME = "language";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {

        String page;
        HttpSession session = request.getSession(false);
        String paramLanguage = (String) session.getAttribute(LANGUAGE_KEY_NAME);
        String language = (session == null || StringUtils.isEmpty(paramLanguage)) ? LANGUAGE_DEFAULT : (paramLanguage);
        if (session != null) {
            session.setAttribute(LANGUAGE_KEY_NAME, language);
        }
        request.setAttribute(LANGUAGE_KEY_NAME, language);

        // определение команды, пришедшей из JSP
        ActionFactory client = new ActionFactory();
        String commandParam = request.getParameter("command");
        ActionCommand command = client.defineCommand(commandParam);

        page = command.execute(request, response);

        if((session = request.getSession(false)) != null) {
            session.setAttribute("lastCommand", commandParam);
        }

        if (page.startsWith("RD:")) {
            response.sendRedirect(page.substring(3));
            return;
        }

        if (page != null) {
            RequestDispatcher dispatch = request.getRequestDispatcher(page);
            dispatch.forward(request, response);
        } else {

            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}