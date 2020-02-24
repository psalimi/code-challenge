package code.challenge;

import code.challenge.models.Driver;
import code.challenge.models.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrackDrivingHistory {

    private static Logger LOGGER = LoggerFactory.getLogger(TrackDrivingHistory.class);

    public static void main(String[] args) {

        TrackDrivingHistoryService trackDrivingHistoryService = new TrackDrivingHistoryService();
        Map<String, Driver> drivers = new HashMap<>();

        if (args.length < 1) {
            LOGGER.error("Please provide a file path as the only argument", new IOException());
            return;
        }

        Scanner scanner = trackDrivingHistoryService.readFile(args[0]);

        if (scanner == null) {
            return;
        }

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] data = line.split(" ");

            if (trackDrivingHistoryService.isCommandDriver(line)) {
                if (!drivers.containsKey(data[1])) {
                    drivers.put(data[1], trackDrivingHistoryService.createDriver(data));
                }
            } else if (trackDrivingHistoryService.isCommandTrip(line)) {
                String driverName = data[1];
                Trip trip = trackDrivingHistoryService.createTrip(data);

                if (trip == null) {
                    LOGGER.error("Line " + line + " is invalid.");
                    return;
                }

                //Add this trip for the driver if the average speed is valid
                if (trackDrivingHistoryService.isValidTrip(trip)) {
                    trackDrivingHistoryService.addTripForDriver(drivers.get(driverName), trip);
                }
            } else {
                LOGGER.error("Line " + line + " is ignored because it is invalid/incompatible");
            }
        }

        //Create Driver History Report
        trackDrivingHistoryService.createDrivingReport(drivers);
    }
}

