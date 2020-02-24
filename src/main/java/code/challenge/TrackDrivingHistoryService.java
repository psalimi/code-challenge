package code.challenge;

import code.challenge.models.Driver;
import code.challenge.models.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class TrackDrivingHistoryService {

    private final static String DRIVER = "driver";
    private final static String TRIP = "trip";
    private final static Logger LOGGER = LoggerFactory.getLogger(TrackDrivingHistory.class);


    public TrackDrivingHistoryService() {

    }

    public boolean isDataValid(String line, int requiredLength) {

        if (line == null) {
            return false;
        }

        String[] data = line.split(" ");

        if (data.length != requiredLength) {
            return false;
        }

        for (String datum : data) {
            if (datum.isEmpty() || datum.isBlank()) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidTrip(Trip trip) {
        if (trip != null) {
            float duration = calculateDuration(trip.getStartTime(), trip.getEndTime());
            float mph = calculateMPHRate(duration, trip.getMilesDriven());
            return 5f < mph && mph < 100f;
        }

        return false;
    }

    private float calculateMPHRate(float time, float distance) {
        return distance / time;
    }

    private float calculateDuration(Date startTime, Date endTime) {
        return (endTime.getTime() - startTime.getTime()) / (1000f * 60f * 60f);
    }

    public void addTripForDriver(Driver driver, Trip trip) {
        driver.getTrips().add(trip);
        driver.setTotalDistance(driver.getTotalDistance() + trip.getMilesDriven());
        driver.setTotalTime(driver.getTotalTime() + calculateDuration(trip.getStartTime(), trip.getEndTime()));
        driver.setAverageSpeed(calculateMPHRate(driver.getTotalTime(), driver.getTotalDistance()));
    }

    public Driver createDriver(String[] data) {
        return new Driver(data[1]);
    }

    public Trip createTrip(String[] data) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date startTime = format.parse(data[2]);
            Date endTime = format.parse(data[3]);
            float milesDriven = Float.parseFloat(data[4]);
            return new Trip(startTime, endTime, milesDriven);
        } catch (ParseException | ArrayIndexOutOfBoundsException exception) {
            LOGGER.error("Please enter a valid trip", exception);
        }

        return null;
    }

    public boolean isCommandDriver(String line) {
        return line != null && isDataValid(line, 2) && line.toLowerCase().startsWith(DRIVER);
    }

    public boolean isCommandTrip(String line) {
        return line != null && isDataValid(line, 5) && line.toLowerCase().startsWith(TRIP);
    }

    public void createDrivingReport(Map<String, Driver> drivers) {

        StringBuilder text = new StringBuilder();
        drivers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    Driver driver = entry.getValue();
                    text.append(driver.getName())
                            .append(": ")
                            .append(Math.round(driver.getTotalDistance()))
                            .append(" miles");

                    if (Math.round(driver.getTotalDistance()) > 0) {
                        text.append(" @")
                                .append(Math.round(driver.getAverageSpeed()))
                                .append(" mph");
                    }

                    text.append("\n");
                });

        createFile(text.toString());
    }

    private void createFile(String text) {

        if (text == null) {
            LOGGER.error("Text being written to file cannot be null.");
            return;
        }

        try {
            File file = new File("./build/resources/main/files/driverHistory.txt");
            if (file.createNewFile()) {
                LOGGER.info("File created: " + file.getName());
            } else {
                LOGGER.info("File already exists.");
            }

            FileWriter writer = new FileWriter(file);
            try {
                writer.write(text);
                LOGGER.info("Successfully created driving history.");
            } catch (IOException e) {
                LOGGER.error("Failed to write to file", e);
            }
            writer.close();
        } catch (IOException e) {
            LOGGER.error("An error occured while writing to file.", e);
        }
    }

    public Scanner readFile(String filePath) {

        try {
            if (filePath != null) {
                File file = new File(filePath);
                return new Scanner(file);
            }
        } catch (FileNotFoundException exception) {
            LOGGER.error("Failed to read file", exception);
        }

        return null;
    }
}
