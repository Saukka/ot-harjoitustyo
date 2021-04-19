
package tetris.domain;


import java.awt.*;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;



public class Board {
    
    final int widthPX = 280;
    final int heightPX = 560;
    
    int widthSquares = 10;
    int heightSquares = 20;
    
    int squareWidth = widthPX/widthSquares;
    
    Pane pane = new Pane();
    Scene scene = new Scene(pane,widthPX,heightPX);
    
    Tetramino currentPiece;
    int currentX;
    int currentY;
    
    Rectangle[] rectangle;
    int[][] spots;
    int[][] coords;
    
    Map<Rectangle, Integer> placedPieces;
    int[][] newCoordinates;
    
    public Board() {
        newCoordinates = new int[4][2];
        rectangle = new Rectangle[4];
        spots = new int[widthSquares][heightSquares];
        placedPieces = new HashMap<>();
    }
    
    void newPiece() {
        currentPiece = new Tetramino();
        currentPiece.setRandomShape();
        
        currentX = widthSquares/2;
        currentY = 3;
        
        /* coords = new int[4][2];
        
        for (int i = 0; i < 4; i++) {
            coords[i][0] = currentPiece.getCoords()[i][0] + currentX;
            coords[i][1] = currentPiece.getCoords()[i][1] + currentY;
        } */
        
        drawPiece();
    }
    
    void drawPiece() {
        for (int i = 0; i < 4; i++) {
            rectangle[i] = new Rectangle((currentPiece.getCoords()[i][0] + currentX)*squareWidth,(currentPiece.getCoords()[i][1] + currentY)*squareWidth, squareWidth, squareWidth);
            pane.getChildren().add(rectangle[i]);
        }
    } 
    
    void movePieceLeft() {
        if (currentX+currentPiece.minX() > 0) { 
            for (int i = 0; i<4; i++) {
                rectangle[i].setX(rectangle[i].getX()-squareWidth);
            }
            currentX--;
        }
    }
    void movePieceRight() {
        if (currentX+currentPiece.maxX() < widthSquares-1) {
            for (int i = 0; i<4; i++) {
                rectangle[i].setX(rectangle[i].getX()+squareWidth);
            }
            currentX++;
        }
    }
    
    
    void movePieceDown(int squares) {
        
        for (int i = 0; i < 4; i++) {
            rectangle[i].setY(rectangle[i].getY()+squareWidth*squares);
        }
        currentY += squares;
    }
    
    boolean checkBelow(int y) {
        for (int i = 0; i < 4; i++) {
            if (y == 19 || spots[currentPiece.getCoords()[i][0]+currentX][currentPiece.getCoords()[i][1]+y-1] == 1) {
                return false;
            }
        }
        return true;
    }
    
    boolean check(int[][] coordinates) {
        
        for (int i = 0; i < 4; i++) {
            if (coordinates[i][0]+currentX < 0 || coordinates[i][0]+currentX > widthSquares-1 || spots[coordinates[i][0]+currentX][coordinates[i][1]+currentY] == 1 ) {
                return false;
            }
        }
        return true;
    }
    
    /* boolean checkOne(int x, int y) {
        return !(x < 0 || x > widthSquares || spots[x][y] == 1);
    } */
    
    
    void rotatePieceLeft() {
        newCoordinates = currentPiece.rotateLeft();
        if (check(newCoordinates)) {
            currentPiece.setCoords(newCoordinates);
            for (int i = 0; i < 4; i++) {
                rectangle[i].setX((currentX+newCoordinates[i][0])*squareWidth);
                rectangle[i].setY((currentY+newCoordinates[i][1])*squareWidth);
            }
        } 
    }
    
    void place() {
        for (int i = 0; i < 4; i++) {
            placedPieces.put(rectangle[i],((int)rectangle[i].getY()/squareWidth));
            spots[currentPiece.getCoords()[i][0]][currentPiece.getCoords()[i][1]] = 1;
        }
    }
    
    
    void hardDrop() {
        int squares = 0;
        int y = currentY;
        
        while (checkBelow(y)) {
            y++;
            squares++;
        }
        movePieceDown(squares);
        
    }
    
}