package databaseintegration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This is the interface for an adapter that stores data that can then be loaded later
 * This interface corresponds to classes that lie on the "Controllers" layer of clean architecture
 */

public interface DataStoring {
    void writeToAppointment(int appointmentID, int clinicID, String clientID, int periodID, String brand) throws SQLException;

    void writeToClient(String healthCardID, String name, boolean hasAppointment) throws SQLException;

    void writeToClinic(int clinicID, String location, boolean isBookable);

    void writeToTimePeriods(int periodID, int clinicID, int availableSlots, LocalDateTime datetime) throws SQLException;

    void writeToVaccineBatch(int batchID, int clinicID, String brand,
                             LocalDate expiryDate, int reserved, int quantity) throws SQLException;
}
