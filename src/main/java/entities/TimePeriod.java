package entities;

import java.time.LocalDateTime;

/**
 * This is the TimePeriod entity that represents a time of day for a specific date.
 * A TimePeriod has a dateTime and keeps track of how many available slots there are
 * and booked slots in the case of BookableClinics
 */

public class TimePeriod {
    final LocalDateTime dateTime;
    int availableSlots;
    int bookedSlots;

    // Constructor
    public TimePeriod(LocalDateTime dateTime, int availableSlots){
        this.dateTime = dateTime;
        this.availableSlots = availableSlots;
        this.bookedSlots = 0;
    }

    // Try to remove 1 from available slots and add 1 to bookedSlots.
    // Return whether it was added
    public boolean findAndReserveSlot(){
        // If there are no slots, return false
        if(this.getAvailableSlots() == 0) {return false;}
        availableSlots -= 1;
        bookedSlots += 1;
        return true;
    }

    // Try to add 1 to available slots and remove 1 from bookedSlots. This is done because the Client cancelled
    public void addAvailableSlot(){

        availableSlots += 1;
        bookedSlots -= 1;
    }

    // Return a string of the information of this batch
    @Override
    public String toString() {
        return  "Time: " + this.dateTime +
                "\nTotal Timeslots: " + this.availableSlots +
                "\nTimeslots Booked: " + this.bookedSlots;
    }

    // Getters
    public int getAvailableSlots() {
        return availableSlots;
    }

    public LocalDateTime getDateTime() {return dateTime;}
}
