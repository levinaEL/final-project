package levina.web.controllers.filters;

import levina.web.contants.IClientConstants;
import levina.web.contants.IRequestConstants;
import levina.web.contants.IUserConstants;
import levina.web.utils.ConfigurationManager;
import levina.web.utils.Validator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MY on 05.09.2016.
 */
@WebFilter(urlPatterns = "/controller")
public class ValidationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("create_client".equals(request.getParameter("command"))) {
            String firstName = request.getParameter(IClientConstants.FIRST_NAME);
            String lastName = request.getParameter(IClientConstants.LAST_NAME);
            String birthday = request.getParameter(IClientConstants.BIRTHDAY);
            String series = request.getParameter(IClientConstants.PASSPORT_SERIES);
            String number = request.getParameter(IClientConstants.PASSPORT_NUMBER);
            String personalNumber = request.getParameter(IClientConstants.PERSONAL_NUMBER);
            String email = request.getParameter(IClientConstants.EMAIL);
            String phone = request.getParameter(IClientConstants.PHONE);

            boolean isValidNames = Validator.validateName(firstName) && Validator.validateName(lastName);
            boolean isValidPassportInfo = Validator.validatePassportSeries(series) &&
                    Validator.validatePassportNumber(number) && Validator.validatePersonalNumber(personalNumber);
            boolean isValidBirthday = Validator.validateBirthday(birthday);
            boolean isValidEmail = Validator.validateEmail(email);
            boolean isValidPhone = Validator.validatePhone(phone);

            String path = ConfigurationManager.getProperty("path.action.get-client");

            if (!isValidNames) {
                request.setAttribute("errName", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else if (!isValidPassportInfo) {
                request.setAttribute("errPassport", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else if (!isValidBirthday) {
                request.setAttribute("errBirth", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else if (!isValidPhone) {
                request.setAttribute("errPhone", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else if (!isValidEmail) {
                request.setAttribute("errEmail", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }

        } else if ("register".equals(request.getParameter("command"))) {
            String login = request.getParameter(IUserConstants.LOGIN);
            String password = request.getParameter(IUserConstants.PASSWORD);

            boolean isValidLogin = Validator.validateUserName(login);
            boolean isValidPassword = Validator.validatePassword(password);

            String path = ConfigurationManager.getProperty("path.page.registration");

            if (!isValidLogin) {
                request.setAttribute("errLogin", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else if (!isValidPassword) {
                request.setAttribute("errPass", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } else if ("booking".equals(request.getParameter("command"))) {
            String startDate = request.getParameter(IRequestConstants.START_DATE);
            String endDate = request.getParameter(IRequestConstants.END_DATE);

            boolean isValidStartDate = Validator.validateDatesFormat(startDate)
                    && Validator.validateDatesFormat(endDate)
                    && Validator.checkDates(startDate, endDate);
            if (!isValidStartDate) {
                request.setAttribute("errMsg", true);
                RequestDispatcher dispatch =
                        request.getRequestDispatcher(ConfigurationManager.getProperty("path.action.get-request"));
                dispatch.forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }

        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
