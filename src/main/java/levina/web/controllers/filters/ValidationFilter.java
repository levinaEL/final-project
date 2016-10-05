package levina.web.controllers.filters;

import levina.web.constants.IClientConstants;
import levina.web.constants.IRequestConstants;
import levina.web.constants.IServiceConstants;
import levina.web.constants.IUserConstants;
import levina.web.utils.ConfigurationManager;
import levina.web.utils.ValidationUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ValidationFilter used for validate input data from the forms
 */
@WebFilter(urlPatterns = "/controller")
public class ValidationFilter implements Filter {
    public static final String CREATE = "create_client";
    public static final String REGISTER = "register";
    public static final String BOOKING = "booking";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Validate input data from the forms
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //create/update client personal info
        if (CREATE.equals(request.getParameter(IServiceConstants.COMMAND))) {
            String firstName = request.getParameter(IClientConstants.FIRST_NAME);
            String lastName = request.getParameter(IClientConstants.LAST_NAME);
            String birthday = request.getParameter(IClientConstants.BIRTHDAY);
            String series = request.getParameter(IClientConstants.PASSPORT_SERIES);
            String number = request.getParameter(IClientConstants.PASSPORT_NUMBER);
            String personalNumber = request.getParameter(IClientConstants.PERSONAL_NUMBER);
            String email = request.getParameter(IClientConstants.EMAIL);
            String phone = request.getParameter(IClientConstants.PHONE);

            boolean isValidNames = ValidationUtils.validateName(firstName) && ValidationUtils.validateName(lastName);
            boolean isValidPassportInfo = ValidationUtils.validatePassportSeries(series) &&
                    ValidationUtils.validatePassportNumber(number) && ValidationUtils.validatePersonalNumber(personalNumber);
            boolean isValidBirthday = ValidationUtils.validateBirthday(birthday);
            boolean isValidEmail = ValidationUtils.validateEmail(email);
            boolean isValidPhone = ValidationUtils.validatePhone(phone);

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

            //create user info
        } else if (REGISTER.equals(request.getParameter(IServiceConstants.COMMAND))) {
            String password = request.getParameter(IUserConstants.PASSWORD);
            boolean isValidPassword = ValidationUtils.validatePassword(password);
            String path = ConfigurationManager.getProperty("path.page.registration");

           if (!isValidPassword) {
                request.setAttribute("errPass", true);
                RequestDispatcher dispatch = request.getRequestDispatcher(path);
                dispatch.forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }

            //create/approve booking info
        } else if (BOOKING.equals(request.getParameter(IServiceConstants.COMMAND))) {
            String startDate = request.getParameter(IRequestConstants.START_DATE);
            String endDate = request.getParameter(IRequestConstants.END_DATE);

            boolean isValidStartDate = ValidationUtils.validateDatesFormat(startDate)
                    && ValidationUtils.validateDatesFormat(endDate)
                    && ValidationUtils.checkDates(startDate, endDate);
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
