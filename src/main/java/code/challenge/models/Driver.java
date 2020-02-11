package code.challenge.models;

import java.util.ArrayList;
import java.util.List;

public class Driver implements Comparable<Driver>{

    private String name;
    private List<Trip> trips = new ArrayList<>();
    private Float totalDistance = 0.0f;
    private Float totalTime = 0.0f;
    private Float averageSpeed = 0.0f;

    public Driver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public Float getTotalDistance() {
        return totalDistance;
    }

    public Float getTotalTime() {
        return totalTime;
    }

    public Float getAverageSpeed() {
        return averageSpeed;
    }

    public void setTotalDistance(Float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalTime(Float totalTime) {
        this.totalTime = totalTime;
    }

    public void setAverageSpeed(Float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public int compareTo(Driver driver) {
        return (int)(driver.getTotalDistance() - this.getTotalDistance());
    }
}
