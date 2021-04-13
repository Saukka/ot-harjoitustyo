
package tetris.domain;

import javafx.stage.Stage;

public class Tetris {
    
    
    public void start(Stage window) {
        
        Board board = new Board();
        board.grid.setPrefSize(board.widthPX, board.heightPX);
        
        window.setScene(board.getScene());
        
        
        board.newPiece();
        
        
    };
    
}

