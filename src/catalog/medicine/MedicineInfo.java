package catalog.medicine;

import game.settings.GameSettings;
import game.settings.GameSettings.Language;

public class MedicineInfo{
    private String name;
    private int availabilityRate;
    private String availability;

    public MedicineInfo(String name, int availability){
        this.name = name;
        this.availabilityRate = availability;
    }

    public String getName() {
        return name;
    }

    public int getAvailabilityRate() {
        return availabilityRate;
    }

    public String getAvailability() {
        return availability;
    }

    public boolean isAvailable() {
        if (availability.equals("EM ESTOQUE")) {
            return true;
        }else{
            return false;
        }
    }

    public void setAvailability(boolean value) {
        if (value) {
            if (GameSettings.language == Language.PORTUGUESE) {
                this.availability = "EM ESTOQUE";
            }else{
                this.availability = "IN STOCK";
            }
        }else{
            if (GameSettings.language == Language.PORTUGUESE) {
                this.availability = "SEM ESTOQUE";
            }else{
                this.availability = "NO STOCK";
            }
        }
        
    }
}