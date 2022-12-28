package lines;

import java.util.ArrayList;
import java.util.List;

public class NPCLine extends Line {
    private List<String> requiredProfile;
    private int targetSatisfaction;
    private WaitingTimeEnum requiredWait;
    private DnaSexEnum DnaSex;
    private AgeEnum age;
    private List<LineTopic> relatedTopics;

    public NPCLine(){
        requiredProfile = new ArrayList<>();
        relatedTopics = new ArrayList<>();
    }

    public void setTargetSatisfaction(int targetSatisfaction) {
        this.targetSatisfaction = targetSatisfaction;
    }

    public void setRequiredWait(WaitingTimeEnum requiredWait) {
        this.requiredWait = requiredWait;
    }

    public void setDnaSex(DnaSexEnum dnaSex) {
        DnaSex = dnaSex;
    }

    public void setAge(AgeEnum age) {
        this.age = age;
    }

    public List<String> getRequiredProfile() {
        return requiredProfile;
    }

    public int getTargetSatisfaction() {
        return targetSatisfaction;
    }

    public WaitingTimeEnum getRequiredWait() {
        return requiredWait;
    }

    public DnaSexEnum getDnaSex() {
        return DnaSex;
    }

    public AgeEnum getAge() {
        return age;
    }

    public List<LineTopic> getRelatedTopics() {
        return relatedTopics;
    }

    public enum WaitingTimeEnum{
        NONE, SHORT, LONG;
    }

    public enum DnaSexEnum{
        MALE, FEMALE, ANY;
    }

    public enum AgeEnum{
        SENIOR, ADULT, ANY;
    }
}
