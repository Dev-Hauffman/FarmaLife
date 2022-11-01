package state.counter.pc;

import java.util.ArrayList;
import java.util.List;

import entity.GameObject;
import state.State;

public abstract class PCState {
  protected List<GameObject> objects;

  public PCState(State state){
      objects = new ArrayList<>();        
      createComputer(state);
  }

  protected abstract void createComputer(State state);

  public List<GameObject> getObjects() {
  return objects;
  }

  public void update(State state) {
  }

  public void cleanUp(State state){
    state.getGameObject().removeAll(objects);
  }

}
