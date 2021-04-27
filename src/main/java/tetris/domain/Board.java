
package tetris.domain;


import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.Collections;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import java.util.Random;
import tetris.domain.Tetramino.piece;


public class Board {
    
    Tetris tetris = new Tetris();
    
    final int squareWidth = 28;
    int widthSquares = 10;
    int heightSquares = 20;
    
    int widthPX = squareWidth * widthSquares;
    int heightPX = squareWidth * heightSquares;
    
    Pane pane;
    
    Tetramino currentPiece;
    int currentX;
    int currentY;
    
    Rectangle[] rectangle;
    int[][] spots; // Pelialueen ruutujen tila pidetään tässä muuttujassa. Jos kohta xy on täynnä, niin spots[x][y]=1, muuten spots[x][y]=0
    
    int[][] newCoordinates;
    Color[] colors;
    ArrayList<Integer> placedYs;
    int score;
    int level;
    int lines;
    boolean end;
    boolean check;
    
    int nextPiece;
    // Lista pitää sisällään asetettujen neliöiden Y-koordinaatti-arvot
    
    public Board() { 
        pane = new Pane();
        pane.setPrefSize(widthPX, heightPX);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
        newCoordinates = new int[4][2];
        rectangle = new Rectangle[4];
        spots = new int[widthSquares][heightSquares];
        colors = new Color[]{Color.WHITE, Color.web("0xF6FFFF"), Color.web("0x48F62D"), Color.web("0x3752FF"), Color.web("0x48F62D"), Color.web("0x3752FF"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        placedYs = new ArrayList<>();
        
        nextPiece = new Random().nextInt(7)+1;
        
        score = 0;
        level = 1;
        lines = 0;
        end = false;
    }
    
    void newPiece() {
        currentPiece = new Tetramino();
        currentPiece.setCurrentShape(piece.values()[nextPiece]);
        
        currentX = widthSquares / 2;
        currentY = -1;
       
        drawPiece(currentX, currentY, currentPiece, rectangle, pane);
        nextPiece = new Random().nextInt(7)+1;
    }
    
    void drawPiece(double x, double y, Tetramino piece, Rectangle[] rectangle, Pane pane) {
        // Jokainen palikka piirretään neljällä pienemmällä neliöllä
        for (int i = 0; i < 4; i++) {
            rectangle[i] = new Rectangle((piece.getCoords()[i][0] + x) * squareWidth, (piece.getCoords()[i][1] + y) * squareWidth, squareWidth, squareWidth);
            rectangle[i].setFill(piece.getColor(level));
            rectangle[i].setStroke(Color.web("0x29C343"));
            pane.getChildren().add(rectangle[i]);
        }
    }
    
    
    
    void place() {
        for (int i = 0; i < 4; i++) {
            if (currentPiece.getCoords()[i][1] + currentY < 0) {
                end = true;
                return;
            }
            placedYs.add((int)rectangle[i].getY() / squareWidth);
            spots[currentPiece.getCoords()[i][0] + currentX][currentPiece.getCoords()[i][1] + currentY] = 1;
        }
        clearLines();
        newPiece();
    }
    
    
    void movePieceLeft() {
        if (checkVertical(-1)) { 
            for (int i = 0; i < 4; i++) {
                rectangle[i].setX(rectangle[i].getX() - squareWidth);
            }
            currentX--;
            check = true;
        }
    }
    void movePieceRight() {
        if (checkVertical(1)) {
            for (int i = 0; i<4; i++) {
                rectangle[i].setX(rectangle[i].getX()+squareWidth);
            }
            check = true;
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
        if (checkRotate(newCoordinates)) {
            currentPiece.setCoords(newCoordinates);
            for (int i = 0; i < 4; i++) {
                rectangle[i].setX((currentX+newCoordinates[i][0])*squareWidth);
                rectangle[i].setY((currentY+newCoordinates[i][1])*squareWidth);
            }
        } 
    }
    
    void hardDrop()  {
        
        int squares = 0;
        int y = currentY;
        
        while (checkBelow(y)) {
            y++;
            squares++;
        }
        movePieceDown(squares);
        score += squares * 2;
        place();
    }
    
    void clearLines() {
        int cleared  = 0;
        for (int i = 19; i > 0; i--) {
            int amount = Collections.frequency(placedYs,i);
            if (amount == widthSquares) { 
                cleared++;
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
                        pane.getChildren().get(h).setTranslateY(pane.getChildren().get(h).getTranslateY() + squareWidth);
                        placedYs.set(h, placedYs.get(h)+1);
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
        lines += cleared;
        switch (cleared) {
            case 0:
                break;
            case 1:
                score += 100 * level;
                break;
            case 2:
                score += 300 * level;
                break;
            case 3:
                score += 500 * level;
                break;
            case 4:
                score += 800 * level;
                break;
        }
        if (lines >= level * 8) {
            System.out.println(lines);
            level++;
        } 
    }
    
    
    
    boolean checkRotate(int[][] coordinates) {
        for (int i = 0; i < 4; i++) {
            if (coordinates[i][0] + currentX >= 0 && coordinates[i][0] + currentX < widthSquares && coordinates[i][1] + currentY < 0) {
                continue;
            }
            if (coordinates[i][0] + currentX < 0 || coordinates[i][0] + currentX > widthSquares - 1 || coordinates[i][1]+currentY < 0 || coordinates[i][1]+currentY > 19 || spots[coordinates[i][0]+currentX][coordinates[i][1]+currentY] == 1 ) {
                return false;
            }
        }
        return true;
    }
    
    // move = -1 kun tarkastetaan vasemmalle liikkuminen ja 1 kun oikealle.
    boolean checkVertical(int move) {
        for (int i = 0; i < 4 ; i++) {
            if (currentPiece.getCoords()[i][0] + currentX + move >= 0 && currentPiece.getCoords()[i][0] + currentX + move < widthSquares && currentPiece.getCoords()[i][1] + currentY < 0) {
                continue;
            }
            if (currentPiece.getCoords()[i][0] + currentX + move < 0 || currentPiece.getCoords()[i][0] + currentX + move > widthSquares - 1 || spots[currentPiece.getCoords()[i][0] + currentX + move][currentPiece.getCoords()[i][1] + currentY] == 1 ) {
                return false;
            }
        }
        return true;
    }
    
    boolean checkBelow(int y) {
        for (int i = 0; i < 4; i++) {
            if (currentPiece.getCoords()[i][0] + currentX >= 0 && currentPiece.getCoords()[i][0] + currentX < widthSquares && currentPiece.getCoords()[i][1] + y < 0) {
                continue;
            }
            if (y + currentPiece.maxY() == heightSquares - 1 || spots[currentPiece.getCoords()[i][0] + currentX][currentPiece.getCoords()[i][1] + y + 1] == 1) {
                return false;
            }
        }
        return true;
    }
    
}