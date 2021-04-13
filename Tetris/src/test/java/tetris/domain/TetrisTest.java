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
    
    @Before
    public void setUp() {
        tetramino = new Tetramino();
    }
    
    @Test
    public void tetraminoIsOnBoard() {
        tetramino.setRandomShape();
        assertTrue(tetramino.maxY()>0);
    }
    
    
}
