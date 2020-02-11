package code.challenge.models;

import java.util.Date;

public class Trip {

    private Date startTime;
    private Date endTime;
    private Float milesDriven;

    public Trip(Date startTime, Date endTime, Float milesDriven) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.milesDriven = milesDriven;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public float getMilesDriven() {
        return milesDriven;
    }
}
