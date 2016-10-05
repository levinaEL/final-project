package levina.web.service.commands.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ActionCommand instances used for declaring app command
 */
public interface ActionCommand {
    /**
     * Called for executing command
     *
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
