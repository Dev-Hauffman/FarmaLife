package lines;

import catalog.medicine.MedicineInfo;
import entity.patient.Objective.Medicine;
import state.counter.WorkCounterState;

public class ActionProcessor {
    
    public static void process(Line line, WorkCounterState state){
        for (String action : line.getAction()) {
            apply(action, state, line);
        }
    }

    private static void apply(String action, WorkCounterState state, Line line) {
        switch (action) {
            case "set_medicine":
                for (String string : line.getTags()) {
                    for (MedicineInfo medicine : state.getStock().getMedicineList()) {
                        if (string.equals(medicine.getName())) {
                            System.out.println("found medicine"); //REMOVE
                            state.getActivePatient().getObjective().addMedicine(string, medicine.isAvailable());
                        }
                    }
                }
                break;
        
            case "right_medicine":
                state.getScore().getCurrentResult().setWrongMedicine(false);
                break;

            case "wrong_medicine":
                state.getScore().getCurrentResult().setWrongMedicine(true);
                break;

            case "no_stock":
                if (state.getActivePatient().getObjective().getMedicines().get(0).isInStock() == true) {
                    state.getScore().getCurrentResult().setLied(true);
                    System.out.println("lied(true) set to: " + state.getScore().getCurrentResult().hasLied());// REMOVE
                }else{
                    state.getScore().getCurrentResult().setLied(false);
                    System.out.println("lied(false) set to: " + state.getScore().getCurrentResult().hasLied());// REMOVE
                }
                break;

            case "in_stock":
                if (state.getActivePatient().getObjective().getMedicines().get(0).isInStock() == true) {
                    state.getScore().getCurrentResult().setLied(false);
                    System.out.println("lied(false) set to: " + state.getScore().getCurrentResult().hasLied());// REMOVE
                }else{
                    state.getScore().getCurrentResult().setLied(true);
                    System.out.println("lied(true) set to: " + state.getScore().getCurrentResult().hasLied());// REMOVE
                }
                break;

            default:
                break;
        }
    }
}
