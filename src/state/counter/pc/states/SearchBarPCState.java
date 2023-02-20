package state.counter.pc.states;

import java.util.ArrayList;
import java.util.List;

import catalog.medicine.MedicineInfo;
import catalog.medicine.MedicineLoader;
import catalog.medicine.MedicineStock;
import controller.KeyboardController;
import core.Position;
import entity.GameObject;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import state.State;
import state.counter.WorkCounterState;
import state.counter.pc.PCState;
import text.GameText;
import ui.clickable.UIButton;
import ui.object.ButtonObject;
import ui.object.SearchBar;
import ui.object.UIObject;

public class SearchBarPCState extends PCState{
    private KeyboardController controller;
    private SearchBar searchArea;
    private UIObject screen;
    private List<MedicineInfo> storedInput;
    private List<TableRow> table;
    private List<String>chosenResults;
    private int tablePage;
    private ButtonObject nextArrow;
    private ButtonObject previousArrow;
    private ButtonObject returnArrow;

    public SearchBarPCState(WorkCounterState state) {
        super(state);
        controller = new KeyboardController(state.getInput());
        storedInput = new ArrayList<>();
        tablePage = 0;
    }

    @Override
    protected void createComputer(WorkCounterState state) {
        chosenResults = new ArrayList<>();
        table = new ArrayList<>();
        screen = new UIObject("startcomputerscreen", new Position(714, 434), state, 5);
        UIObject searchBarBackground = new UIObject("searchbarbackground", new Position(36, 60), state, 6);
        screen.addChildren(searchBarBackground);
        searchArea = new SearchBar("typingareawhite", new Position(0, 0), state, 6);
        searchArea.setPosX((searchBarBackground.getSprite().getWidth(null)/2)-(searchArea.getSprite().getWidth(null)/2));
        searchArea.setPosY((searchBarBackground.getSprite().getHeight(null)/2)-(searchArea.getSprite().getHeight(null)/2));
        searchBarBackground.addChildren(searchArea);
        int initialRowY = 100;
        for (int i = 0; i < 6; i++) {
            UIObject resultSpot = new UIObject("searchresultnamefield", new Position(36, initialRowY), state, 6);
            UIObject stockSpot = new UIObject("searchresultstockfield", new Position(370, initialRowY), state, 6);
            GameText medicine = new GameText(" ", state, "testFont", new Position(2, 0), 16, 6);
            medicine.setPosY((resultSpot.getSprite().getHeight(null)/2)-(medicine.getStringSpriteHeight()/2));
            resultSpot.addChildren(medicine);
            GameText stock = new GameText(" ", state, "testFont", new Position(2, 0), 7, 6);
            stock.setPosY((stockSpot.getSprite().getHeight(null)/2)-(stock.getStringSpriteHeight()/2));
            stockSpot.addChildren(stock);
            ButtonObject buttonSpot = new ButtonObject("+", "addcartbuttonunclicked", "addcartbuttonclicked", state, new Position(0, 0), 7, false, screen, (localState) -> {
                for (TableRow tableEntry : table) {
                    if (tableEntry.button.WasClicked()){
                        if (!chosenResults.contains(tableEntry.medicineName.getText())) {
                            chosenResults.add(tableEntry.medicineName.getText());
                        }
                    }
                }
            });   
            buttonSpot.setDisabled(true);         
            buttonSpot.setPosition(new Position(450, initialRowY));
            objects.add(buttonSpot);
            screen.addChildren(resultSpot);
            screen.addChildren(stockSpot);
            table.add(new TableRow(medicine, stock, buttonSpot));
            initialRowY += 37;
        }        
        objects.add(screen);
        nextArrow = new ButtonObject(null, "arrowright", "arrowright", state, new Position(225, 165), 6, false, screen, (localState) -> {
                tablePage++;
                displayResults(state);
                if (storedInput.size() < 6*(tablePage + 1)) {
                    state.getGameObject().remove(nextArrow);
                }
                if (tablePage > 0) {
                    if (!state.getGameObject().contains(previousArrow)) {
                        state.getGameObject().add(previousArrow);                    
                    }
                }
            }
        );
        previousArrow = new ButtonObject(null, "arrowleft", "arrowleft", state, new Position(0, 165), 6, false, screen, (localState) -> {
                if (tablePage > 0) {
                    tablePage--;
                }
                displayResults(state);
                if (tablePage == 0) {
                    state.getGameObject().remove(previousArrow);
                }
                if (storedInput.size() > 6*(tablePage + 1)) {
                    if (!state.getGameObject().contains(nextArrow)) {
                        state.getGameObject().add(nextArrow);                    
                    }
                }
                displayResults(state);
            }
        );
        returnArrow = new ButtonObject(null, "arrowleft", "arrowleft", state, new Position(0, 5), 6, false, screen, (localState) -> {
                state.getInput().clearBufferedKeys();
                state.changePCState(new CartPCState(state, chosenResults));
                System.out.println(chosenResults.toString());
            }
        );
        objects.add(returnArrow);
    }

    public void displayResults(State state){
        if (storedInput.size() > 0) {
            for (int i = 0; i < 6; i++) {
                if (i + (tablePage*6)  < storedInput.size()) {
                    table.get(i).medicineName.setText(storedInput.get(i + (tablePage*6)).getName().toUpperCase());
                    table.get(i).stock.setText(storedInput.get(i + (tablePage*6)).getAvailability());
                    if (table.get(i).stock.getText().equals("EM ESTOQUE") || table.get(i).stock.getText().equals("IN STOCK")) {
                        table.get(i).button.setDisabled(false);
                    }
                }else{
                    table.get(i).medicineName.setText(" ");
                    table.get(i).stock.setText(" ");
                }
            }
        }
    }

    @Override
    public void update(WorkCounterState state) {
        super.update(state);
        if (controller != null && controller.isRequestingEnter() && ! searchArea.getSearchBarContent().isEmpty() && searchArea.isWritable()) {
            storedInput.clear();
            state.getGameObject().remove(nextArrow);
            tablePage = 0;
            for (MedicineInfo entry : state.getStock().getMedicineList()) {
                if (entry.getName().contains(searchArea.getSearchBarContent().getText().toLowerCase())) {
                    storedInput.add(entry);
                }else{
                    if (GameSettings.language == Language.PORTUGUESE) {
                        table.get(0).medicineName.setText("sem resultados");    
                    }else{
                        table.get(0).medicineName.setText("no results");
                    }
                    table.get(0).stock.setText(" ");
                }
            }
            displayResults(state);            
            if (storedInput.size() > 6*(tablePage + 1)) {
                if (!state.getGameObject().contains(nextArrow)) {
                    state.getGameObject().add(nextArrow);                    
                }
            }
        }
    }

    private class TableRow{
        private GameText medicineName;
        private GameText stock;
        private ButtonObject button;

        public TableRow(GameText medicine, GameText stock, ButtonObject button){
            medicineName = medicine;
            this.stock = stock;
            this.button = button;
        }
    }
    
}
