package tetris.domain;

import java.util.Arrays;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tetris.domain.Tetromino.SHAPE;



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
        assertFalse(Arrays.equals(coords, rotatedCoords));
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
        tetromino.setCurrentShape(SHAPE.IPIECE);
        tetromino.setCoords(tetromino.rotateLeft());
        assertEquals(4, tetromino.height());
    }
    
    @Test 
    public void returnsCorrectColorAndStroke() {
        tetromino = new Tetromino();
        assertEquals(Color.web("0xA2A2FF"), tetromino.getColor(5, 2));
        assertEquals(Color.web("0xB61B1B"), tetromino.getColor(11, 0));
    }
    
    @Test
    public void settingShapeWorks() {
        tetromino = new Tetromino();
        tetromino.setCurrentShape(SHAPE.SQUARE);
        assertEquals(2, tetromino.height());
        assertEquals(2, tetromino.width());
    }
}
