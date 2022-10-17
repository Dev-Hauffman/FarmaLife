package entity.patient;

import java.util.HashMap;
import java.util.Map;

import core.Position;
import core.Size;
import entity.DynamicObject;
import entity.GameObject;
import gfx.SpriteLibrary;

public class Patient {
    private Map<String, GameObject> bodyParts;

    public Patient(SpriteLibrary spriteLibrary) {
        bodyParts = new HashMap<>();
        bodyParts.put("body", new DynamicObject("default", new Size(657, 565), new Position(81, 143), spriteLibrary, 3));
    }

    public Map<String, GameObject> getBodyParts() {
        return bodyParts;
    }    
    
}
