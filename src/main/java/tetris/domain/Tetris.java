
package tetris.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
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


public class Tetris {
    
    public void start(Stage window) {
        
        
        Rectangle[] rectangle = new Rectangle[4];
        Rectangle nextBox = new Rectangle(0,56,168,112);
        nextBox.setArcHeight(22);
        nextBox.setArcWidth(22);
        nextBox.setFill(Color.web("0x3D3D3D"));
        
        Pane right = new Pane();
        right.setPrefSize(210, 560);
        right.getChildren().add(nextBox);
        
        Pane left = new Pane();
        left.setPrefSize(210, 560);
        
        Board board = new Board();
        Text score = new Text ("SCORE: " + board.score);
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
        Tetramino piece = new Tetramino();
        piece.setCurrentShape(Tetramino.piece.values()[board.nextPiece]);
        board.drawPiece(3 - piece.minX() - ((double) piece.width() / 2) , 4, piece, rectangle, right);
        
        
        board.pane.setPrefSize(board.widthPX, board.heightPX);
        BorderPane view = new BorderPane();
        view.setBackground(new Background(new BackgroundFill(Color.web("0x8A2EA6"),CornerRadii.EMPTY, Insets.EMPTY)));
        view.setCenter(board.pane);
        view.setLeft(left);
        view.setRight(right);
        view.setPrefSize(700, 560);
        
        Scene scene = new Scene(view);
        window.setScene(scene);
        window.show();
        
        board.newPiece();
        
        
    new AnimationTimer() {
        
        
        float clock = 0; 
        @Override
        public void handle(long time) {
            
            if (board.nextPiece != board.currentPiece.current.ordinal()) {
                right.getChildren().remove(1,5);
                piece.setCurrentShape(Tetramino.piece.values()[board.nextPiece]);
                board.drawPiece(3 - piece.minX() - ((double) piece.width() / 2) , 4, piece, rectangle, right);
            }
            
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.LEFT) {
                    board.movePieceLeft();
                } 
                if (event.getCode() == KeyCode.RIGHT) {
                    board.movePieceRight();
                }
                if (event.getCode() == KeyCode.Z) {
                    board.rotatePieceLeft();
                }
                if (event.getCode() == KeyCode.SPACE) {
                    board.hardDrop();
                }
            });
            
            if (board.end) {
                stop();
            }
            
            clock++;
            if (clock / 60 > 1.1 - Math.log10(board.level)) {
                board.movePieceDown(1);
                clock = 0;
            }
            
            score.setText("SCORE: " + board.score);
            lines.setText("LINES: " + board.lines);
            level.setText("LEVEL: " + board.level);
            
        }        
    }.start();
        
    };
    
    
    
}

