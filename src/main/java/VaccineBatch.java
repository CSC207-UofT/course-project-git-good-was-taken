import java.time.LocalDate;

public class VaccineBatch {
    private String brand;
    private int quantity;
    private LocalDate expiry;
    private int id;
    private int reserve = 0;

    // Constructor
    public VaccineBatch(String brand, int quantity, LocalDate expiry, int id){
        this.brand = brand;
        this.quantity = quantity;
        this.expiry = expiry;
        this.id = id;
    }

    public VaccineBatch(String brand, int quantity, LocalDate expiry, int id, int reserve){
        this.brand = brand;
        this.quantity = quantity;
        this.expiry = expiry;
        this.id = id;
        this.reserve = reserve;
    }

    // Check if a vaccine batch is expired
    public boolean isExpired(){
        LocalDate today = LocalDate.now();
        return (today.isAfter(this.expiry) || today.equals(this.expiry));
    }
    // Return the brand of a vaccine batch
    public String getBrand(){
        return this.brand;
    }
    // Return the id of a vaccine batch
    public int getId(){
        return this.id;
    }
    // Return the number of available vaccines in a batch
    public int getAvailable(){
        return this.quantity - this.reserve;
    }

    public LocalDate getExpiry(){return this.expiry;}
}
