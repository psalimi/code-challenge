package code.challenge;

import code.challenge.models.Driver;
import code.challenge.models.Trip;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


public class TrackDrivingHistoryServiceTest {

    private TrackDrivingHistoryService trackDrivingHistoryService = new TrackDrivingHistoryService();

    @Test
    public void isDataValidTest() {
        String validData = "Driver Dan";
        assertTrue(trackDrivingHistoryService.isDataValid(validData, 2));

        String invalidData = "";
        assertFalse(trackDrivingHistoryService.isDataValid(invalidData, 2));
    }

    @Test
    public void isValidTripTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Trip validTrip = new Trip(format.parse("17:00"), format.parse("18:00"), 70f);
        Trip invalidTrip = new Trip(format.parse("17:00"), format.parse("18:00"), 101f);
        assertTrue(this.trackDrivingHistoryService.isValidTrip(validTrip));
        assertFalse(this.trackDrivingHistoryService.isValidTrip(invalidTrip));
        assertFalse(this.trackDrivingHistoryService.isValidTrip(null));
    }

    @Test
    public void addTripForDriverTest() {
        Driver driver = new Driver("Dan");
        Trip trip = new Trip(new Date(), new Date(), 10f);
        this.trackDrivingHistoryService.addTripForDriver(driver, trip);
        assertEquals(1, driver.getTrips().size());
    }

    @Test
    public void createDriverTest() {
        String[] validData = new String[]{ "Driver", "name"};
        assertEquals("name", this.trackDrivingHistoryService.createDriver(validData).getName());
    }

    @Test
    public void createTripTest() {
        String[] validData = new String[]{ "trip", "name", "12:00", "13:00", "8"};
        Trip trip = this.trackDrivingHistoryService.createTrip(validData);
        assertEquals(Float.parseFloat(validData[4]), trip.getMilesDriven(), 0);

        String[] invalidData = new String[]{ "trip", "name", "12", "13:00", "8"};
        Trip invalidTrip = this.trackDrivingHistoryService.createTrip(invalidData);
        assertNull(invalidTrip);

        String[] invalidData2 = new String[]{ "trip", "name", "12:00", "13:00"};
        Trip invalidTrip2 = this.trackDrivingHistoryService.createTrip(invalidData2);
        assertNull(invalidTrip2);
    }

    @Test
    public void isCommandDriverTest() {
        String validLine = "Driver Parham";
        String invalidLine = "Drive ";
        assertTrue(this.trackDrivingHistoryService.isCommandDriver(validLine));
        assertFalse(this.trackDrivingHistoryService.isCommandDriver(invalidLine));
        assertFalse(this.trackDrivingHistoryService.isCommandDriver(null));
    }

    @Test
    public void isCommandTripTest() {
        String validLine = "Trip Lauren 11:30 11:50 10";
        String invalidLine = "Trip Dan 12 13";
        assertTrue(this.trackDrivingHistoryService.isCommandTrip(validLine));
        assertFalse(this.trackDrivingHistoryService.isCommandTrip(invalidLine));
        assertFalse(this.trackDrivingHistoryService.isCommandTrip(null));
    }

    @Test
    public void createDrivingReportTest() {
    }
}
