package ai.lines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.patient.CurrentStatus;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import lines.LinesCatalog;
import lines.NPCLine;
import lines.PlayerLine;
import state.State;
import state.counter.WorkCounterState;

public class AIResponse {
    private List<Answer> possibleAnswers;

    public AIResponse(){
        possibleAnswers = new ArrayList<>();
    }

    public void speak(WorkCounterState state, PlayerLine playerAnswer){
        possibleAnswers.clear();
        changeSatisfaction(state.getActivePatient().getCurrentStatus(), playerAnswer);
        defineAnswer(state.getLinesCatalog(), playerAnswer, state);
    }

    private void defineAnswer(LinesCatalog linesCatalog, PlayerLine playerAnswer, WorkCounterState state) {
        Random rand = new Random();
        List<NPCLine> npcAnswers = findAnswers(playerAnswer.getResponsesId(), linesCatalog, state);
        if (npcAnswers != null) {
            int chancePool = defineChances(npcAnswers, state.getActivePatient().getCurrentStatus().getSatisfaction());
            System.out.println("chancepool: " + chancePool); //REMOVE
            int chosenNumber = rand.nextInt(chancePool);
            System.out.println("chosennumber: " + chosenNumber); //REMOVE
            NPCLine chosenLine = null;
            for (Answer possibleAnswer : possibleAnswers) {
                if (chosenNumber >= possibleAnswer.getLowerValue() && chosenNumber < possibleAnswer.getHigherValue()) {
                    chosenLine = possibleAnswer.getAnswer();
                    break;
                }
            }
            if (chosenLine != null) {
                state.getActivePatient().getAction().setAction(chosenLine.getAction().get(0));
                List<String> processedAnswer = processAnswer(chosenLine, state);
                state.getPlayerChoices().setLines(chosenLine.getResponsesId(), state);
                state.getSpeechDisplay().loadWords(processedAnswer);
            }
        }
    }

    private List<String> processAnswer(NPCLine chosenLine, WorkCounterState state) {
        List<String> line = new ArrayList<>();
        if (GameSettings.language == Language.PORTUGUESE) {
            line.addAll(chosenLine.getPtLine());
        }else{
            line.addAll(chosenLine.getEngLine());
        }
        if (state.getActivePatient().getObjective().getObjective().equals("knows_medicine")) {
            String medicineName = getMedicine(state).toUpperCase();
            for (int i = 0; i < line.size(); i++) {
                if (line.get(i).equals("[MEDICINE]")) {
                    line.set(i, medicineName);
                }else if (line.get(i).equals("[MEDICINE],")) {
                    line.set(i, medicineName + ",");
                }else if (line.get(i).equals("[MEDICINE].")) {
                    line.set(i, medicineName + ".");
                }else if (line.get(i).equals("[MEDICINE]?")) {
                    line.set(i, medicineName + "?");
                }else if (line.get(i).equals("[MEDICINE]!")) {
                    line.set(i, medicineName + "!");
                }else if (line.get(i).equals("[MEDICINE]...")) {
                    line.set(i, medicineName + "...");
                }           
            }
        }
        return line;
    }

    private String getMedicine(WorkCounterState state) {
        state.getActivePatient().getObjective().getMedicines().get(0).setWasOrdered(true);
        return state.getActivePatient().getObjective().getMedicines().get(0).getName();
    }

    private int defineChances(List<NPCLine> npcAnswers, int npcSatisfaction) {
        System.out.println("answer size: " + npcAnswers.size()); //REMOVE
        int totalChance = 0;
        for (NPCLine npcLine : npcAnswers) {
            System.out.println("line: " + npcLine.getPtLine());  //REMOVE
            int chance = 100 - (Math.abs(npcSatisfaction - npcLine.getTargetSatisfaction())*5);
            if (chance < 0) {
                chance = 0;
            }
            System.out.println("Line chance: " + chance); //REMOVE
            totalChance += chance;
            possibleAnswers.add(new Answer(totalChance - chance, totalChance, npcLine));
        }
        return totalChance;
    }

    private List<NPCLine> findAnswers(List<Integer> reponsesId, LinesCatalog linesCatalog, State state) {
        List<NPCLine> possibleNPCAnswers = filterAnswers(reponsesId, linesCatalog, state);
        
        return possibleNPCAnswers;
    }

    private List<NPCLine> filterAnswers(List<Integer> reponsesId, LinesCatalog linesCatalog, State state) {
        List<NPCLine> possibleNPCAnswers = new ArrayList<>();
        for (Integer integer : reponsesId) {
            if (integer >= 0) {
                NPCLine response = (NPCLine)linesCatalog.getFullLineList().get(integer);
                if (state instanceof WorkCounterState) {
                    WorkCounterState workState = (WorkCounterState)state;
                    if (workState.getActivePatient().getObjective().getObjective().equals("knows_medicine")) {
                        if (response.getRelatedTopics().contains("any") || response.getRelatedTopics().contains("knows_medicine")) {
                            possibleNPCAnswers.add(response);
                        }else{
                            continue;
                        }
                    }else{
                        if (response.getRelatedTopics().contains("any") || response.getRelatedTopics().contains("has_symptom")) {
                            possibleNPCAnswers.add(response);
                        }else{
                            continue;
                        }
                    }
                }
            }else{
                possibleNPCAnswers = null;
            }
        }
        return possibleNPCAnswers;
    }

    private void changeSatisfaction(CurrentStatus npcStatus, PlayerLine playerAnswer) {
        int value = npcStatus.getSatisfaction() - playerAnswer.getSatisfactionRating();
        if (value > 0) {
            int valueToSubtract = (int)Math.floor(value/5);
            npcStatus.decreaseSatisfaction(Math.abs(valueToSubtract));
            System.out.println("satisfaction decreased to: " + npcStatus.getSatisfaction() + " by " + valueToSubtract); // REMOVE
        }else{
            int valueToSum = (int)Math.ceil(value/5);
            npcStatus.increaseSatisfaction(Math.abs(valueToSum));
            System.out.println("satisfaction increased to: " + npcStatus.getSatisfaction() + " by " + valueToSum); // REMOVE
        }
    }

    private class Answer{
        private int lowerValue;
        private int higherValue;
        private NPCLine answer;

        public Answer(int lowerValue, int higherValue, NPCLine answer){
            this.lowerValue = lowerValue;
            this.higherValue = higherValue;
            this.answer = answer;
        }

        public int getLowerValue() {
            return lowerValue;
        }

        public int getHigherValue() {
            return higherValue;
        }

        public NPCLine getAnswer() {
            return answer;
        }

        public void clearPossibleAnswers(){
            possibleAnswers.clear();
        }
    }
}
