/*
* The sprites on the spritesheet starts look south and rotate clockwise for this to work
*/
package core;

public enum Direction {
    S(0),
    SW(1),
    W(2),
    NW(3),
    N(4),
    NE(5),
    E(6),
    SE(7)
    ;

    private int animationRow;

    private Direction(int animationRow) {
        this.animationRow = animationRow;
    }

    public static Direction fromMotion(Motion motion) {
        double x = motion.getVector().getX();
        double y = motion.getVector().getY();

        if (x == 0 && y > 0) { // why y>0 is south and not north? Because this is the value that will be discounted of the current position
            return S;
        }
        if (x < 0 && y == 0) {
            return W;
        }
        if (x == 0 && y < 0) {
            return N;
        }
        if (x > 0 && y == 0) {
            return E;
        }
        if (x < 0 && y > 0) {
            return SW;
        }
        if (x < 0 && y < 0) {
            return NW;
        }
        if (x > 0 && y < 0) {
            return NE;
        }
        if (x > 0 && y > 0) {
            return SE;
        }

        return S;
    }

    public int getAnimationRow() {
        return animationRow;
    }
    
}
