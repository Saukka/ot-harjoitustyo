
package tetris.domain;

import java.util.Random;
import javafx.scene.paint.Color;


public class Tetramino {

    
    Color[] colors;
    
    // Palikoiden nimet	
    enum piece {
        Empty, Square, SPiece, MirrorSPiece, LPiece, MirrorLPiece, TPiece, IPiece
    }	   
    
    // Jokainen palikka muodostuu neljästä neliöstä
    int[][][] pieces = new int[][][] {
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, // Empty
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, // Square
        {{-1, 0}, {0, 0}, {0, 1}, {1, 1}}, // SPiece
        {{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, // MirrorSPiece
        {{1, -1}, {1, 0}, {0, 0}, {-1, 0}}, // LPiece
        {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}}, // MirrorLPiece		
        {{-1, 0}, {0, 0}, {1, 0}, {0, -1}}, // TPiece		
        {{-1, 0}, {0, 0}, {1, 0}, {2, 0}}// IPiece
    };
    piece current;
    private int[][] currentCoords;
    
    public Tetramino() {
        currentCoords = new int[4][2];
        setCurrentShape(piece.Empty);
    }
    
    Color getColor(int level) {
        //if (level == 1) {
        colors = new Color[]{Color.web("0x48F62D"), Color.web("0xF6FFFF"), Color.web("0x48F62D"), Color.web("0x3752FF"), Color.web("0x48F62D"), Color.web("0x3752FF"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        //}
        return colors[current.ordinal()];
    }
    
    void setCurrentShape(piece piece) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(pieces[piece.ordinal()], 0, currentCoords, 0, 4);
        }
        current = piece;
    }
    
    void setRandomShape() {
       piece[] pieceValues = piece.values();
       setCurrentShape(pieceValues[new Random().nextInt(7)+1]);
    }
    
    int width() {
        return maxX() - minX() + 1;
    }
    
    int minY() {
        int minY = currentCoords[0][1];
        
        for (int i = 1; i < 4; i++) {
            if (currentCoords[i][1] < minY) {
                minY = currentCoords[i][1];
            }
        }
        return minY;
    }
    
    int maxY() {
        int maxY = currentCoords[0][1];
        
        for (int i = 1; i < 4; i++) {
            if (currentCoords[i][1] > maxY) {
                maxY = currentCoords[i][1];
            }
        }
        return maxY;
    }
    
    int maxX() {
        int maxX = currentCoords[0][0];
        
        for (int i = 1; i < 4; i++) {
            if (currentCoords[i][0] > maxX) {
                maxX = currentCoords[i][0];
            }
        }
        return maxX;
        
    }
    
    int minX() {
        int minX = currentCoords[0][0];
        
        for (int i = 1; i < 4; i++) {
            if (currentCoords[i][0] < minX) {
                minX = currentCoords[i][0];
            }
        }
        return minX;
        
    }
    int[][] getCoords() {
        return currentCoords;
    }
    void setCoords(int [][] c) {
        currentCoords = c;
    }
    
    int[][] rotateLeft() {
        int[][] newCoords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            newCoords[i][0] = currentCoords[i][1];
            newCoords[i][1] = - currentCoords[i][0];
        }
        return newCoords;
    }
    
    void move() {
        
    }
    
}