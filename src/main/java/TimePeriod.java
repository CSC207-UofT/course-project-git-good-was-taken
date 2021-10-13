import java.util.List;
import java.util.ArrayList;

/* TODO: Change 'Object' to whatever is appropriate here*/
public class TimePeriod {

    Object daytime;
    List<Object> availabilities;
    List<Object> bookedSlots;

    public TimePeriod(){
        this.daytime = new Object();
        this.availabilities = new ArrayList();
        this.bookedSlots = new ArrayList();
    }

    public void addAvailability(Object availableSpot){
        this.availabilities.add(availableSpot);
    }

    /*
    TODO: improve following method
     */
    public boolean removeAvailability(Object unavailableSpot){
        if(this.availabilities.contains(unavailableSpot)){
            this.availabilities.remove(unavailableSpot);
            return true;
        }
        else
            return  false;
    }

    public List<Object> getAvailabilities() {
        return availabilities;
    }
}
