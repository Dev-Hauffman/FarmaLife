package entity.patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Position;
import core.Size;
import entity.DynamicObject;
import entity.GameObject;
import gfx.SpriteLibrary;

public class Patient {
    private Map<String, GameObject> bodyParts;
    private List<String> cart;

    public Patient(SpriteLibrary spriteLibrary) {
        cart = new ArrayList<>();
        bodyParts = new HashMap<>();
        bodyParts.put("body", new DynamicObject("default", new Size(657, 565), new Position(81, 143), spriteLibrary, 2));
    }

    public Map<String, GameObject> getBodyParts() {
        return bodyParts;
    }

    public List<String> getCart() {
        return cart;
    }
    
}
