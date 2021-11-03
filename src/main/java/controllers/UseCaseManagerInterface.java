package controllers;

import entities.BookableServiceLocation;
import entities.Client;
import entities.TimePeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface UseCaseManagerInterface {
    ArrayList<Integer> getClinicIds();

    boolean setEmployees(int clinicId, LocalDate date, int employees);

    boolean addTimePeriod(int clinicId, LocalDateTime dateTime);

    boolean removeTimePeriod(int clinicId, LocalDateTime dateTime);

    int addMultipleTimePeriods(int clinicId, LocalDateTime start, LocalDateTime end, int interval);

    boolean addBatch(int clinicId, String batchBrand, int batchQuantity, LocalDate batchExpiry, int batchId);

    String getSupplyStringByClinic(int clinicId);

    boolean AppointmentBooking(Client client, BookableServiceLocation clinic, TimePeriod timePeriod,
                               String vaccineBrand, int appointmentId);

    boolean AppointmentCancellation(int appointmentId, BookableServiceLocation clinic);

    String AppointmentViewing(Client client, BookableServiceLocation clinic, TimePeriod timePeriod,
                              String vaccineBrand, int appointmentId);
}