package entity.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import catalog.medicine.MedicineInfo;

public class Objective {
    private List<Medicine> medicines;
    //private List<Symptom> symptoms;
    private String objective;

    public Objective(List<MedicineInfo> medicinesList){
        medicines = new ArrayList<>();
        Random random = new Random();
        defineObjective(random);
        if (objective.equals("knows_medicine")) {
            int chosenMedicine = random.nextInt(medicinesList.size());
            medicines.add(new Medicine(medicinesList.get(chosenMedicine).isAvailable(), medicinesList.get(chosenMedicine).getName()));
            System.out.println(medicinesList.get(chosenMedicine).getName()); // REMOVE
        }
        System.out.println("Patient " + objective); // REMOVE
    }

    private void defineObjective(Random random) {
        int result = random.nextInt(2);
        if (result == 0) {
            objective = "has_symptom";
        }else{
            objective = "knows_medicine";
        }
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public String getObjective() {
        return objective;
    }

    public void addMedicine(String name, boolean isAvaliable){
        medicines.add(new Medicine(isAvaliable, name));
    }

    public class Medicine {
        private String name;
        private boolean wasOrdered;
        private boolean inStock;

        public Medicine(boolean hasStock, String name){
            this.name = name;
            inStock = hasStock;
            wasOrdered = false;
        }

        public Medicine(String name, boolean wasOrdered){
            this.name = name;
            this.wasOrdered = wasOrdered;
        }

        public String getName() {
            return name;
        }

        public boolean isInStock() {
            return inStock;
        }

        public void setWasOrdered(boolean wasOrdered) {
            this.wasOrdered = wasOrdered;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
