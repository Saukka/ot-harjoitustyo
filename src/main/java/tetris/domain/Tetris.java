
package tetris.domain;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import tetris.ui.MenuUI;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import tetris.domain.Tetromino.shape;

/**
 * Luokassa asetetaan peliin kuuluvat ulkoasu-komponentit jotka vaihtelevat peliä pelatessa. Luokka sisältää AnimationTimerin, jossa käsitellään käyttäjän inputit.
 */
public class Tetris {
    
    Stage window;
    Scene scene;
    BorderPane view;
    Pane left;
    Pane right;
    
    public Tetris(Stage window, Scene scene, BorderPane view, Pane left, Pane right) {
        this.window = window;
        this.scene = scene;
        this.view = view;
        this.left = left;
        this.right = right;
    }
    
    public void start(ArrayList<KeyCode> keys, Text score, Text lines, Text level) {
        
        Map<KeyCode, Boolean> movementButtons = new HashMap<>();
        Map<KeyCode, Boolean> otherButtons = new HashMap<>();
        AtomicReference<Boolean> hardDrop = new AtomicReference<>();
        hardDrop.set(false);
        
        KeyCode rotateLeftK = keys.get(0);
        KeyCode rotateRightK = keys.get(1);
        KeyCode holdK = keys.get(2);
        KeyCode hardDropK = keys.get(3);
        KeyCode moveLeftK = keys.get(4);
        KeyCode moveDownK = keys.get(5);
        KeyCode moveRightK = keys.get(6);
        
        scene.setOnKeyPressed(event -> {
            KeyCode k = event.getCode();
            if (k == moveLeftK || k == moveDownK || k == moveRightK) {
                movementButtons.put(event.getCode(), Boolean.TRUE);
            } else if (k == hardDropK) {
                hardDrop.set(true);
            } else {
                otherButtons.put(event.getCode(), Boolean.TRUE);
            }
        });
        scene.setOnKeyReleased(event -> {
            KeyCode c = event.getCode();
            if (c == moveLeftK || c == moveDownK || c == moveRightK) {
                movementButtons.put(event.getCode(), Boolean.FALSE);
            } else if (c == hardDropK) {
                hardDrop.set(false);
            } else {
                otherButtons.put(event.getCode(), Boolean.FALSE);
            }
        });
        
        Board board = new Board();
        view.setCenter(board.pane);
        
        board.newPiece(false, board.nextPiece);
        
        CurrentPiece piece = board.current;
        
        Square[] squareNext = new Square[4];
        Square[] squareHold = new Square[4];
        
        Tetromino next = new Tetromino();
        next.setCurrentShape(shape.values()[board.nextPiece]);
        board.drawPiece(3 - next.minX() - ((double) next.width() / 2) , 3 - next.minY() - ((double) next.height() / 2), next, squareNext, right);
        
        Tetromino hold = new Tetromino();
        hold.setCurrentShape(shape.values()[board.holdPiece]);
        
        MenuUI ui = new MenuUI();
        
        new AnimationTimer() {

            float clock = 0;
            
            // seuraavien muuttujien avulla katsotaan, pidetäänkö näppäintä pohjassa
            int accelerationClock = 0;
            int accelerationTimes = 2;
            boolean accelerationLeft = false;
            boolean accelerationRight = false;
            boolean accelerationOther = false;
            boolean accelerationDrop = false;
            
            // seuraavien muuttujien avulla pelaajalle annetaan hieman aikaa liikuttaa palikkaa kun palikan alla ei ole tilaa
            boolean place = false;
            float extraTimeClock = 0;
            boolean timeStarted = false;
            int times = 4;

            @Override
            public void handle(long time) {

                if (Collections.frequency(movementButtons.values(), true) == 0) {
                    accelerationClock = 0;
                    accelerationTimes = 4;
                } 
                
                if (Collections.frequency(otherButtons.values(), true) == 0) {
                    accelerationOther = false;
                }
                
                if (movementButtons.getOrDefault(moveLeftK, false)) {
                    accelerationClock++;
                    if (!accelerationLeft) {
                        piece.moveVertical(-1);
                        giveTime();
                        accelerationLeft = true;
                    }
                    if (accelerationClock > accelerationTimes * 3) {
                        accelerationTimes++;
                        piece.moveVertical(-1);
                        giveTime();
                    } 
                } else {
                    accelerationLeft = false;
                }
                
                if (movementButtons.getOrDefault(moveRightK, false)) {
                    accelerationClock++;
                    if (!accelerationRight) {
                        piece.moveVertical(1);
                        giveTime();
                        accelerationRight = true;
                    }
                    if (accelerationClock > accelerationTimes * 3) {
                        accelerationTimes++;
                        piece.moveVertical(1);
                        giveTime();
                    }
                } else {
                    accelerationRight = false;
                }
                
                if (movementButtons.getOrDefault(moveDownK, false)) {
                    accelerationClock++;
                    
                    if (accelerationClock > accelerationTimes * 2) {
                        accelerationTimes++;
                        movePieceDown(true);
                    }   
                } 
                
                if ((otherButtons.getOrDefault(rotateLeftK, false)) && !accelerationOther) {
                    piece.rotate(0);
                    giveTime();
                    accelerationOther = true;
                }
                if ((otherButtons.getOrDefault(rotateRightK, false)) && !accelerationOther) {
                    piece.rotate(1);
                    giveTime();
                    accelerationOther = true;
                }
                
                if (hardDrop.get() == true && !accelerationDrop) {
                        piece.hardDrop();
                        accelerationDrop = true;
                } 
                if (hardDrop.get() == false) {
                    accelerationDrop = false;
                }
                    
                if ((otherButtons.getOrDefault(holdK, false))) {
                    if (board.holdPiece == 0) {
                        board.swapHold();
                        hold.setCurrentShape(shape.values()[board.holdPiece]);
                        board.drawPiece(4 - hold.minX() - ((double) hold.width() / 2) , 4 - hold.minY() - ((double) hold.height() / 2), hold, squareHold, left);
                    } else if (board.canSwapHold) {
                        board.swapHold();
                        left.getChildren().remove(4, (5 * 4));
                        hold.setCurrentShape(shape.values()[board.holdPiece]);
                        board.drawPiece(4 - hold.minX() - ((double) hold.width() / 2) , 4 - hold.minY() - ((double) hold.height() / 2), hold, squareHold, left);
                    }
                }
                
                if (board.nextPiece != piece.piece.shape.ordinal()) {
                    right.getChildren().remove(1, 1 + (4 * 4));
                    next.setCurrentShape(shape.values()[board.nextPiece]);
                    board.drawPiece(3 - next.minX() - ((double) next.width() / 2) , 4 - next.minY() - ((double) next.height() / 2), next, squareNext, right);
                }

                if (board.end) {
                    Button menu = new Button("Main menu");
                    menu.setFocusTraversable(false);
                    menu.setLayoutX(45);
                    menu.setLayoutY(465);
                    left.getChildren().add(menu);

                    menu.setOnAction(e-> {
                        ui.start(window);
                    }); 
                    stop();
                }
                
                extraTimeClock++;

                if (timeStarted && extraTimeClock > 60 - 3 * board.level) {
                    place = true;
                }

                clock++;
                if (clock / 60 > 1.1 - Math.log10(board.level)) {
                    movePieceDown(false);
                }

                score.setText("SCORE: " + board.score);
                lines.setText("LINES: " + board.lines);
                level.setText("LEVEL: " + board.level);

            }
            
            public void movePieceDown(boolean softDrop) {
                if (board.checkBelow(piece.y)) {
                        piece.moveDown(1, softDrop);
                        clock = 0;
                        timeStarted = false;
                    } else if (place) {
                        piece.moveDown(1, softDrop);
                        clock = 0;
                        place = false;
                        timeStarted = false;
                    } else if (!timeStarted) {
                        extraTimeClock = 0;
                        timeStarted = true;
                        times = 3;
                    }
            }
            public void giveTime() {
                extraTimeClock -= times * 5;
                times--;
            }
            
        }.start();
        
    };
    
}