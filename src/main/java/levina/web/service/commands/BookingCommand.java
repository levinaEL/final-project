package levina.web.service.commands;

import levina.web.model.Client;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by MY on 14.08.2016.
 */
public class BookingCommand implements ActionCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        //ServletContext servletContext = request.getServletContext();
        //Long clientID = (Long)servletContext.getAttribute("clientId");
        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        Long userID = (Long)request.getSession().getAttribute("userID");
        Client client = clientService.getByUserId(userID);
        Long clientID = client.getId();
        Long roomID = null;
        Date start = null;
        Date end = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String startDateStr = request.getParameter("start");
        String endDateStr = request.getParameter("end");

        try {
            start = new Date(sdf.parse(startDateStr).getTime());
            end = new Date(sdf.parse(endDateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        int numberSeats = Integer.parseInt(request.getParameter("number"));
        StatusRequest status = StatusRequest.PENDING;
        RoomType type = RoomType.valueOf(request.getParameter("type").toUpperCase());
        request.getSession().setAttribute("room_type", type);

        requestService.createNew(clientID, roomID, start, end, numberSeats, status);
        page = "client-home-prot.jsp";
        return page;
    }
}
