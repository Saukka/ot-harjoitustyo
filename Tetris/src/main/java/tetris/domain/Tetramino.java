
package tetris.domain;

import java.util.Random;
import java.util.ArrayList;


public class Tetramino {
    
    // Palikoiden nimet	
    enum piece {
        Empty, Square, SPiece, MirrorSPiece, LPiece, MirrorLPiece, TPiece, IPiece
    }	   
    
    int[][][] pieces = new int[][][] {
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, // Empty
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, // Square
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, // SPiece	
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, // MirrorSPiece
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}, // LPiece		
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}, // MirrorLPiece		
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, // TPiece		
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}}// IPiece
    }; 
    
    private piece current;  
    private int[][] currentCoords;
    
    public Tetramino() {
        currentCoords = new int[4][2];
        setCurrentShape(piece.Empty);
    }
    
    public void setCurrentShape(piece piece) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(pieces[piece.ordinal()], 0, currentCoords, 0, 4);
        }
        current = piece;
    }
    
    public void setRandomShape() {
       piece[] pieceValues = piece.values();
       setCurrentShape(pieceValues[new Random().nextInt(7)+1]);
    }
    
    public int maxY() {
        int maxY = currentCoords[0][1];
        
        for (int i = 1; i < 4; i++) {
            if (currentCoords[i][1] > maxY) {
                maxY = currentCoords[i][1];
            }
        }
        return maxY;
        
    }
    
    public int minX() {
        int minX = currentCoords[0][0];
        
        for (int i = 1; i < 4; i++) {
            if (currentCoords[i][0] < minX) {
                minX = currentCoords[i][0];
            }
        }
        return minX;
        
    }
    public int[][] getCoords() {
        return currentCoords;
    }

}