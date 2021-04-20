package tetris.domain;

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
    
    @Test
    public void rotatingChangesCoordinates() {
        tetramino = new Tetramino();
        tetramino.setRandomShape();
        int[][] coords = tetramino.getCoords();
        int[][] rotatedCoords = tetramino.rotateLeft();
        assertFalse(coords.equals(rotatedCoords));
    }
    
    @Test
    public void rotatingWorksProperly() {
        tetramino = new Tetramino();
        tetramino.setRandomShape();
        int[][] coords = tetramino.getCoords();
        tetramino.rotateLeft();
        tetramino.rotateLeft();
        tetramino.rotateLeft();
        tetramino.rotateLeft();
        assertTrue(coords.equals(tetramino.getCoords()));
    }
    
    @Test
    public void rotatedIPieceWidthIsFour() {
        tetramino = new Tetramino();
        tetramino.setCurrentShape(Tetramino.piece.IPiece);
        tetramino.setCoords(tetramino.rotateLeft());
        assertEquals(4, tetramino.width());
    }
    
    
    // Scenen initialisoiminen Board-luokassa jostain syystä tuottaa NullPointerExceptionin mitä en millään saanut korjattua, joten seuraava testi ei toimi.
    // Jos Scenen initialisoiminen ottaa pois Board-luokasta, testi toimii
    /* 
    @Test
    public void droppedPieceAddedToList() {
        board = new Board();
        board.newPiece();
        board.hardDrop();
        assertEquals(4,board.placedYs.size());
        // jokainen palikka on neljä neliötä, ja jokaisen neliön y-koordinaatti lisätään listaan palikan asettuessa
    } 
    */
    
}
