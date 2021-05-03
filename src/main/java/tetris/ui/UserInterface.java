
package tetris.ui;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import tetris.domain.Tetris;


public class UserInterface extends Application {
    
    @Override
    public void start(Stage window) {
        
        window.setTitle("Tetris");
        
        ArrayList<KeyCode> keys = new ArrayList<>();
        keys.add(KeyCode.Z);
        keys.add(KeyCode.X);
        keys.add(KeyCode.C);
        keys.add(KeyCode.SPACE);
        keys.add(KeyCode.LEFT);
        keys.add(KeyCode.DOWN);
        keys.add(KeyCode.RIGHT);
        
        Button startButton = new Button("Start");
        startButton.setLayoutX(275);
        startButton.setLayoutY(380);
        
        
        Label rotate = new Label("Rotate");
        rotate.relocate(126, 125);
        Button rotateLeft = new Button("Z");
        rotateLeft.relocate(90, 150);
        rotateLeft.setMinSize(50, 30);

        Label left2 = new Label("left");
        left2.relocate(105, 182);
        
        Button rotateRight = new Button("X");
        rotateRight.setMinSize(50, 30);
        rotateRight.relocate(150, 150);
        Label right2 = new Label("right");
        right2.relocate(162, 182);
        
        Label holdLabel = new Label("Hold");
        holdLabel.relocate(90, 295);
        Button hold = new Button("C");
        hold.relocate(79, 263);
        hold.setMinSize(50, 30);
        Label hardDropLabel = new Label("Hard Drop");
        hardDropLabel.relocate(170, 295);
        Button hardDrop = new Button("Space");
        hardDrop.setMinSize(50, 30);
        hardDrop.relocate(170, 263);
        
        Label move = new Label("Move");
        move.relocate(352, 208);
        Button left = new Button("Left");
        Button right = new Button("Right");
        Button down = new Button("Down");
        left.setMinSize(50, 30);
        right.setMinSize(50, 30);
        down.setMinSize(50, 30);
        left.relocate(292, 225);
        right.relocate(398, 225);
        down.relocate(345, 225);
        
       
        
        Pane setUp = new Pane();
        setUp.getChildren().addAll(startButton, rotate, rotateLeft, rotateRight, left2, right2, move, left, right, down, holdLabel, hold, hardDrop, hardDropLabel);
        
        Scene scene = new Scene(setUp,600,560);
        
        window.setScene(scene);
        
        Tetris tetris = new Tetris();
        
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(rotateLeft);
        buttons.add(rotateRight);
        buttons.add(hold);
        buttons.add(hardDrop);
        buttons.add(left);
        buttons.add(down);
        buttons.add(right);
        
        for (Button b : buttons) {
            b.setOnKeyPressed(event -> {
                b.setText("");
                b.setText(event.getCode().getName());
                keys.set(buttons.indexOf(b), event.getCode());
            });
        }
        
        startButton.setOnAction(e-> {
            tetris.start(window, keys);
        });
        
        window.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
