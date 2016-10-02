package levina.web.utils;

import levina.web.constants.IServiceConstants;
import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.model.Room;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RoomService;

import java.util.*;

/**
 * Created by MY on 30.08.2016.
 */
public class ClientsUtils {

    public static Set<Long> clientsNeededSale(Map<Long, Integer> clientCountMap) {
        Set<Long> clientsForSale = new HashSet<>();
        for (Map.Entry entry : clientCountMap.entrySet()) {
            int count = (int) entry.getValue();
            if (count > IServiceConstants.COUNT_REQUEST_FOR_SALE) {
                clientsForSale.add((Long) entry.getKey());
            }
        }
        return clientsForSale;
    }

    public static Collection<String> getClientsName(Collection<Request> requests){
        Collection<String> namesCollection = new ArrayList<>();
        ClientService clientService = new ClientService();
        for(Request request : requests){
            Long id = request.getClientID();
            Client client = clientService.getById(id);
            namesCollection.add(client.getLastName());
        }

        return namesCollection;
    }

    public static Collection<Double> getClientsCost(Collection<Request> requests){
        Collection<Double> costCollection = new ArrayList<>();
        RoomService roomService = new RoomService();
        Double cost;
        for(Request request : requests){
            Long roomId = request.getRoomID();
            if(roomId != null) {
                Room room = roomService.getById(roomId);
                long diff = Math.abs(request.getEndDate().getTime() - request.getStartDate().getTime());
                cost = room.getCost() * diff / (24 * 60 * 60 * 1000);
            }else{
                cost = null;
            }
            costCollection.add(cost);
        }
        return costCollection;
    }

}
