public class Appointment {
    /**
     * appointment entity class
     *
     * @param:
     * client: object of client representing a specific client
     * daytime: ******************
     * vaccineBrand: string representing the brand of the vaccine
     */

    Client client;
    Object daytime; /* TODO: adjust attribute type*/
    String vaccineBrand;

    public Appointment(Client client, Object daytime, String vaccineBrand){
        this.client = client;
        this.daytime = daytime;
        this.vaccineBrand = vaccineBrand;
    }

    //getters
    public Client getClient() { return client; }

    public Object getDaytime() { return daytime; }

    public String getVaccineBrand() { return vaccineBrand;}
}
