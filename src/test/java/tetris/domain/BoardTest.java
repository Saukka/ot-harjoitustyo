/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saulisorvari
 */
public class BoardTest {
    
    public BoardTest() {
    }
    
    Board board;
    
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
