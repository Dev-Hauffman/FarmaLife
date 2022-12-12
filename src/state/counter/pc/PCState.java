package state.counter.pc;

import java.util.ArrayList;
import java.util.List;

import entity.GameObject;
import state.counter.WorkCounterState;

public abstract class PCState {
  protected List<GameObject> objects;

  public PCState(WorkCounterState state){
    objects = new ArrayList<>();        
    createComputer(state);
  }

  protected abstract void createComputer(WorkCounterState state);

  public List<GameObject> getObjects() {
    return objects;
  }

  public void update(WorkCounterState state) {
  }

  public void cleanUp(WorkCounterState state){
    state.getGameObject().removeAll(objects);
  }

}
