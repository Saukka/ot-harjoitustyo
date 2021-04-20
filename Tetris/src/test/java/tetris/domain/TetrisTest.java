package tetris.domain;

import javafx.scene.Scene;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



public class TetrisTest {
    
    public TetrisTest() {
    }
    
   Tetramino tetramino;
   Board board;
    
    @Before
    public void setUp() {
        tetramino = new Tetramino();
        board = new Board();
    }
    
    @Test
    public void droppedPieceAddedToList() {
        board.pane.setPrefSize(board.widthPX, board.heightPX);
        board.newPiece();
        board.hardDrop();
        assertEquals(board.placedYs.size(), 4);
        // jokainen palikka on neljä neliötä, ja jokaisen neliön y-koordinaatti lisätään listaan palikan asettuessa
    }
    
    
}
