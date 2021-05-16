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
public class BoardAndPieceTest {
    
    public BoardAndPieceTest() {
    }
    
    Board board;
    
    @Test
    public void droppedPieceAddedToList() {
        board = new Board(1, false);
        board.newPiece(false, board.nextPiece);
        board.current.hardDrop();
        assertEquals(4, board.placed.size());
        // jokainen palikka on neljä neliötä, ja placed listaan tallennetaan neliöt
    } 
    
    @Test
    public void scoreIncreases() {
        board = new Board(1, false);
        board.newPiece(false, board.nextPiece);
        board.current.hardDrop();
        assertTrue(board.score > 30);
    }
    
    @Test
    public void holdPieceWorks() {
        board = new Board(1, false);
        board.newPiece(false, board.nextPiece);
        board.swapHold();
        int holdPiece = board.holdPiece;
        board.current.hardDrop();
        board.swapHold();
        assertEquals(holdPiece, board.current.piece.shape.ordinal());
    }
    
    @Test
    public void movingPieceWorks() {
        board = new Board(1, false);
        board.newPiece(false, board.nextPiece);
        CurrentPiece piece = board.current;
        int xCoordinate = piece.x;
        piece.moveVertical(1);
        assertFalse(xCoordinate == piece.x);
        piece.moveVertical(-1);
        assertEquals(xCoordinate, piece.x);
        piece.moveVertical(-1);
        assertEquals(xCoordinate - 1, piece.x);
        int yCoordinate = piece.y;
        board.current.moveDown(1, true);
        piece.moveDown(1, true);
        assertEquals(yCoordinate + 2, piece.y);
    }
    
    @Test
    public void rotatingWorks() {
        board = new Board(1, false);
        board.newPiece(false, 4);
        CurrentPiece piece = board.current;
        int[][] coordinates = piece.piece.getCoords();
        piece.rotate(1);
        assertFalse(coordinates == piece.piece.getCoords());
    }
    
    @Test
    public void drawMethodDraws() {
        board = new Board(3, false);
        board.newPiece(false, 5);
        assertEquals(4 + 4 * 4, board.pane.getChildren().size());
    }
}
