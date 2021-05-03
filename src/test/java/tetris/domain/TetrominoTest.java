package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



public class TetrominoTest {
    
    public TetrominoTest() {
    }
    
    Tetromino tetromino;
    
    @Test
    public void rotatingChangesCoordinates() {
        tetromino = new Tetromino();
        tetromino.setRandomShape();
        int[][] coords = tetromino.getCoords();
        int[][] rotatedCoords = tetromino.rotateLeft();
        assertFalse(coords.equals(rotatedCoords));
    }
    
    @Test
    public void rotatingWorksProperly() {
        tetromino = new Tetromino();
        tetromino.setRandomShape();
        int[][] coords = tetromino.getCoords();
        tetromino.rotateLeft();
        tetromino.rotateLeft();
        tetromino.rotateLeft();
        tetromino.rotateLeft();
        assertTrue(coords.equals(tetromino.getCoords()));
    }
    
    @Test
    public void rotatedIPieceHeightIsFour() {
        tetromino = new Tetromino();
        tetromino.setCurrentShape(Tetromino.piece.IPiece);
        tetromino.setCoords(tetromino.rotateLeft());
        assertEquals(4, tetromino.height());
    }
    
    @Test 
    public void pieceBagWorksProperly() {
        tetromino = new Tetromino();
        tetromino.getNextPiece();
        tetromino.getNextPiece();
        tetromino.getNextPiece();
        tetromino.getNextPiece();
        tetromino.getNextPiece();
        tetromino.getNextPiece();
        // yksi jäljellä
        assertEquals(1, tetromino.pieceBag.size());
        tetromino.getNextPiece();
        // pussi täytetään uudelleen kun viimeinen otetaan pois
        assertEquals(7, tetromino.pieceBag.size());
    }
}
