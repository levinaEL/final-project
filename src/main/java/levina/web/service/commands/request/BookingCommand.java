package levina.web.service.commands.request;

import levina.web.model.Client;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.service.logic.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Created by MY on 14.08.2016.
 */
public class BookingCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        Long roomID = null;
        Date start = null;
        Date end = null;
        int numberSeats = 0;
        RoomType type = null;
        StatusRequest status;

        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        RoomService roomService = new RoomService();

        boolean role = (boolean) request.getSession().getAttribute("role");
        Long userID = (Long) request.getSession().getAttribute("userID");
        Long clientID;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String startDateStr = request.getParameter("start");
        String endDateStr = request.getParameter("end");
        if(request.getParameter("number")!=null) {
            numberSeats = Integer.parseInt(request.getParameter("number"));
        }
        if(request.getParameter("type")!=null) {
            type = RoomType.valueOf(request.getParameter("type").toUpperCase());
        }


        if(request.getParameter("start")!= null && request.getParameter("end")!=null)
        try {
            start = new Date(sdf.parse(startDateStr).getTime());
            end = new Date(sdf.parse(endDateStr).getTime());
        } catch (ParseException parseEx) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                start = new Date(sdf.parse(startDateStr).getTime());
                end = new Date(sdf.parse(endDateStr).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (request.getParameter("availableRoom") != null) {
            Collection<Room> rooms = roomService.getAllClientsRequests(start, end, numberSeats, type);
            if (rooms.isEmpty()) {
                request.getServletContext().setAttribute("roomNotFound", true);
            }
            request.setAttribute("rooms", rooms);
            page = "available-rooms.jsp";
        } else {
            if (role) {
                if (request.getServletContext().getAttribute("requestId").equals("")) {
                    clientID = (Long)request.getServletContext().getAttribute("clientId");
                    if (request.getServletContext().getAttribute("roomNotFound") == null) {
                        roomID = Long.parseLong(request.getParameter("roomId"));
                        status = StatusRequest.APPROVED;
                    }else{
                        status = StatusRequest.CANCEL;
                    }
                    requestService.createNew(clientID, roomID, type, start, end, numberSeats, status);
                } else {
                    Long requestId = (Long) request.getServletContext().getAttribute("requestId");

                    if (request.getServletContext().getAttribute("roomNotFound") == null) {
                        roomID = Long.parseLong(request.getParameter("roomId"));
                        requestService.approve(requestId, roomID);
                    } else {
                        requestService.delete(requestId);
                    }
                }
            } else {
                Client client = clientService.getByUserId(userID);
                clientID = client.getId();
                status = StatusRequest.PENDING;
                requestService.createNew(clientID, roomID, type, start, end, numberSeats, status);

            }
            page = "controller?command=booking_list";
        }
        return page;
    }
}
