/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.ui;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tetris.domain.Tetris;


public class GameUI {
    
    
    public void start(Stage window, ArrayList<KeyCode> keys) {

        Rectangle nextBox = new Rectangle(0, 56, 168, 112);
        nextBox.setArcHeight(22);
        nextBox.setArcWidth(22);
        nextBox.setFill(Color.web("0x3D3D3D"));
        
        Rectangle holdBox = new Rectangle(28, 56, 168, 112);
        holdBox.setArcHeight(22);
        holdBox.setArcWidth(22);

        Pane right = new Pane();
        right.setPrefSize(210, 560);
        right.getChildren().add(nextBox);

        Pane left = new Pane();
        left.setPrefSize(210, 560);
        left.getChildren().add(holdBox);
        
        Text score = new Text("SCORE: ");
        Text lines = new Text("LINES: ");
        Text level = new Text("LEVEL: ");
        
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
        
        BorderPane view = new BorderPane();
        view.setBackground(new Background(new BackgroundFill(Color.web("0x8A2EA6"), CornerRadii.EMPTY, Insets.EMPTY)));
        view.setLeft(left);
        view.setRight(right);
        view.setPrefSize(722, 602);
        
        Scene scene = new Scene(view);
        window.setScene(scene);
        window.show();
        
        Tetris tetris = new Tetris(window, scene, view, left, right);
        tetris.start(keys, score, lines, level);
    }
    
}
