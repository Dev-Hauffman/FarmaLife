package controller;
/*
* Controller is responsible for the keyboard keys used in the game
*/

public interface IController {
    
    boolean isRequestingUp();

    boolean isRequestingDown();

    boolean isRequestingLeft();

    boolean isRequestingRight();
}
