package entities;

/**
 * This is the Client entity which consists of a person's data
 * from an external data source.
 */

public class Client {
    private final String name;
    private final String healthCareNumber;
    private Boolean hasAppointment;

    public Client(String name, String healthCareNumber){
        this.name = name;
        this.healthCareNumber = healthCareNumber;
        this.hasAppointment = false; //look over later!!!
    }

    public void approveAppointment() {this.hasAppointment = true;}

    // Getters
    public String getName() {return name;}

    public String getHealthCareNumber() {return healthCareNumber;}

    public Boolean getHasAppointment() {return hasAppointment;}
}