package client_booking;

import constants.ManagementSystemException;
import entities.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This is the Use Case for booking appointments.
 * Every time the use case is needed, a new AppointmentBooking instance is created
 * with parameters being the client, clinic, time period, and vaccination brand requested,
 * along with a specified ID
 */

public class AppointmentBooking {
    User client;
    ClinicDecorator clinic;
    TimePeriod timePeriod;
    String vaccineBrand;
    int appointmentId;


    public AppointmentBooking(User client, ClinicDecorator clinic, TimePeriod timePeriod, String vaccineBrand, int id){
        this.client = client;
        this.clinic = clinic;
        this.timePeriod = timePeriod;
        this.vaccineBrand = vaccineBrand;
        this.appointmentId = id;
    }

    // Return whether there is an opening for the specified time period
    private boolean isTimeslotAvailable(){
        for(TimePeriod timePeriod: clinic.getTimePeriods(this.timePeriod.getDateTime().toLocalDate())) {
            if (timePeriod.getDateTime().equals(this.timePeriod.getDateTime())) {
                return timePeriod.getAvailableSlots() > 0;
            }
        }
        return false;
    }

    // Reserve a vaccine dose for this client IF there is a timeslot available
    // AND this person doesn't already have an appointment
    // Return the VaccineBatch in question
    public VaccineBatch assignVaccineDose() throws ManagementSystemException {
        if(this.client.getHasAppointment()) {
            throw new ManagementSystemException(ManagementSystemException.CLIENT_ALREADY_HAS_APPOINTMENT);
        }

        ArrayList<VaccineBatch> batchList = this.clinic.getSupplyObj().getBatchList();
        VaccineBatch earliestExpiringVaccine = null;
        LocalDate earliestDate = null;
        for (VaccineBatch batch : batchList) {
            if (batch.getBrand().equals(this.vaccineBrand) && !batch.isExpired() && batch.getAvailable() > 0) {
                if (earliestDate == null || batch.getExpiry().isBefore(earliestDate)) {
                    earliestDate = batch.getExpiry();
                    earliestExpiringVaccine = batch;
                }
            }
        }
        if (earliestExpiringVaccine == null) {
            throw new ManagementSystemException(ManagementSystemException.BRAND_DOES_NOT_EXIST);
        }
        earliestExpiringVaccine.changeReserve(1);
        return earliestExpiringVaccine;
    }

    // Check if the appointment ID is unique
    private boolean hasUniqueId() {
        return !clinic.getAppointmentIds().contains(appointmentId);
    }

    // Create an appointment for this client in the Clinic's system
    public String createAppointment() throws ManagementSystemException {
        if(!this.isTimeslotAvailable()) {
            throw new ManagementSystemException(ManagementSystemException.TIME_SLOT_UNAVAILABLE);
        }
        if(!this.hasUniqueId()) {
            throw new ManagementSystemException(ManagementSystemException.APPOINTMENT_ID_ALREADY_EXISTS);
        }

        Appointment appointment = new Appointment(
                this.client, this.timePeriod, this.vaccineBrand, this.appointmentId, this.assignVaccineDose());
        this.client.approveAppointment();
        this.clinic.addAppointment(appointment);
        this.timePeriod.findAndReserveSlot();
        return appointment.toString();
    }
}