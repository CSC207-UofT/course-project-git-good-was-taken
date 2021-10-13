import java.awt.*;
import java.util.ArrayList;

/*
TODO: Add 'extends Clinic'
 */
public class BookableClinic {

    private ArrayList<Appointment> appointmentList;

    public BookableClinic(){this.appointmentList = new ArrayList<>();}

    public void addAppointment(Appointment appointment){appointmentList.add(appointment);}


    public void logPastAppointment() {
        /*
    TODO: Implement this method (What does this method do?)
     */
    }

}
