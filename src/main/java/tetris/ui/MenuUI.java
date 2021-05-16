
package tetris.ui;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;


public class MenuUI extends Application {
    
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
        startButton.setFont(Font.font("Silom", 20));
        startButton.setMinSize(80, 20);
        startButton.setLayoutX(300);
        startButton.setLayoutY(380);
        
        Label rotate = new Label("Rotate");
        rotate.relocate(186, 125);
        Button rotateLeft = new Button("Z");
        rotateLeft.relocate(150, 150);
        rotateLeft.setMinSize(50, 30);

        Label left2 = new Label("left");
        left2.relocate(165, 182);
        
        Button rotateRight = new Button("X");
        rotateRight.setMinSize(50, 30);
        rotateRight.relocate(210, 150);
        Label right2 = new Label("right");
        right2.relocate(222, 182);
        
        Label holdLabel = new Label("Hold");
        holdLabel.relocate(150, 295);
        Button hold = new Button("C");
        hold.relocate(139, 263);
        hold.setMinSize(50, 30);
        Label hardDropLabel = new Label("Hard Drop");
        hardDropLabel.relocate(227, 295);
        Button hardDrop = new Button("Space");
        hardDrop.setMinSize(50, 30);
        hardDrop.relocate(230, 263);
        
        Label move = new Label("Move");
        move.relocate(492, 193);
        Button left = new Button("Left");
        Button right = new Button("Right");
        Button down = new Button("Down");
        left.setMinSize(50, 30);
        right.setMinSize(50, 30);
        down.setMinSize(50, 30);
        left.relocate(432, 210);
        right.relocate(538, 210);
        down.relocate(485, 210);
       
        Pane setUp = new Pane();
        setUp.getChildren().addAll(startButton, rotate, rotateLeft, rotateRight, left2, right2, move, left, right, down, holdLabel, hold, hardDrop, hardDropLabel);
        
        Scene scene = new Scene(setUp,722, 602);
        
        window.setScene(scene);
        
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(rotateLeft);
        buttons.add(rotateRight);
        buttons.add(hold);
        buttons.add(hardDrop);
        buttons.add(left);
        buttons.add(down);
        buttons.add(right);
        
        buttons.forEach((b) -> {
            b.setOnKeyPressed(event -> {
                b.setText("");
                b.setText(event.getCode().getName());
                keys.set(buttons.indexOf(b), event.getCode());
            });
        });
        
        GameUI gameUI = new GameUI();
        
        startButton.setOnAction(e-> {
            gameUI.start(window, keys);
        });
        
        window.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
