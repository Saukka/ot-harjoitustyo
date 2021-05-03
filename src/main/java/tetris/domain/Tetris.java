
package tetris.domain;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import tetris.ui.UserInterface;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;


public class Tetris {
    
    public void start(Stage window, ArrayList<KeyCode> keys) {
        UserInterface ui = new UserInterface();
        
        Rectangle[] rectangleNext = new Rectangle[4];
        Rectangle[] rectangleHold = new Rectangle[4];
        
        // alusta jossa näkyy seuraava palikka
        Rectangle nextBox = new Rectangle(0, 56, 168, 112);
        nextBox.setArcHeight(22);
        nextBox.setArcWidth(22);
        nextBox.setFill(Color.web("0x3D3D3D"));
        
        Pane right = new Pane();
        right.setPrefSize(210, 560);
        right.getChildren().add(nextBox);
        
        Pane left = new Pane();
        left.setPrefSize(210, 560);
        
        Rectangle holdBox = new Rectangle(20, 56, 168, 112);
        holdBox.setArcHeight(22);
        holdBox.setArcWidth(22);
        left.getChildren().add(holdBox);
        
        Board board = new Board();
        Text score = new Text("SCORE: " + board.score);
        Text lines = new Text("LINES: " + board.lines);
        Text level = new Text("LEVEL: " + board.level);
        
        level.setTranslateX(28);
        level.setTranslateY(360);
        level.setFont(Font.font("verdana", 20));
        score.setTranslateX(28);
        score.setTranslateY(400);
        score.setFont(Font.font("verdana", 20));
        lines.setTranslateX(28);
        lines.setTranslateY(440);
        lines.setFont(Font.font("verdana", 20));
        left.getChildren().add(score);
        left.getChildren().add(lines);
        left.getChildren().add(level);
        
        Tetromino next = new Tetromino();
        next.setCurrentShape(Tetromino.piece.values()[board.nextPiece]);
        board.drawPiece(3 - next.minX() - ((double) next.width() / 2) , 4, next, rectangleNext, right);
        
        Tetromino hold = new Tetromino();
        hold.setCurrentShape(Tetromino.piece.values()[board.holdPiece]);
        
        board.pane.setPrefSize(board.widthPX, board.heightPX);
        BorderPane view = new BorderPane();
        view.setBackground(new Background(new BackgroundFill(Color.web("0x8A2EA6"), CornerRadii.EMPTY, Insets.EMPTY)));
        view.setCenter(board.pane);
        view.setLeft(left);
        view.setRight(right);
        view.setPrefSize(700, 560);
        
        Scene scene = new Scene(view);
        window.setScene(scene);
        window.show();
        
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
        
        board.newPiece(false, board.nextPiece);
        
        
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
                        board.movePieceLeft();
                        extraTimeClock -= times * 5;
                        times--;
                        accelerationLeft = true;
                    }
                    if (accelerationClock > accelerationTimes * 3) {
                        accelerationTimes++;
                        board.movePieceLeft();
                        extraTimeClock -= times * 5;
                        times--;
                    } 
                } else {
                    accelerationLeft = false;
                }
                
                if (movementButtons.getOrDefault(moveRightK, false)) {
                    accelerationClock++;
                    if (!accelerationRight) {
                        board.movePieceRight();
                        extraTimeClock -= times * 5;
                        times--;
                        accelerationRight = true;
                    }
                    if (accelerationClock > accelerationTimes * 3) {
                        accelerationTimes++;
                        board.movePieceRight();
                        extraTimeClock -= times * 5;
                        times--;
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
                    board.rotatePiece(0);
                    extraTimeClock -= times * 5;
                    times--;
                    accelerationOther = true;
                }
                if ((otherButtons.getOrDefault(rotateRightK, false)) && !accelerationOther) {
                    board.rotatePiece(1);
                    extraTimeClock -= times * 5;
                    times--;
                    accelerationOther = true;
                }
                
                if (hardDrop.get() == true && !accelerationDrop) {
                        board.hardDrop();
                        accelerationDrop = true;
                } 
                if (hardDrop.get() == false) {
                    accelerationDrop = false;
                }
                    
                if ((otherButtons.getOrDefault(holdK, false)) && !accelerationOther) {
                    accelerationOther = true;
                    if (board.holdPiece == 0) {
                        board.swapHold();
                        hold.setCurrentShape(Tetromino.piece.values()[board.holdPiece]);
                        board.drawPiece(3.5, 3.5, hold, rectangleHold, left);
                    } else if (board.canSwapHold) {
                        board.swapHold();
                        left.getChildren().remove(4, 8);
                        hold.setCurrentShape(Tetromino.piece.values()[board.holdPiece]);
                        board.drawPiece(3.5, 3.5, hold, rectangleHold, left);
                    }
                }
                
                if (board.nextPiece != board.currentPiece.current.ordinal()) {
                    right.getChildren().remove(1, 5);
                    next.setCurrentShape(Tetromino.piece.values()[board.nextPiece]);
                    board.drawPiece(3 - next.minX() - ((double) next.width() / 2) , 4, next, rectangleNext, right);
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
                if (board.checkBelow(board.currentY)) {
                        board.movePieceDown(1, softDrop);
                        clock = 0;
                        timeStarted = false;
                    } else if (place) {
                        board.movePieceDown(1, softDrop);
                        clock = 0;
                        place = false;
                        timeStarted = false;
                    } else if (!timeStarted) {
                        extraTimeClock = 0;
                        timeStarted = true;
                        times = 3;
                    }
            }
        }.start();
        
    };
    
}