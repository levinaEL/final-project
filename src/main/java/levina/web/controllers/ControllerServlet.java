package levina.web.controllers;

import levina.web.constants.IServiceConstants;
import levina.web.factories.ActionFactory;
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
 * ControllerServlet uses as controller of requests and responses
 */
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    public static final String LAST_COMMAND = "lastCommand";
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

    /**
     * Process request: get request parameter and based on this define the command coming from jsp,
     * execute it and generate response
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String paramLanguage = (String) session.getAttribute(LANGUAGE_KEY_NAME);
        String language = (session == null || StringUtils.isEmpty(paramLanguage)) ? LANGUAGE_DEFAULT : (paramLanguage);
        if (session != null) {
            session.setAttribute(LANGUAGE_KEY_NAME, language);
        }
        request.setAttribute(LANGUAGE_KEY_NAME, language);


        ActionFactory client = new ActionFactory();

        String commandParam = request.getParameter(IServiceConstants.COMMAND);
        ActionCommand command = client.defineCommand(commandParam); // define the command coming from JSP

        String page = command.execute(request, response);

        if((session = request.getSession(false)) != null) {
            session.setAttribute(LAST_COMMAND, commandParam);
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