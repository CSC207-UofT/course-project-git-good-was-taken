package entities;

import java.time.LocalDate;

/**
 * This is the Entity for a vaccine batch, which includes the
 * brand, quantity, expiry, and other data relevant to a specific batch
 */

public class VaccineBatch {
    private final String brand;
    private final int quantity;
    private final LocalDate expiry;
    private final int id;
    private int reserve;

    private VaccineBatch(BatchBuilder builder){
        this.brand = builder.brand;
        this.id = builder.id;
        this.expiry = builder.expiry;
        this.quantity = builder.quantity;
        this.reserve = builder.reserve;

    }

    /**
     * @return [description]
     */
    // Return whether the batch is expired
    public boolean isExpired(){
        LocalDate today = LocalDate.now();
        return (today.isAfter(this.expiry) || today.equals(this.expiry));
    }

    /**
     * @param num [description]
     */
    // Set the reserve quantity
    public void setReserve(int num) {
        this.reserve = num;
    }

    /**
     * @param num [description]
     */
    // Change the reserve quantity
    public void changeReserve(int num) {
        this.reserve += num;
    }

    /**
     * @return [description]
     */
    // Return the number of available vaccines in a batch
    public int getAvailable(){
        return this.quantity - this.reserve;
    }

    /**
     * @return [description]
     */
    // Return a string of the information of this batch
    @Override
    public String toString() {
        return  "Batch ID: " + this.id +
                "\nBrand: " + this.brand +
                "\nExpiry: " + this.expiry +
                "\nTotal Doses: " + this.quantity +
                "\nDoses Reserved: " + this.reserve;
    }

    // Getters

    /**
     * @return [description]
     */
    public String getBrand(){
        return this.brand;
    }

    /**
     * @return [description]
     */
    public LocalDate getExpiry(){return this.expiry;}

    /**
     * @return [description]
     */
    public int getId(){return this.id;}

    /**
     * @return [description]
     */
    public int getReserve() {return this.reserve;}

    public static  class BatchBuilder {
        private final String brand;
        private final int quantity;
        private final LocalDate expiry;
        private final int id;
        private int reserve;

        public BatchBuilder(String brand, int quantity, LocalDate expiry, int id) {
            this.brand = brand;
            this.quantity = quantity;
            this.expiry = expiry;
            this.id = id;
            this.reserve = 0;
        }

        public BatchBuilder Reserved(int reserved){
            this.reserve = reserved;
            return this;
        }

        public VaccineBatch build(){
            return new VaccineBatch(this);
        }
    }
}


