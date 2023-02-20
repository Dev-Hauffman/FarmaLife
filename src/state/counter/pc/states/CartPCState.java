package state.counter.pc.states;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import core.Position;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import state.counter.WorkCounterState;
import state.counter.pc.PCState;
import text.GameText;
import ui.object.ButtonObject;
import ui.object.SearchBar;
import ui.object.UIObject;

public class CartPCState extends PCState{
    private List<CartItem> cart;
    private UIObject cartBackground;
    private boolean refresh;
    private ButtonObject finishButton;

    public CartPCState(WorkCounterState state, List<String> searchResult){
        super(state);
        for (String string : searchResult) {
            if (!state.getActivePatient().getCart().contains(string)) {
                state.getActivePatient().getCart().add(string);
            }
        }
        if (searchResult.size() > 0) {
            setCartResults(state);
        }
    }

    private void setCartResults(WorkCounterState state) {
        int initialNameRowY = 10;
        int initiaIconRowY = 5;
        for (int i = 0; i < 6; i++) {
            if (!(state.getActivePatient().getCart().size() <= i)) {
                UIObject cartItem = new UIObject("cartitem", new Position(10, initialNameRowY), state, 6);
                GameText medicine = new GameText(state.getActivePatient().getCart().get(i), state, "testFont", new Position(2, 0), 16, 6);
                medicine.setPosY((cartItem.getSprite().getHeight(null)/2)-(medicine.getStringSpriteHeight()/2));
                cartItem.addChildren(medicine);
                cartBackground.addChildren(cartItem);
                ButtonObject trashButton = new ButtonObject(null, "carttrashicon", "carttrashicon", state, new Position(210, initiaIconRowY), 7, false, (localState) -> {
                    System.out.println("clicked to remove");
                    for (CartItem cartEntry : cart) {
                        if (cartEntry.button.WasClicked()){
                            state.getActivePatient().getCart().remove(cartEntry.getMedicineName().getText());
                            break;
                        }
                    }                   
                    refresh = true;
                });        
                cartBackground.addChildren(trashButton);
                cart.add(new CartItem(medicine, trashButton));
            }else{
                cartBackground.loadGraphics(state.getSpriteLibrary());
            }
            initialNameRowY += 30;
            initiaIconRowY += 15;
        }
    }

    @Override
    protected void createComputer(WorkCounterState state) {
        cart = new ArrayList<>();
        UIObject screen = new UIObject("startcomputerscreen", new Position(714, 434), state, 5);
        GameText patientInfo = new GameText("Cliente " + "#" + String.format("%03d", WorkCounterState.patientsCounter), state, "testFont", new Position(40, 24), 16, 7);
        screen.addChildren(patientInfo);
        UIObject searchBarBackground = new UIObject("searchbarbackground", new Position(36, 60), state, 6);
        screen.addChildren(searchBarBackground);
        SearchBar searchArea = new SearchBar("typingareawhite", new Position(0, 0), state, 6);
        searchArea.setPosX((searchBarBackground.getSprite().getWidth(null)/2)-(searchArea.getSprite().getWidth(null)/2));
        searchArea.setPosY((searchBarBackground.getSprite().getHeight(null)/2)-(searchArea.getSprite().getHeight(null)/2));
        searchBarBackground.addChildren(searchArea); 
        objects.add(screen);
        cartBackground = new UIObject("cartbackground",new Position(36, 110), state, 6);
        screen.addChildren(cartBackground);
        String ptFinish = "FINALIZAR";
        String engFinish = "FINISH";
        finishButton = new ButtonObject(
                            GameSettings.language == Language.PORTUGUESE? ptFinish : engFinish, 
        "callnextcomputerbutton", 
            "clickedcallnextcomputerbutton", 
                            state, 
                            new Position(0, 0), 
                8, 
                true,
                            screen, 
                            (localState) -> {
                                state.changePCState(new CallNextPCState(state));
                                if (state.getScore().getCurrentResult().hasLied()) {
                                    System.out.println("you lied!");// REMOVE
                                }else{
                                    System.out.println("you said the truth!"); // REMOVE
                                }
                                if (state.getScore().getCurrentResult().isWrongMedicine()) {
                                    System.out.println("wrong medicine!"); // REMOVE
                                }else{
                                    System.out.println("right medicine!"); // REMOVE
                                }
                                if (cart.size() > 1) {
                                    state.getScore().getCurrentResult().setRightCart(false);
                                    System.out.println("too many items in the cart!");
                                }else{
                                    // TODO what if cart is empty or if there's on correct or incorrect item inside
                                }
                                state.getScore().changeCurrentResult();
                            }
		);
        finishButton.setDisabled(true);
        finishButton.setPosition(new Position((screen.getSprite().getWidth(null)/2) - (finishButton.getSprite().getWidth(null)/2), 320));
        objects.add(finishButton);
    }

    @Override
    public void update(WorkCounterState state) {        
        super.update(state);
        if (refresh == true) {
            cart.clear();
            cartBackground.getChildren().clear();
            setCartResults(state);
            refresh = false;
        }
    }

    public ButtonObject getFinishButton() {
        return finishButton;
    }

    private class CartItem{
        private GameText medicineName;
        private ButtonObject button;

        public CartItem(GameText medicine, ButtonObject button){
            medicineName = medicine;
            this.button = button;
        }

        public GameText getMedicineName() {
            return medicineName;
        }
    }
}
