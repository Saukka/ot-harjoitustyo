package tetris.domain;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tetris.domain.Tetromino.shape;



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
        tetromino.setCurrentShape(shape.IPIECE);
        tetromino.setCoords(tetromino.rotateLeft());
        assertEquals(4, tetromino.height());
    }
    
    @Test 
    public void returnsCorrectColorAndStroke() {
        tetromino = new Tetromino();
        assertEquals(Color.web("0xA2A2FF"), tetromino.getColor(5, 2));
        assertEquals(Color.web("0XE50101"), tetromino.getColor(11, 0));
    }
}
