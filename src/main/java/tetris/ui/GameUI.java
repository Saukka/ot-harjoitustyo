/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.ui;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import tetris.dao.HighScores;
import tetris.domain.Tetris;


public class GameUI {
    
    
    public void start(Stage window, ArrayList<KeyCode> keys, int startLevel, boolean thin, HighScores scores) {

        Rectangle nextBox = new Rectangle(5, 64, 172, 112);
        nextBox.setArcHeight(22);
        nextBox.setArcWidth(22);
        nextBox.setFill(Color.web("0x3D3D3D"));
        
        Rectangle holdBox = new Rectangle(30, 64, 172, 112);
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
        level.setFont(Font.font("Silom", 20));
        score.setTranslateX(28);
        score.setTranslateY(400);
        score.setFont(Font.font("Silom", 20));
        lines.setTranslateX(28);
        lines.setTranslateY(440);
        lines.setFont(Font.font("Silom", 20));
        left.getChildren().add(score);
        left.getChildren().add(lines);
        left.getChildren().add(level);
        
        Button pauseButton = new Button("Pause");
        pauseButton.setMinSize(50, 50);
        pauseButton.relocate(20, 400);
        pauseButton.setFocusTraversable(false);
        pauseButton.setBackground(new Background(new BackgroundFill(Color.web("0xd4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        pauseButton.setOnMouseEntered(eh -> {
            pauseButton.setBackground(new Background(new BackgroundFill(Color.web("0x919191"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        pauseButton.setOnMouseExited(eh -> {
            pauseButton.setBackground(new Background(new BackgroundFill(Color.web("0xd4d4d4"), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        right.getChildren().add(pauseButton);
        
        Pane pauseScreen = new Pane();
        Text pauseText = new Text("Game is paused");
        pauseText.setFont(Font.font("Rockwell", 20));
        pauseText.relocate(67, 215);
        pauseText.setFill(Color.WHITE);
        Button continueButton = new Button("Continue");
        continueButton.setMinSize(100, 40);
        continueButton.relocate(60, 250);
        continueButton.setFont(Font.font("Silom", 30));
        continueButton.setFocusTraversable(false);
        pauseScreen.getChildren().addAll(continueButton, pauseText);
        pauseScreen.setBackground(new Background(new BackgroundFill(Color.web("0x16067d"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        BorderPane view = new BorderPane();
        view.setBackground(new Background(new BackgroundFill(Color.web("0x8A2EA6"), CornerRadii.EMPTY, Insets.EMPTY)));
        view.setLeft(left);
        view.setRight(right);
        view.setPrefSize(722, 602);
        
        Scene scene = new Scene(view);
        window.setScene(scene);
        window.show();
        
        Tetris tetris = new Tetris(window, scene, view, left, right, pauseScreen, pauseButton, continueButton, scores);
        tetris.start(keys, startLevel, score, lines, level, thin);
    }
    
}
