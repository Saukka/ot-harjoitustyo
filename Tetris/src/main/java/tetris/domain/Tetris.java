
package tetris.domain;

import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class Tetris {
    
    public void start(Stage window) {
        
        Board board = new Board();
        board.pane.setPrefSize(board.widthPX, board.heightPX);
        
        window.setScene(board.scene);
        window.show();
        
        board.newPiece();
        
    new AnimationTimer() {
        
        
        long clock = 0; 
        @Override
        public void handle(long time) {
            
            board.scene.setOnKeyPressed(event -> {
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
            clock++;
            if (clock > 30) {
                board.movePieceDown(1);
                clock = 0;
            }
            
            
        }        
    }.start();
        
    };
    
}

