package levina.web.controllers.filters;


import levina.web.constants.IUserConstants;
import levina.web.utils.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * PageRedirectSecurityFilter performs the function of controlling,
 * preventing direct access to pages without authorization
 */
@WebFilter("/jsp/*")
public class PageRedirectSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    /**
     * PageRedirectSecurityFilter performs the function of controlling,
     * preventing direct access to pages without authorization
     * @param req
     * @param res
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = ConfigurationManager.getProperty("path.page.login");

        boolean loggedIn = session != null && session.getAttribute(IUserConstants.USER_ID) != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

    public void destroy() {
    }
}
