package entity.humanoid;

import controller.IEntityController;
import entity.GameObject;
import entity.MovingEntity;
import entity.humanoid.action.Action;
import entity.humanoid.effect.Effect;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;
import core.Position;
import core.Size;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;

public class Humanoid extends MovingEntity {
    
    private static List<String> availableCharacters = new ArrayList<>(List.of("dave", "matt", "melissa", "roger"));
    protected Optional<Action> action;
    protected List<Effect> effects;

    public Humanoid(IEntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);

        effects = new ArrayList<>();
        action = Optional.empty();

        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSets(getRandomCharacter()));

        this.collisionBoxSize = new Size(16, 28);
        this.renderOffset = new Position(size.getWidth() / 2, size.getHeight() - 12);
        this.collisionBoxOffset = new Position(collisionBoxSize.getWidth() / 2, collisionBoxSize.getHeight());
    }

    private String getRandomCharacter() {
        Collections.shuffle(availableCharacters);
        return availableCharacters.get(0);
    }

    @Override
    public void update(State state) {
        super.update(state);
        handleAction(state);
        effects.forEach(effect -> effect.update(state, this));

        cleanUp();
    }
    
    private void cleanUp() {
        if (action.isPresent() && action.get().isDone()) {
            action = Optional.empty();
        }
    }

    @Override
    protected String decideAnimation() {
        if (action.isPresent()) {
            return action.get().getAnimationName();
        } else if (motion.isMoving()) {
            return "walk";
        }
        return "stand";
    }
    
    protected void handleAction(State state) {
        if (action.isPresent()) {
            action.get().update(state, this);
        }
    }

    protected void handleMotion() {
        if (action.isPresent()) {
            motion.stop(true, true);
        }
    }

    public void perform(Action action){
        this.action = Optional.of(action);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    @Override
    protected void handleCollision(GameObject other) {        
    }
    
}
