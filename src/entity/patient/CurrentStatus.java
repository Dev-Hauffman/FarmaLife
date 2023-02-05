package entity.patient;

import java.util.Random;

public class CurrentStatus {
    private int satisfaction;
    
    public CurrentStatus(){
        Random rand = new Random();
        satisfaction = rand.nextInt(21) + 40;
        System.out.println("Satisfaction: " + satisfaction); //REMOVE
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void increaseSatisfaction(int value){
        satisfaction += value;
    }

    public void decreaseSatisfaction(int value){
        satisfaction -= value;
    }
}
