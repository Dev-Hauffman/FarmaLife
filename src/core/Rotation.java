package core;

public enum Rotation {
    FRONT(0),
    HALF_RIGHT(1),
    SIDE_RIGHT(2)
    ;

    private int frame;

    private Rotation(int frame) {
        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }
}
