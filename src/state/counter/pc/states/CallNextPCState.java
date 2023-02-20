package state.counter.pc.states;

import java.util.ArrayList;
import java.util.List;

import core.Position;
import entity.GameObject;
import entity.StaticObject;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
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

	public CallNextPCState(WorkCounterState state){
		super(state);
		canChangeState = false;
		timer = 100;
	}

	protected void createComputer(WorkCounterState state) {
		StaticObject screen = new StaticObject("startcomputerscreen", new Position(714, 434), state.getSpriteLibrary(), 5);
		UIObject display = new UIObject("clientcountercomputerdisplay", new Position(113, 93), state, 6, screen);
		String ptDisplayString = "Nro de clientes";
		String engDisplayString = "No. of clients";
		GameText displayString = new GameText(
			GameSettings.language == Language.PORTUGUESE? ptDisplayString : engDisplayString, 
			state, 
			"testFont", 
			new Position(0, 9), 
			16, 
			7
		);
		displayString.setPosX((display.getSprite().getWidth(null)/2)-(displayString.getStringSpriteWidth()/2));
		display.addChildren(displayString);
		FlashingText displayNumber = new FlashingText("#" + String.format("%03d", WorkCounterState.patientsCounter), state, "testFont", new Position(0, 0), 32, 7);
		displayNumber.setPosX((display.getSprite().getWidth(null)/2)-(displayNumber.getStringSpriteWidth()/2));
		displayNumber.setPosY(displayString.getStringSpriteHeight() + 24);
		display.addChildren(displayNumber);
		String ptNext = "Proximo";
		String engNext = "Next";
		ButtonObject nextPatient = new ButtonObject(
									GameSettings.language == Language.PORTUGUESE? ptNext : engNext, 
			  	"callnextcomputerbutton", 
					"clickedcallnextcomputerbutton", 
								   	state, 
								  	new Position(0, 0), 
					   	8, 
						true,
								  	screen, 
									(localState) -> {
										state.spawnPatient();
										WorkCounterState.patientsCounter++;
										displayNumber.setText("#" + String.format("%03d", WorkCounterState.patientsCounter));
										displayNumber.flashText(100);
										canChangeState = true;
									}
		);
		nextPatient.setPosition(new Position(display.getSprite().getWidth(null)/2, 200));
		objects.add(screen);
		objects.add(display);
		objects.add(nextPatient);
	}

	@Override
	public void update(WorkCounterState state){
		if(canChangeState){	
			timer--;
			if(timer < 0){
				((WorkCounterState)state).changePCState(new CartPCState(state, new ArrayList<>()));
			}
		}
	}
}
