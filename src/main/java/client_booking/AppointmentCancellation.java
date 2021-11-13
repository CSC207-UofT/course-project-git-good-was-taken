package client_booking;

import Constants.ExceptionConstants;
import entities.*;

/**
 * This is the Use Case for cancelling appointments.
 * Every time the use case is needed, a new AppointmentCancellation instance is created
 * with the only parameters being the clinic and appointment ID
 */

public class AppointmentCancellation {

    int appointmentId;
    ClinicDecorator clinic;

    // Constructor
    public AppointmentCancellation(int appointmentId, ClinicDecorator clinic){
        this.appointmentId = appointmentId;
        this.clinic = clinic;
    }

    // Delete the appointment. Return true if successful
    public String deleteAppointment() throws Exception {
        Appointment appointment = clinic.getAppointmentRecord(this.appointmentId);
        if (appointment.getClient().getHasAppointment()) {
            appointment.getClient().disapproveAppointment();
            clinic.removeAppointmentById(this.appointmentId);
            appointment.getTimePeriod().addAvailableSlot();
            return appointment.toString();
        }
        throw new Exception(ExceptionConstants.APPOINTMENT_DOES_NOT_EXIST);
    }


}