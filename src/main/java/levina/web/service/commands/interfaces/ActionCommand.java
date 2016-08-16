package levina.web.service.commands.interfaces;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 11.08.2016.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request);
}
