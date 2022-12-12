package catalog.medicine;

import java.util.List;
import java.util.Random;

public class MedicineStock {

    private MedicineLoader loader;
    private List<MedicineInfo> medicineList;
    
    public MedicineStock(){
        loader = new MedicineLoader();
        populateList();
    }

    private void populateList(){
        medicineList = loader.loadMedicines(medicineList);
        defineStockAvailability();
    }

    private void defineStockAvailability(){
        Random rand = new Random();
        for (MedicineInfo medicineInfo : medicineList) {
            if (rand.nextInt(100) < medicineInfo.getAvailabilityRate()) {
                medicineInfo.setAvailability(true);
            }else{
                medicineInfo.setAvailability(false);
            }
        }
    }

    public List<MedicineInfo> getMedicineList() {
        return medicineList;
    }
    
}
