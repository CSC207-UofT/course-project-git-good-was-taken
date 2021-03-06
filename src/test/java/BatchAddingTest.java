import constants.ManagementSystemException;
import clinicmanagement.BatchAdding;
import entities.Clinic;
import entities.VaccineBatch;
import org.junit.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class BatchAddingTest {
    VaccineBatch batch;
    VaccineBatch badBatch;
    Clinic clinic;
    BatchAdding expiredAdd;
    BatchAdding batchAdd;

    @Before // Setting up before the tests
    public void setUp() {
        batch = new VaccineBatch.BatchBuilder().brand("Pfizer").quantity(100).expiry(LocalDate.of(2099, 10 , 30)).id(1234).build();
        badBatch = new VaccineBatch.BatchBuilder().brand("Pfizer").quantity(100).expiry(LocalDate.of(2021, 10 , 10)).id(1234).build();
        clinic = new Clinic.ClinicBuilder().clinicId(1).location("Shoppers Drug Mart - 279 Yonge Street").build();
        batchAdd = new BatchAdding(clinic, batch);
        expiredAdd = new BatchAdding(clinic, badBatch);
    }

    @Test(timeout = 100) // Testing an expired batch of vaccine doses does not get added
    public void TestExpiredBatch(){
        try{
            expiredAdd.addBatch();
            fail();
        }catch(ManagementSystemException ignored){}
    }

    @Test(timeout = 100) // Testing a valid batch of vaccine doses is added
    public void TestAddBatch(){
        try{
            assertNotNull(batchAdd.addBatch());
        } catch(ManagementSystemException e) {
            fail();
        }
    }

    @Test(timeout = 100) // Testing for correct list of vaccine batches after expired addition
    public void TestEmptyContents(){
        try{
            expiredAdd.addBatch();
            fail();
        } catch(ManagementSystemException ignored) {}
        ArrayList<VaccineBatch> testBatchList = new ArrayList<>();
        assertEquals(testBatchList, clinic.getSupply());
    }

    @Test(timeout = 100) // Testing for correct list of vaccine batches after valid addition
    public void TestContents(){
        try{
            batchAdd.addBatch();
        } catch(ManagementSystemException e) {
            fail();
        }
        ArrayList<VaccineBatch> testBatchList = new ArrayList<>();
        testBatchList.add(batch);
        assertEquals(testBatchList, clinic.getSupply());
    }

}
