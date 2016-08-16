package levina.web.model;

import levina.web.model.enums.StatusRequest;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by MY on 05.08.2016.
 */
public class Request {
    private Long requestID;
    private Long clientID;
    private Long roomID;
    private Timestamp requestDate;
    private Date startDate;
    private Date endDate;
    private int personsCount;
    private StatusRequest statusRequest;

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }


    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }

    public Long getClientID() {
        return clientID;
    }


    public Long getRoomID() {
        return roomID;
    }


    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPersonsCount() {
        return personsCount;
    }

    public void setPersonsCount(int personsCount) {
        this.personsCount = personsCount;
    }

    public StatusRequest getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(StatusRequest statusRequest) {
        this.statusRequest = statusRequest;
    }
}
