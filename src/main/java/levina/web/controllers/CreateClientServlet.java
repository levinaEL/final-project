package levina.web.controllers;

import levina.web.model.Client;
import levina.web.service.logic.ClientService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by MY on 08.08.2016.
 */
@WebServlet("/PersonalInfo")
public class CreateClientServlet extends HttpServlet {

    public final boolean ADMIN = true;
    public final boolean USER = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = null;
        Long id = null;
        ClientService clientService = new ClientService();
        HttpSession session = req.getSession();
        boolean role = (boolean) session.getAttribute("role");
        Long userID = (Long) session.getAttribute("userID");

        if (role && !req.getParameter("id").equals("")) {
            id = Long.parseLong(req.getParameter("id"));
            client = clientService.getById(id);
        } else {
            client = clientService.getByUserId(userID);
        }
//        if ( !req.getParameter("id").equals("")) {
//            id = Long.parseLong(req.getParameter("id"));
//            client = clientService.getById(id);
//        }

        if (client == null) {
            client = new Client();
        }

        req.setAttribute("client", client);
        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("clientId", client.getId());
        RequestDispatcher dispatch = req.getRequestDispatcher("personal-info.jsp");
        dispatch.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService clientService = new ClientService();

        HttpSession session = req.getSession();
        Long userID = (Long) session.getAttribute("userID");

        Client client = null;
        Long id = null;
        boolean role = (boolean) session.getAttribute("role");

        if (!req.getParameter("id").equals("")) {
            id = Long.parseLong(req.getParameter("id"));
            client = clientService.getById(id);

        }

        String firstName = req.getParameter("fname");
        String patronName = req.getParameter("pname");
        String lastName = req.getParameter("lname");
        String pSeries = req.getParameter("pSeries");
        String email = req.getParameter("email");
        int pNumber = Integer.parseInt(req.getParameter("pNumber"));
        String personalNumb = req.getParameter("prslNumber");
        String address = req.getParameter("address");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        boolean ban = Boolean.parseBoolean(req.getParameter("ban"));
        if (client != null) {
            client.setId(id);
            if (role) {
                client.setUserID(null);
            } else {
                client.setUserID(userID);
            }
            client.setFirstName(firstName);
            client.setPatronymicName(patronName);
            client.setLastName(lastName);
            client.setPassportSeries(pSeries);
            client.setEmail(email);
            client.setPassportNumber(pNumber);
            client.setPersonalNumber(personalNumb);
            client.setAddress(address);
            client.setBirthday(birthday);
            client.setPhoneNumber(phone);
            client.setBan(ban);

            clientService.update(client);
        } else {
            if (role) {
                clientService.createNew(null, email, firstName, patronName, lastName, address, phone, pSeries, pNumber, personalNumb,
                        birthday);
            } else {
                clientService.createNew(userID, email, firstName, patronName, lastName, address, phone, pSeries, pNumber, personalNumb,
                        birthday);
            }
        }
        if (role) {
            resp.sendRedirect("admin-home-prot.jsp");
        } else {
            RequestDispatcher dispatch = req.getRequestDispatcher("client-home-prot.jsp");
            dispatch.forward(req, resp);
        }

    }


}
