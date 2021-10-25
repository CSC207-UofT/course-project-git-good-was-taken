import entities.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookableClinicTest {
    BookableClinic clinic;
    Client client;
    Appointment appointment;
    TimePeriod timePeriod;

    @Before // Setting up before the tests
    public void setUp() {
        clinic = new BookableClinic(1);
        client = new Client("Joe Mama", "4206969");
        timePeriod = new TimePeriod(LocalDateTime.now(), 50);
        clinic.addTimePeriod(timePeriod, LocalDate.now());
        appointment = new Appointment(client, timePeriod, "Pfizer", 10);
    }

    @Test(timeout = 100) // Testing adding appointments
    public void TestAddAppointment() {
        assertTrue(clinic.addAppointment(appointment));
        assertEquals(clinic.getAppointmentRecord(10), appointment);
    }

    @Test(timeout = 100) // Testing removing appointments
    public void TestRemoveAppointment() {
        clinic.addAppointment(appointment);
        assertTrue(clinic.removeAppointment(appointment));
        assertNull(clinic.getAppointmentRecord(10));
    }
    @Test(timeout = 100) // Testing removing appointments by its ID
    public void TestRemoveAppointmentById() {
        clinic.addAppointment(appointment);
        assertTrue(clinic.removeAppointmentById(10));
        assertNull(clinic.getAppointmentRecord(10));
    }
}