package levina.web.controllers;

import levina.web.service.ActionFactory;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.utils.ConfigurationManager;
import levina.web.utils.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MY on 10.08.2016.
 */
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

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
        // определение команды, пришедшей из JSP
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);

        page = command.execute(request, response);

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