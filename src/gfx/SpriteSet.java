package gfx;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;

public class SpriteSet {
    
    private Map<String, Image> animationSheets;

    public SpriteSet() {
        this.animationSheets = new HashMap<>();
    }

    public SpriteSet(Image image) {
        this.animationSheets = new HashMap<>();
        addSheet("default", image);
    }
    
    public void addSheet(String name, Image animationSheet) {
        animationSheets.put(name, animationSheet);
    }

    public Image getOrGetDefault(String name) {
        if (animationSheets.containsKey(name)) {
            return animationSheets.get(name);
        }
        return animationSheets.get("default");
    }

    public void checkAnimationSheets(){ //REMOVE, just for debug
        System.out.println(animationSheets.keySet().toString());
    }
}
