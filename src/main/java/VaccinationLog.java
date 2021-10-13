import java.util.ArrayList;
/* TODO: specify the "Object" */
public class VaccinationLog {
    /**
     *
     */
    private ArrayList<Object> log;

    public VaccinationLog() { this.log = new ArrayList(); }

    public void addToLog(Object appointment){
        log.add(appointment);
    }
}
