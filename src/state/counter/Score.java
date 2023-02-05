package state.counter;

public class Score {    
    private Result[] scores;
    private Result currentResult;
    private int index;

    public Score(){
        index = 0;
        scores = new Result[10];
        currentResult = new Result();
        scores[index] = currentResult;
    }

    public Result getCurrentResult() {
        return currentResult;
    }

    public void changeCurrentResult(){
        if (index < 10) {
            currentResult = new Result();
            scores[index++] = currentResult;
        }
    }

    public class Result{
        private boolean lied;
        private boolean wrongMedicine;
        private boolean rightCart;
        private boolean helped;
        private int patientSatisfaction;
        
        public boolean hasLied() {
            return lied;
        }

        public boolean isWrongMedicine() {
            return wrongMedicine;
        }

        public int getPatientSatisfaction() {
            return patientSatisfaction;
        }

        public void setLied(boolean lied) {
            this.lied = lied;
        }

        public void setWrongMedicine(boolean wrongMedicine) {
            this.wrongMedicine = wrongMedicine;
        }

        public void setPatientSatisfaction(int patientSatisfaction) {
            this.patientSatisfaction = patientSatisfaction;
        }

        public boolean hasHelped() {
            return helped;
        }

        public boolean isRightCart() {
            return rightCart;
        }

        public void setRightCart(boolean rightCart) {
            this.rightCart = rightCart;
        }
        
    }
}
