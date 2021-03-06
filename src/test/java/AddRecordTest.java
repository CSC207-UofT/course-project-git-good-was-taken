import constants.ManagementSystemException;
import clientbooking.RecordAdding;
import entities.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddRecordTest {
    BookableClinic clinic;
    RecordAdding addRecord;
    TimePeriod timePeriod;
    VaccineBatch batch;
    Client client1;
    LocalDateTime dateTime;


    @Before // Setting up before the tests
    public void setUp() {
        dateTime = LocalDateTime.of(2020, 11, 14, 12, 30);
        timePeriod = new TimePeriod(dateTime, 5);
        batch = new VaccineBatch.BatchBuilder().brand("Pfizer").quantity(100).expiry(LocalDate.of(2099, 10 , 30)).id(1234).build();


        // Setting up the clients
        client1 = new Client("client1", "qwertyuiop");

        // Setting up appointments
        Appointment appointment1 = new Appointment.AppointmentBuilder().client(client1).timePeriod(timePeriod).vaccineBrand("Pfizer").appointmentID(1).clientVaccineBatch(batch).build();

        Clinic clinic1 = new Clinic.ClinicBuilder().clinicId(1).location("Toronto").build();
        clinic = new BookableClinic(clinic1);
        clinic.setShift(dateTime.toLocalDate(), 5);
        clinic.addTimePeriod(timePeriod, dateTime.toLocalDate());

        clinic.addAppointment(appointment1);

        addRecord = new RecordAdding(clinic);

    }

    @Test(timeout = 150) // Testing that appointments can be logged by appointment ID
    public void TestLogAppointment() {
        try{
            addRecord.logAppointment(1);
        }catch(ManagementSystemException e) {
            fail();
        }
        assertTrue(clinic.getAppointmentByTimePeriod(timePeriod).isEmpty());
        assertTrue(clinic.getVaccineLog().containsId("A1"));
    }

    @Test(timeout = 150) // Testing that walk ins can be logged
    public void TestLogWalkIn() {
        try{
            addRecord.logWalkIn("2", client1, dateTime, "Pfizer");
        }catch(ManagementSystemException e) {
            fail();
        }
        assertTrue(clinic.getVaccineLog().containsId("V2"));
    }

    @Test(timeout = 150) // Testing that all appointment on a certain date and time can be logged
    public void TestLogByDateTime() {
        try{
            addRecord.logByDateTime(dateTime);
        }catch(ManagementSystemException e) {
            fail();
        }
        assertTrue(clinic.getAppointmentByTimePeriod(timePeriod).isEmpty());
        assertTrue(clinic.getVaccineLog().containsId("A1"));
    }

    @Test(timeout = 150) // Testing that all appointments on a certain date can be logged
    public void TestLogByDate() {
        try{
            addRecord.logByDate(dateTime.toLocalDate());
        }catch(ManagementSystemException e) {
            fail();
        }
        assertTrue(clinic.getAppointmentByTimePeriod(timePeriod).isEmpty());
        assertTrue(clinic.getVaccineLog().containsId("A1"));
    }

}

