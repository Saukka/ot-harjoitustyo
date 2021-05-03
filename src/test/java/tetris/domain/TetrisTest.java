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
    
    Tetromino tetramino;
    Board board;
    Tetris tetris;
    
    @Test
    public void rotatingChangesCoordinates() {
        tetramino = new Tetromino();
        tetramino.setRandomShape();
        int[][] coords = tetramino.getCoords();
        int[][] rotatedCoords = tetramino.rotateLeft();
        assertFalse(coords.equals(rotatedCoords));
    }
    
    @Test
    public void rotatingWorksProperly() {
        tetramino = new Tetromino();
        tetramino.setRandomShape();
        int[][] coords = tetramino.getCoords();
        tetramino.rotateLeft();
        tetramino.rotateLeft();
        tetramino.rotateLeft();
        tetramino.rotateLeft();
        assertTrue(coords.equals(tetramino.getCoords()));
    }
    
    @Test
    public void rotatedIPieceHeightIsFour() {
        tetramino = new Tetromino();
        tetramino.setCurrentShape(Tetromino.piece.IPiece);
        tetramino.setCoords(tetramino.rotateLeft());
        assertEquals(4, tetramino.height());
    }
    
    
    @Test
    public void droppedPieceAddedToList() {
        board = new Board();
        board.newPiece(false, board.nextPiece);
        board.hardDrop();
        assertEquals(4, board.placed.size());
        // jokainen palikka on neljä neliötä, ja placed listaan tallennetaan neliöt
    } 
    
    @Test
    public void scoreIncreases() {
        board = new Board();
        board.newPiece(false, board.nextPiece);
        board.hardDrop();
        assertTrue(board.score > 30);
    }
    
    @Test
    public void holdPieceWorks() {
        board = new Board();
        board.newPiece(false, board.nextPiece);
        board.swapHold();
        int holdPiece = board.holdPiece;
        board.hardDrop();
        board.swapHold();
        assertEquals(holdPiece, board.currentPiece.current.ordinal());
        
    }
    
    
}
