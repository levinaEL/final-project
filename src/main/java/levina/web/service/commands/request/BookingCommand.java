package levina.web.service.commands.request;

import levina.web.model.Client;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.service.logic.RoomService;
import levina.web.utils.ConfigurationManager;
import org.apache.log4j.Logger;

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
    public final static Logger logger = Logger.getLogger(BookingCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        Long roomID = null;
        Long clientID = null;
        Date start = null;
        Date end = null;
        StatusRequest status;

        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        RoomService roomService = new RoomService();

        boolean role = (boolean) request.getSession().getAttribute("role");
        Long userID = (Long) request.getSession().getAttribute("userID");

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        String startDateStr = request.getParameter("start");
        String endDateStr = request.getParameter("end");
        int numberSeats = Integer.parseInt(request.getParameter("number"));
        RoomType type = RoomType.valueOf(request.getParameter("type").toUpperCase());

        try {
            start = new Date(sdf.parse(startDateStr).getTime());
            end = new Date(sdf.parse(endDateStr).getTime());
        } catch (ParseException parseEx) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                start = new Date(sdf.parse(startDateStr).getTime());
                end = new Date(sdf.parse(endDateStr).getTime());
            } catch (ParseException e) {
                logger.error("Wrong date format", e);
            }
        }

        //if click show rooms
        if (request.getParameter("availableRoom") != null) {
            Collection<Room> rooms = roomService.getAllClientsRequests(start, end, numberSeats, type);
            if (rooms.isEmpty()) {
                request.getSession().setAttribute("roomNotFound", true);
            }
            request.setAttribute("rooms", rooms);
            page = ConfigurationManager.getProperty("path.page.free-rooms");
        } else {
            if (role) {
                // if admin create request for room
                if (request.getParameter("requestId").equals("")) {
                    if (!request.getParameter("clientId").equals("")) {
                        clientID = Long.parseLong(request.getParameter("clientId"));
                    }
                    if (request.getSession().getAttribute("roomNotFound") == null) {
                        roomID = Long.parseLong(request.getParameter("roomId"));
                        status = StatusRequest.APPROVED;
                    } else {
                        status = StatusRequest.CANCEL;
                    }
                    requestService.createNew(clientID, roomID, type, start, end, numberSeats, status);
                } else { // if admin approve the clients request
                    Long requestId = Long.parseLong(request.getParameter("requestId"));

                    if (request.getSession().getAttribute("roomNotFound") == null) {
                        roomID = Long.parseLong(request.getParameter("roomId"));
                        requestService.approve(requestId, roomID);
                    } else {
                        requestService.delete(requestId);
                    }
                }

            } else { // if client create the request for room
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
