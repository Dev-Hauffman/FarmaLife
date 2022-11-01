package state.counter.pc.states;

import java.util.ArrayList;
import java.util.List;

import core.Position;
import entity.GameObject;
import entity.StaticObject;
import state.State;
import state.counter.WorkCounterState;
import state.counter.pc.PCState;
import text.FlashingText;
import text.GameText;
import ui.object.ButtonObject;
import ui.object.UIObject;

public class CallNextPCState extends PCState{
	private boolean canChangeState;
	private int timer;
	public CallNextPCState(State state){
		super(state);
		canChangeState = false;
		timer = 100;
	}

	protected void createComputer(State state) {
		StaticObject screen = new StaticObject("startcomputerscreen", new Position(1014, 534), state.getSpriteLibrary(), 5);
		UIObject display = new UIObject("clientcountercomputerdisplay", new Position(113, 93), state, 6, screen);
		
		GameText displayString = new GameText("Nro de clientes", state, "testFont", new Position(0, 9), 16, 7);
		displayString.setPosX((display.getSprite().getWidth(null)/2)-(displayString.getStringSpriteWidth()/2) + 20);
		display.addChildren(displayString);
		FlashingText displayNumber = new FlashingText("#" + String.format("%03d", WorkCounterState.patientsCounter), state, "testFont", new Position(0, 0), 32, 7);
		displayNumber.setPosX((display.getSprite().getWidth(null)/2)-(displayNumber.getStringSpriteWidth()/2));
		displayNumber.setPosY(displayString.getStringSpriteHeight() + 24);
		display.addChildren(displayNumber);

		ButtonObject nextPatient = new ButtonObject("Next", "callnextcomputerbutton", "clickedcallnextcomputerbutton", state, new Position(0, 0), 8, screen, (localState) -> {
			WorkCounterState.patientsCounter++;
			displayNumber.setText("#" + String.format("%03d", WorkCounterState.patientsCounter));
			displayNumber.flashText(100);
			canChangeState = true;
		});
		nextPatient.setPosition(new Position(display.getSprite().getWidth(null)/2, 200));
		objects.add(screen);
		objects.add(display);
		objects.add(nextPatient);		
	}

	@Override
	public void update(State state){
		if(canChangeState){	
			timer--;
			if(timer < 0){
				((WorkCounterState)state).changePCState(new SearchBarPCState(state));
			}
		}
	}
}
