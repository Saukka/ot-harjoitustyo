
package tetris.domain;


import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Collections;
import java.util.ArrayList;



public class Board {
    
    final int squareWidth = 28;
    int widthSquares = 10;
    int heightSquares = 20;
    
    int widthPX = squareWidth*widthSquares;
    int heightPX = squareWidth*heightSquares;
    
    Pane pane = new Pane();
    Scene scene = new Scene(pane,widthPX,heightPX);
    
    Tetramino currentPiece;
    int currentX;
    int currentY;
    
    Rectangle[] rectangle;
    int[][] spots; // Pelialueen ruutujen tila pidetään tässä muuttujassa. Jos kohta xy on täynnä, niin spots[x][y]=1, muuten spots[x][y]=0
    
    int[][] newCoordinates;
    Color[] colors;
    ArrayList<Integer> placedYs;  // Lista pitää sisällään asetettujen neliöiden Y-koordinaatti-arvot
    
    public Board() {  
        newCoordinates = new int[4][2];
        rectangle = new Rectangle[4];
        spots = new int[widthSquares][heightSquares];
        colors = new Color[]{Color.WHITE, Color.LIGHTPINK, Color.LIGHTGREEN, Color.LIGHTBLUE, Color.LIGHTSALMON, Color.LIGHTCORAL, Color.VIOLET, Color.GOLD};
        placedYs = new ArrayList<>();
    }
    
    void newPiece() {
        currentPiece = new Tetramino();
        currentPiece.setRandomShape();
        
        currentX = widthSquares/2;
        currentY = -currentPiece.minY();
       
        drawPiece();
    }
    
    void drawPiece() {
        // Jokainen palikka piirretään neljällä pienemmällä neliöllä
        for (int i = 0; i < 4; i++) {
            rectangle[i] = new Rectangle((currentPiece.getCoords()[i][0] + currentX)*squareWidth,(currentPiece.getCoords()[i][1] + currentY)*squareWidth, squareWidth, squareWidth);
            rectangle[i].setFill(colors[currentPiece.current.ordinal()]);
            pane.getChildren().add(rectangle[i]);
        }
    } 
    
    void place() {
        for (int i = 0; i < 4; i++) {
            placedYs.add((int)rectangle[i].getY()/squareWidth);
            spots[currentPiece.getCoords()[i][0]+currentX][currentPiece.getCoords()[i][1]+currentY] = 1;
        }
        clearLines();
        newPiece();
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
        if (squares == 1 && !checkBelow(currentY)) {
            place();
            return;
        }
        for (int i = 0; i < 4; i++) {
            rectangle[i].setY(rectangle[i].getY()+squareWidth*squares);
        }
        currentY += squares;
    }

    void rotatePieceLeft() {
        if (currentPiece.current.ordinal() == 1) {
            return;
        }
        newCoordinates = currentPiece.rotateLeft();
        if (check(newCoordinates)) {
            currentPiece.setCoords(newCoordinates);
            for (int i = 0; i < 4; i++) {
                rectangle[i].setX((currentX+newCoordinates[i][0])*squareWidth);
                rectangle[i].setY((currentY+newCoordinates[i][1])*squareWidth);
            }
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
        place();
    }
    
    // metodi mahdollisesti lyhemmäksi myöhemmin
    void clearLines() {
        for (int i = 20; i > 0; i--) {
            int amount = Collections.frequency(placedYs,i);
            if (amount == widthSquares) { 
                // palojen poistaminen kentältä
                for (int j = 0; j < placedYs.size(); j++) {
                    if (placedYs.get(j) == i) {
                        pane.getChildren().remove(j);
                        placedYs.remove(j);
                        j--;
                    }
                }
                // rivin yläpuolella olevien rivien alaspäin tuominen
                for (int h = 0; h < placedYs.size(); h++) {
                    if (placedYs.get(h) < i) {
                        pane.getChildren().get(h).setTranslateY(pane.getChildren().get(h).getTranslateY()+squareWidth);
                        placedYs.set(h,placedYs.get(h)+1);
                    }
                }
                 for (int y = i; y > 0; y--) {
                    for (int x = 0; x < widthSquares; x++) {
                        spots[x][y] = spots[x][y-1];
                    }
                }
                i++;
            }
        }
    }
    
    boolean check(int[][] coordinates) {
        
        for (int i = 0; i < 4; i++) {
            if (coordinates[i][0]+currentX < 0 || coordinates[i][0]+currentX > widthSquares-1 || spots[coordinates[i][0]+currentX][coordinates[i][1]+currentY] == 1 ) {
                return false;
            }
        }
        return true;
    }
    
    boolean checkBelow(int y) {
        for (int i = 0; i < 4; i++) {
            if (y + currentPiece.maxY() == heightSquares-1 || spots[currentPiece.getCoords()[i][0]+currentX][currentPiece.getCoords()[i][1]+y+1] == 1) {
                return false;
            }
        }
        return true;
    }
    
}