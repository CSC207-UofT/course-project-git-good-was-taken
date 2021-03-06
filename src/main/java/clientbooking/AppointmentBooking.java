package clientbooking;

import constants.ManagementSystemException;
import entities.*;
import managers.Modifier;

import java.time.LocalDate;
import java.util.List;

/**
 * This is the Use Case for booking appointments.
 * Every time the use case is needed, a new AppointmentBooking instance is created
 * with parameters being the client, clinic, time period, and vaccination brand requested,
 * along with a specified ID
 */

public class AppointmentBooking {
    final User client;
    final ClinicDecorator clinic;
    TimePeriod timePeriod;
    final String vaccineBrand;
    final int appointmentId;
    final Modifier modifier;

    /**
    * creates Use Case for booking appointments.
    *
    * @param client The client booking the appointment
    * @param clinic The clinic where the appointment is booked
    * @param timePeriod The time period when the appointment is expected to happen
    * @param vaccineBrand the vaccine brand for this appointment
    * @param id the id of this appointment
    */
    public AppointmentBooking(User client, ClinicDecorator clinic, TimePeriod timePeriod, String vaccineBrand, int id){
        this.client = client;
        this.clinic = clinic;
        this.timePeriod = timePeriod;
        this.vaccineBrand = vaccineBrand;
        this.appointmentId = id;
        this.modifier = null;
    }

    /**
     * creates Use Case for booking appointments.
     *
     * @param client The client booking the appointment
     * @param clinic The clinic where the appointment is booked
     * @param timePeriod The time period when the appointment is expected to happen
     * @param vaccineBrand the vaccine brand for this appointment
     * @param id the id of this appointment
     * @param modifier the modifier that this appointment will be written to
     */
    public AppointmentBooking(User client, ClinicDecorator clinic, TimePeriod timePeriod, String vaccineBrand, int id,
                              Modifier modifier){
        this.client = client;
        this.clinic = clinic;
        this.timePeriod = timePeriod;
        this.vaccineBrand = vaccineBrand;
        this.appointmentId = id;
        this.modifier = modifier;
    }


    /** indicates whether there is an opening for the specified time period
    * @return true the time period is available - false if not.
    */
    private boolean isTimeslotAvailable(){
        for(TimePeriod timePeriod: clinic.getTimePeriods(this.timePeriod.getDateTime().toLocalDate())) {
            if (timePeriod.getDateTime().equals(this.timePeriod.getDateTime())) {
                this.timePeriod = timePeriod;
                return timePeriod.getAvailableSlots() > 0;
            }
        }
        return false;
    }


    /** Reserve a vaccine dose for this client IF there is a timeslot available
     * AND this person doesn't already have an appointment.
     * @return true if the vaccine batch in question assigned successfully; false otherwise.
     * @throws ManagementSystemException throws if the client has an appointment
     */
    public VaccineBatch assignVaccineDose() throws ManagementSystemException {
        if(this.client.getHasAppointment()) {
            throw new ManagementSystemException(ManagementSystemException.CLIENT_ALREADY_HAS_APPOINTMENT);
        }

        List<VaccineBatch> batchList = this.clinic.getSupplyObj().getBatchList();
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

        if(modifier != null) {
            this.modifier.UpdateReservedInBatch(earliestExpiringVaccine);
        }

        return earliestExpiringVaccine;
    }


    /** Check if the appointment ID is unique
     * @return true if the id is unique - false otherwise
     */
    private boolean hasUniqueId() {return !clinic.getAppointmentIds().contains(appointmentId);}


    /** Create an appointment for this client in the Clinic's system
     * @return String describing the appointment
     * @throws ManagementSystemException throws exception if the time slot unavailable or doesnt have a unique id.
     */
    public String createAppointment() throws ManagementSystemException {
        if(!this.isTimeslotAvailable()) {
            throw new ManagementSystemException(ManagementSystemException.TIME_SLOT_UNAVAILABLE);
        }
        if(!this.hasUniqueId()) {
            throw new ManagementSystemException(ManagementSystemException.APPOINTMENT_ID_ALREADY_EXISTS);
        }

        Appointment appointment = new Appointment.AppointmentBuilder()
                .client(this.client).timePeriod(this.timePeriod).vaccineBrand(this.vaccineBrand)
                .appointmentID(this.appointmentId).clientVaccineBatch(this.assignVaccineDose()).build();
        this.client.approveAppointment();
        this.clinic.addAppointment(appointment);
        this.timePeriod.findAndReserveSlot();

        if(modifier != null) {
            this.modifier.UpdateBookedAvailableSlots(this.timePeriod, this.clinic.getServiceLocationId());
        }

        if(modifier != null) {
            this.modifier.StoreAppointment(appointment, this.clinic.getServiceLocationId());
        }

        if(modifier != null) {
            this.modifier.UpdateToHasAppointment(appointment.getClient().getHealthCareNumber());
        }

        return appointment.toString();
    }
}