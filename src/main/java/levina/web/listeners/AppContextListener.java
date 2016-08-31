package levina.web.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by MY on 31.08.2016.
 */
@WebListener
public class AppContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        System.setProperty("rootPath", context.getRealPath("../../"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
