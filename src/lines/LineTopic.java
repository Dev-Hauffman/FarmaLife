package lines;

import java.util.List;

public class LineTopic {
    private int id;
    private List<LineTopic> parentTopics;
    private List<LineTopic> childrenTopics;
    private List<Integer> relatedTopicsID;
    private boolean initialTopic;
    private List<Line> responses;
    private String engName;
    private String ptName;
}
