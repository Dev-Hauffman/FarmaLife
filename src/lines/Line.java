package lines;

import java.util.ArrayList;
import java.util.List;

import state.counter.StagesEnum;

public class Line {
    private int id;
    private List<Integer> parentId;
    private int nodeLevel;
    private StagesEnum stage;
    private boolean initialLine;
    private List<String> tags;
    private List<String> engLine;
    private List<String> ptLine;
    private List<Integer> responsesId;
    private List<String> actions;
    // private List<Line> responses;

    public Line(){
        parentId = new ArrayList<>();
        tags = new ArrayList<>();
        engLine = new ArrayList<>();
        ptLine = new ArrayList<>();
        responsesId = new ArrayList<>();
        actions = new ArrayList<>();
        // responses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }


    public List<Integer> getParentId() {
        return parentId;
    }


    public int getNodeLevel() {
        return nodeLevel;
    }


    public StagesEnum getStage() {
        return stage;
    }


    public List<String> getTags() {
        return tags;
    }

    public List<String> getEngLine() {
        return engLine;
    }

    public List<String> getPtLine() {
        return ptLine;
    }

    public List<Integer> getResponsesId() {
        return responsesId;
    }

    // public List<Line> getResponses() {
    //     return responses;
    // }

    public List<String> getAction() {
        return actions;
    }

    public boolean isInitialLine() {
        return initialLine;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public void setStage(StagesEnum stage) {
        this.stage = stage;
    }

    public void setInitialLine(boolean initialLine) {
        this.initialLine = initialLine;
    }
}
