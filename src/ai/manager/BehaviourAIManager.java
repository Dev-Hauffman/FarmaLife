package ai.manager;

import java.util.HashMap;
import java.util.Map;

import ai.manager.BaseHeadAIManager.BaseHeadEnum;
import ai.manager.BodyAIManager.BodyEnum;
import ai.manager.EyebrowAIManager.EyebrowEnum;
import ai.manager.EyelidsAIManager.EyelidEnum;
import ai.manager.IrisAIManager.IrisEnum;
import ai.manager.MouthAIManager.MouthEnum;
import entity.patient.Patient;
import state.State;

public class BehaviourAIManager extends AIManager{
    private BaseHeadAIManager baseHead;
    private BodyAIManager body;
    private EyebrowAIManager eyebrow;
    private EyelidsAIManager eyelids;
    private IrisAIManager iris;
    private MouthAIManager mouth;

    public BehaviourAIManager(){
        baseHead = new BaseHeadAIManager();
        baseHead.setParent(this);
        body = new BodyAIManager();
        body.setParent(this);
        eyebrow = new EyebrowAIManager();
        eyebrow.setParent(this);
        eyelids = new EyelidsAIManager();
        eyelids.setParent(this);
        iris = new IrisAIManager();
        iris.setParent(this);
        mouth = new MouthAIManager();
        mouth.setParent(this);
        
    }

    @Override
    public void update(State state, Patient patient) {
        body.update(state, patient);
        baseHead.update(state, patient);
        eyebrow.update(state, patient);
        eyelids.update(state, patient);
        mouth.update(state, patient);
        iris.update(state, patient);
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((BehaviorEnum)nextState) {
            case MOVING_TO_START:
                baseHead.transitionTo(BaseHeadEnum.MOVE_TO_START);
                body.transitionTo(BodyEnum.MOVE_TO_START);
                eyebrow.transitionTo(EyebrowEnum.IDLE);
                eyelids.transitionTo(EyelidEnum.BLINK);
                iris.transitionTo(IrisEnum.IDLE);
                mouth.transitionTo(MouthEnum.IDLE);
                break;

            case MOVING_TO_END:
                baseHead.transitionTo(BaseHeadEnum.MOVING_TO_END);
                body.transitionTo(BodyEnum.MOVING_TO_END);
                eyebrow.transitionTo(eyebrow.getCurrentState());
                eyelids.transitionTo(EyelidEnum.BLINK);
                iris.transitionTo(IrisEnum.IDLE);
                mouth.transitionTo(MouthEnum.IDLE);
                break;
        
            default:
                break;
        }
    }
    
    public enum BehaviorEnum implements IState{
        MOVING_TO_START, IDLE, HAND_IN_PRESCRIPTION, MOVING_TO_END;
    }
}
