package client_booking;

import Constants.BookingConstants;
import Constants.ExceptionConstants;
import entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This is the Use Case for logging records to the VaccinationLog
 * Every time the use case is needed, a new RecordAdding instance is created
 * with parameters being flexible based on how we want to log
 */

public class RecordAdding {

    ClinicDecorator clinic;

    // Constructor
    public RecordAdding(ClinicDecorator clinic){
        this.clinic = clinic;
    }

    // Log a given appointment based on an appointment ID
    public String logAppointment(int id) throws Exception {
        if(clinic.getAppointmentRecord(id) == null) {
            throw new Exception(ExceptionConstants.APPOINTMENT_DOES_NOT_EXIST);
        }
        if(!clinic.getAppointmentRecord(id).appointmentTimePassed()) {
            throw new Exception(ExceptionConstants.APPOINTMENT_NOT_PASSED);
        }

        clinic.logPastVaccinations(clinic.getAppointmentRecord(id));
        Appointment removedAppointment = clinic.getAppointmentRecord(id);
        clinic.removeAppointmentById(id);
        return removedAppointment.toString();
    }

    // Log a walk-in appointment given certain parameters
    public String logWalkIn(String vaccinationID, User client, LocalDateTime dateTime, String brand) throws Exception {
        if (dateTime.isBefore(LocalDateTime.now())){
            clinic.logPastVaccinations(vaccinationID, client, dateTime, brand);
            return clinic.getVaccineLog().getRecordString(BookingConstants.NON_APPOINTMENT_BASED_PREFIX + vaccinationID);
        }
        else{
            throw new Exception(ExceptionConstants.TIME_NOT_PASSED);
        }
    }

    // Log all appointment on a certain date and time
    public StringBuilder logByDateTime(LocalDateTime dateTime) throws Exception {
        if(dateTime.isAfter(LocalDateTime.now())) {
            throw new Exception(ExceptionConstants.TIME_NOT_PASSED);
        }

        if(clinic.getTimePeriod(dateTime) == null) {
            throw new Exception(ExceptionConstants.CLINIC_DOES_NOT_HAVE_TIMESLOT);
        }

        else{
            TimePeriod timePeriod = clinic.getTimePeriod(dateTime);
            ArrayList<Appointment> appointments = clinic.getAppointmentByTimePeriod(timePeriod);
            ArrayList<String> appointmentsString = new ArrayList<>();
            for (Appointment appointment: appointments){
                clinic.logPastVaccinations(appointment);
                appointmentsString.add(appointment.toString());
                clinic.removeAppointmentById(appointment.getAppointmentId());
            }
            StringBuilder output = new StringBuilder();
            for(String appointment : appointmentsString){
                output.append(appointment);
            }
            return output;
        }
    }

    // Log all appointments on a given date
    public StringBuilder logByDate(LocalDate date) throws Exception {
        if (clinic.getTimePeriods(date) == null ||
                clinic.getTimePeriods(date).equals(new ArrayList<TimePeriod>())){
            throw new Exception(ExceptionConstants.CLINIC_DOES_NOT_HAVE_TIMESLOT);
        }

        else{
            StringBuilder object = new StringBuilder();
            for (TimePeriod timePeriod : clinic.getTimePeriods(date)){
                object.append(logByDateTime(timePeriod.getDateTime()));
            }
            return object;
        }
    }
}