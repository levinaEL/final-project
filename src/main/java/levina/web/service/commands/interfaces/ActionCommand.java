package levina.web.service.commands.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 11.08.2016.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
