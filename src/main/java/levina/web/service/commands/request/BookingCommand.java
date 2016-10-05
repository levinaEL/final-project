package levina.web.service.commands.request;

import levina.web.constants.IClientConstants;
import levina.web.constants.IRequestConstants;
import levina.web.constants.IUserConstants;
import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.service.logic.RoomService;
import levina.web.utils.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class BookingCommand implements ActionCommand {
    public final static Logger logger = Logger.getLogger(BookingCommand.class);
    public final String[] DATE_FORMATS = {"MM/dd/yyyy", "yyyy-MM-dd"};
    public static final String FREE_ROOMS = "availableRoom";

    private Date toSqlDate(java.util.Date date) {
        return new Date(date.getTime());
    }

    private Request getRequestFromParam(Map<String, String[]> paramMap) {
        Request request = new Request();

        request.setStartDate(toSqlDate(parseDate(paramMap.get(IRequestConstants.START_DATE)[0])));
        request.setEndDate(toSqlDate(parseDate(paramMap.get(IRequestConstants.END_DATE)[0])));
        request.setRoomType(RoomType.valueOf(paramMap.get(IRequestConstants.TYPE)[0]));
        request.setPersonsCount(Integer.parseInt(paramMap.get(IRequestConstants.PERSONS_COUNT)[0]));

        return request;
    }

    private java.util.Date parseDate(String value) {
        for (String dateFormat : DATE_FORMATS) {
            try {
                return (new SimpleDateFormat(dateFormat)).parse(value);
            } catch (ParseException e) {
                logger.error("Exception in parsing dates");
            }
        }
        return null;
    }

    /**
     * Execute all operation with booking(create, approve, cancel)
     *
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        Long roomID = null;
        Long clientID = null;

        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        RoomService roomService = new RoomService();

        boolean role = (boolean) request.getSession().getAttribute(IUserConstants.ROLE);
        Long userID = (Long) request.getSession().getAttribute(IUserConstants.USER_ID);
        Request requestItem = getRequestFromParam(request.getParameterMap());

        if (requestItem.getStartDate() == null || requestItem.getEndDate() == null) {
            logger.error("Wrong date format");
        }

        //if click show rooms
        if (request.getParameter(FREE_ROOMS) != null) {
            Collection<Room> rooms = roomService.getAvailableRooms(requestItem);
            if (rooms.isEmpty()) {
                request.setAttribute("roomNotFound", true);
            }
            request.setAttribute("rooms", rooms);
            page = ConfigurationManager.getProperty("path.page.free-rooms");
        } else {
            if (role) {
                // if admin create request for room
                if (StringUtils.isEmpty(request.getParameter(IRequestConstants.REQUEST_ID))) {
                    StatusRequest status;
                    if (!StringUtils.isEmpty(request.getParameter(IClientConstants.CLIENT_ID))) {
                        clientID = Long.parseLong(request.getParameter(IClientConstants.CLIENT_ID));
                    }
                    if (!StringUtils.isEmpty(request.getParameter(IRequestConstants.ROOM))) {
                        roomID = Long.parseLong(request.getParameter(IRequestConstants.ROOM));
                        status = StatusRequest.APPROVED;
                    } else {
                        status = StatusRequest.CANCEL;
                    }
                    requestItem.setClientID(clientID);
                    requestItem.setRoomID(roomID);
                    requestItem.setStatusRequest(status);
                    requestService.createNew(requestItem);
                } else { // if admin approve the clients request
                    Long requestId = Long.parseLong(request.getParameter(IRequestConstants.REQUEST_ID));

                    if (!StringUtils.isEmpty((request.getParameter(IRequestConstants.ROOM)))) {
                        roomID = Long.parseLong(request.getParameter(IRequestConstants.ROOM));
                        requestService.approve(requestId, roomID);
                    } else { //if there no available rooms, then request cancel
                        Map<Long, Boolean> reqCancel = new HashMap<>();
                        reqCancel.put(requestId, true);
                        request.getServletContext().setAttribute("reqCancel", reqCancel);
                        requestService.cancel(requestId);
                    }
                }
            } else { // if client create the request for room
                Client client = clientService.getByUserId(userID);
                requestItem.setClientID(client.getId());
                requestItem.setStatusRequest(StatusRequest.PENDING);
                requestService.createNew(requestItem);
            }
            page = ConfigurationManager.getProperty("path.action.booking-list");
        }
        return page;
    }
}
