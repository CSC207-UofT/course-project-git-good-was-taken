public class Client {

    private final String name;
    private final String healthCareNumber;
    private Boolean hasAppointment;

    public Client(String name, String healthCareNumber){
        this.name = name;
        this.healthCareNumber = healthCareNumber;
        this.hasAppointment = false; //look over later!!!
    }

    public String getName() {return name;}

    public String getHealthCareNumber() {return healthCareNumber;}

    public Boolean getHasAppointment() {return hasAppointment;}

    public void approveAppointment() {this.hasAppointment = true;}
}
