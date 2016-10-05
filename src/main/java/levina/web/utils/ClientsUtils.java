package levina.web.utils;

import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.model.Room;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RoomService;

import java.util.*;


public class ClientsUtils {
    /**
     * the required number of requests for sale
     */
    public static final int COUNT_REQUEST_FOR_SALE = 3;

    /**
     * Forms the set of clients which need a sale
     * @param clientCountMap - Map: key - client id, value - count of the requests
     * @return Set
     */
    public static Set<Long> clientsNeededSale(Map<Long, Integer> clientCountMap) {
        Set<Long> clientsForSale = new HashSet<>();
        for (Map.Entry entry : clientCountMap.entrySet()) {
            int count = (int) entry.getValue();
            if (count > COUNT_REQUEST_FOR_SALE) {
                clientsForSale.add((Long) entry.getKey());
            }
        }
        return clientsForSale;
    }

    /**
     * Forms the collection of clients names
     * @param requests - collection of all current requests
     * @return Collection
     */
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

    /**
     * Count for al clients cost for their trip
     * @param requests - collection of all current requests
     * @return Collection
     */
    public static Collection<Double> getClientsCost(Collection<Request> requests){
        Collection<Double> costCollection = new ArrayList<>();
        RoomService roomService = new RoomService();
        Double cost;
        for(Request request : requests){
            Long roomId = request.getRoomID();
            if(roomId != null) {
                Room room = roomService.getById(roomId);
                long diff = Math.abs(request.getEndDate().getTime() - request.getStartDate().getTime());
                cost = room.getCost() * diff / (24 * 60 * 60 * 1000); //for getting proper format of days
            }else{
                cost = null;
            }
            costCollection.add(cost);
        }
        return costCollection;
    }

}
