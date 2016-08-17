package levina.web.service.commands;

import levina.web.model.Client;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by MY on 14.08.2016.
 */
public class BookingCommand implements ActionCommand{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;

        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        Long userID = (Long)request.getSession().getAttribute("userID");
        Long clientID = null;
        boolean role = (boolean)request.getSession().getAttribute("role");
        if(role){
            clientID = (Long)request.getServletContext().getAttribute("id");
        }else{
            Client client = clientService.getByUserId(userID);
            clientID = client.getId();
        }
        Long roomID = null;
        Date start = null;
        Date end = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String startDateStr = request.getParameter("start");
        String endDateStr = request.getParameter("end");
        int numberSeats = Integer.parseInt(request.getParameter("number"));
        StatusRequest status = StatusRequest.PENDING;
        RoomType type = RoomType.valueOf(request.getParameter("type").toUpperCase());

        try {
            start = new Date(sdf.parse(startDateStr).getTime());
            end = new Date(sdf.parse(endDateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        requestService.createNew(clientID, roomID, type, start, end, numberSeats, status);
//        if(role){
//            page = "admin-home-prot.jsp";
//        }else {
//            page = "controller?command=booking_list";
//        }
        page = "controller?command=booking_list";
        return page;
    }
}
