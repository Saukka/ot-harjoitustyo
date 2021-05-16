
package tetris.domain;

import java.io.IOException;
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
import tetris.dao.HighScores;
import tetris.domain.Tetromino.SHAPE;

/**
 * Luokassa käsitellään käyttäjän inputit AnimationTimerilla ja pidetään ulkoasun komponentit ajan tasalla
 */

public class Tetris {
    
    Stage window;
    Scene scene;
    BorderPane view;
    Pane left;
    Pane right;
    Pane pauseScreen;
    Button pauseButton;
    Button continueButton;
    HighScores scores;
    
    public Tetris(Stage window, Scene scene, BorderPane view, Pane left, Pane right, Pane pauseScreen, Button pauseButton, Button continueButton, HighScores scores) {
        this.window = window;
        this.scene = scene;
        this.view = view;
        this.left = left;
        this.right = right;
        this.pauseButton = pauseButton;
        this.continueButton = continueButton;
        this.pauseScreen = pauseScreen;
        this.scores = scores;
    }
    
    public void start(ArrayList<KeyCode> keys, int startLevel, Text score, Text lines, Text level, boolean thin) {
        
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
        
        Board board = new Board(startLevel, thin);
        view.setCenter(board.pane);
        
        board.newPiece(false, board.nextPiece);
        
        CurrentPiece piece = board.current;
        
        Square[] squareNext = new Square[4];
        Square[] squareHold = new Square[4];
        
        Tetromino next = new Tetromino();
        next.setCurrentShape(SHAPE.values()[board.nextPiece]);
        board.drawPiece(3 - next.minX() - ((double) next.width() / 2) , 3 - next.minY() - ((double) next.height() / 2), next, squareNext, right);
        
        Tetromino hold = new Tetromino();
        hold.setCurrentShape(SHAPE.values()[board.holdPiece]);
        
        MenuUI ui = new MenuUI();
        
        new AnimationTimer() {
            
            float clock = 0;
            
            // seuraavien muuttujien avulla katsotaan, pidetäänkö näppäintä pohjassa
            int accelerationClock = 0;
            int accelerationTimes = 4;
            boolean accelerationLeft = false;
            boolean accelerationRight = false;
            boolean accelerationRotate = false;
            boolean accelerationDrop = false;
            
            // seuraavien muuttujien avulla pelaajalle annetaan hieman aikaa liikuttaa palikkaa kun palikan alla ei ole tilaa
            boolean place = false;
            double extraTimeClock = 0;
            boolean timeStarted = false;
            int times = 8;

            @Override
            public void handle(long time) {

                if (Collections.frequency(movementButtons.values(), true) == 0) {
                    accelerationClock = 0;
                    accelerationTimes = 4;
                } 
                
                if (Collections.frequency(otherButtons.values(), true) == 0) {
                    accelerationRotate = false;
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
                
                if ((otherButtons.getOrDefault(rotateLeftK, false)) && !accelerationRotate) {
                    piece.rotate(0);
                    giveTime();
                    accelerationRotate = true;
                }
                if ((otherButtons.getOrDefault(rotateRightK, false)) && !accelerationRotate) {
                    piece.rotate(1);
                    giveTime();
                    accelerationRotate = true;
                }
                
                if (hardDrop.get() == true && !accelerationDrop) {
                    piece.hardDrop();
                    accelerationDrop = true;
                } 
                if (hardDrop.get() == false) {
                    accelerationDrop = false;
                }
                
                pauseButton.setOnAction(e -> {
                    stop();
                    view.setCenter(pauseScreen);
                }); 
                continueButton.setOnAction(e -> {
                    view.setCenter(board.pane);
                    start();
                });
                    
                if ((otherButtons.getOrDefault(holdK, false))) {
                    if (board.holdPiece == 0) {
                        board.swapHold();
                        hold.setCurrentShape(SHAPE.values()[board.holdPiece]);
                        board.drawPiece(4 - hold.minX() - ((double) hold.width() / 2) , 4 - hold.minY() - ((double) hold.height() / 2), hold, squareHold, left);
                    } else if (board.canSwapHold) {
                        board.swapHold();
                        left.getChildren().remove(4, (5 * 4));
                        hold.setCurrentShape(SHAPE.values()[board.holdPiece]);
                        board.drawPiece(4 - hold.minX() - ((double) hold.width() / 2) , 4 - hold.minY() - ((double) hold.height() / 2), hold, squareHold, left);
                    }
                }
                
                if (board.nextPiece != piece.piece.shape.ordinal()) {
                    right.getChildren().remove(2, 2 + (4 * 4));
                    next.setCurrentShape(SHAPE.values()[board.nextPiece]);
                    board.drawPiece(3 - next.minX() - ((double) next.width() / 2) , 4 - next.minY() - ((double) next.height() / 2), next, squareNext, right);
                }

                if (board.end) {
                    pauseButton.setVisible(false);
                    Button menu = new Button("Main menu");
                    menu.setFocusTraversable(false);
                    menu.setLayoutX(45);
                    menu.setLayoutY(465);
                    left.getChildren().add(menu);
                    menu.setOnAction(e-> {
                        try {
                            ui.start(window);
                        } catch (IOException ex) {
                        }
                    });
                    try {
                        scores.addScore(board.score, thin); 
                    } catch (Exception e) {
                        
                    }
                    stop();
                    
                }
                
                extraTimeClock++;

                if (timeStarted && extraTimeClock > 25) {
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
                    if (board.checkBelow(piece.y + 1)) {
                        timeStarted = false;
                    }
                } else if (place) {
                    piece.moveDown(1, softDrop);
                    clock = 0;
                    place = false;
                    timeStarted = false;
                } else if (!timeStarted) {
                    extraTimeClock = 0;
                    timeStarted = true;
                    times = 8;
                }
            }
            public void giveTime() {
                if (times > 0) {
                    extraTimeClock = 0.7 * board.level - times;
                    times--;
                }
            }
            
        }.start();
        
    };
    
}