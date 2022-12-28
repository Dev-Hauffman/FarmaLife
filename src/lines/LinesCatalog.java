package lines;

import java.util.ArrayList;
import java.util.List;

public class LinesCatalog {
    private List<Line> fullLineList;
    private List<Line> startingList;
    private List<LineTopic> topicCatalog;
    private List<LineTopic> startingTopics;

    public LinesCatalog(){
        LineLoader lineLoader = new LineLoader();
        fullLineList = new ArrayList<>();
        startingList = new ArrayList<>();
        topicCatalog = new ArrayList<>();
        startingTopics = new ArrayList<>();
        LinesCatalog catalog = lineLoader.loadLines(this);
        this.fullLineList = catalog.getFullLineList();
        this.startingList = catalog.getStartingList();
        this.topicCatalog = catalog.getTopicCatalog();
        this.startingTopics = catalog.getStartingTopics();
        // connectLines(); // not using this for now as it would required 3 loops, instead, i'll try to load the lines based on their id when it's necessary
    }

    // private void connectLines() {
    //     for (Line line : fullLineList) {
    //         for (Integer response : line.getReponsesId()) {
    //             line.getResponses().add(fullLineList.get(response));
    //         }
    //     }
    // }

    public List<Line> getFullLineList() {
        return fullLineList;
    }

    public List<Line> getStartingList() {
        return startingList;
    }

    public List<LineTopic> getTopicCatalog() {
        return topicCatalog;
    }

    public List<LineTopic> getStartingTopics() {
        return startingTopics;
    }
}
