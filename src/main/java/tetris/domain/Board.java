
package tetris.domain;


import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import tetris.domain.Tetromino.piece;


public class Board {
    
    Tetris tetris = new Tetris();
    
    final int squareWidth = 28;
    int widthSquares = 10;
    int heightSquares = 20;
    
    int widthPX = squareWidth * widthSquares;
    int heightPX = squareWidth * heightSquares;
    
    Pane pane;
    
    Tetromino currentPiece;
    int currentX;
    int currentY;
    Rectangle[] pieceSquare;
    
    Tetromino ghostPiece;
    Rectangle[] ghostSquare;
    
    int[][] spots; // Pelialueen ruutujen tila pidetään tässä muuttujassa. Jos kohta xy on täynnä, niin spots[x][y]=1, muuten spots[x][y]=0
    
    int[][] newCoordinates;
    
    // Lista pitää sisällään asetettujen neliöiden tiedot
    ArrayList<PlacedRectangle> placed;
    
    int score;
    int level;
    int lines;
    boolean end;
    boolean check;
    
    int nextPiece;
    int holdPiece;
    boolean canSwapHold;
    
    public Board() { 
        pane = new Pane();
        pane.setPrefSize(widthPX, heightPX);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        newCoordinates = new int[4][2];
        pieceSquare = new Rectangle[4];
        spots = new int[widthSquares + 8][heightSquares + 6];
        placed = new ArrayList<>();
        
        for (int x = 0; x < 18; x++) {
            for (int y = 25; y > -1; y--) {
                if (x < 4 || x > 13 || y > 23) {
                    spots[x][y] = 1;
                }
            }
        }
        
        currentPiece = new Tetromino();
        nextPiece = currentPiece.getNextPiece();
        
        ghostPiece = new Tetromino();
        ghostSquare = new Rectangle[4];
        
        for (int i = 0; i < 4; i++) {
            ghostSquare[i] = new Rectangle(0, -200, squareWidth, squareWidth);
            pane.getChildren().add(ghostSquare[i]);
        }
        
        holdPiece = 0;
        canSwapHold = true;
        
        score = 0;
        level = 1;
        lines = 0;
        end = false;
    }
    
    void newPiece(boolean hold, int pieceValue) {
        
        currentPiece.setCurrentShape(piece.values()[pieceValue]);
        
        currentX = widthSquares / 2 + 4;
        currentY = -1 + 4;
        drawPiece(currentX - 4, currentY - 4, currentPiece, pieceSquare, pane);
        updateGhost();
        if (hold) {
            return;
        }
        nextPiece = currentPiece.getNextPiece();
    }
    
    void drawPiece(double x, double y, Tetromino piece, Rectangle[] rectangle, Pane pane) {
        // Jokainen palikka piirretään neljällä pienemmällä neliöllä
        for (int i = 0; i < 4; i++) {
            rectangle[i] = new Rectangle((piece.getCoords()[i][0] + x) * squareWidth, (piece.getCoords()[i][1] + y) * squareWidth, squareWidth, squareWidth);
            rectangle[i].setFill(piece.getColor(level, piece.current.ordinal()));
            rectangle[i].setStroke(piece.getColor(level, 0));
            pane.getChildren().add(rectangle[i]);
        }
    }
    
    void place() {
        boolean endGame = true;
        for (int i = 0; i < 4; i++) {
            placed.add(new PlacedRectangle(currentPiece.getCoords()[i][0] + currentX, (int) pieceSquare[i].getY() / squareWidth + 4, currentPiece.current.ordinal(), pieceSquare[i]));
            spots[currentPiece.getCoords()[i][0] + currentX][currentPiece.getCoords()[i][1] + currentY] = 1;
            if (currentPiece.getCoords()[i][1] + currentY > 4) {
                endGame = false;
            }
            
        }
        end = endGame;
        canSwapHold = true;
        clearLines();
        newPiece(false, nextPiece);
    }
    
    void reColorPlaced() {
        for (int i = 0; i < placed.size(); i++) {
            placed.get(i).reColor(level);
        }
    }
    
    void movePieceLeft() {
        if (checkVertical(-1)) { 
            for (int i = 0; i < 4; i++) {
                pieceSquare[i].setX(pieceSquare[i].getX() - squareWidth);
            }
            currentX--;
            updateGhost();
        }
    }
    void movePieceRight() {
        if (checkVertical(1)) {
            for (int i = 0; i < 4; i++) {
                pieceSquare[i].setX(pieceSquare[i].getX() + squareWidth);
            }
            currentX++;
            updateGhost();
        }
    }
    
    void movePieceDown(int squares, boolean softDrop) {
        if (squares == 1 && !checkBelow(currentY)) {
            place();
            return;
        }
        for (int i = 0; i < 4; i++) {
            pieceSquare[i].setY(pieceSquare[i].getY() + squareWidth * squares);
        }
        currentY += squares;
        if (softDrop) {
            score++;
        }
    }

    // r = 1 jos käännetään oikealle
    void rotatePiece(int r) {
        if (currentPiece.current.ordinal() == 1) {
            return;
        }
        if (r == 1) {
            newCoordinates = currentPiece.rotateRight();
        } else {
            newCoordinates = currentPiece.rotateLeft();
        }
        
        int rotateHeight = checkRotate(newCoordinates);
        if (rotateHeight != -1) {
            currentPiece.setCoords(newCoordinates);
            currentY -= rotateHeight;
            for (int i = 0; i < 4; i++) {
                pieceSquare[i].setX((currentX - 4 + newCoordinates[i][0]) * squareWidth);
                pieceSquare[i].setY((currentY - 4 + newCoordinates[i][1]) * squareWidth);
            }
            updateGhost();
        } 
    }
    
    void updateGhost() {
        int y = currentY;
        while (checkBelow(y)) {
            y++;
        }
        for (int i = 0; i < 4; i++) {
            ghostSquare[i].setFill(currentPiece.getColor(level, currentPiece.current.ordinal()));
            ghostSquare[i].setOpacity(0.35);
            ghostSquare[i].setX((currentX - 4 + currentPiece.getCoords()[i][0]) * squareWidth);
            ghostSquare[i].setY((y - 4 + currentPiece.getCoords()[i][1]) * squareWidth);
        }
    }
    
    void hardDrop()  {
        
        int squares = 0;
        int y = currentY;
        
        while (checkBelow(y)) {
            y++;
            squares++;
        }
        movePieceDown(squares, false);
        score += squares * 2;
        place();
    }
    
    void swapHold() {
        if (holdPiece == 0) {
            holdPiece = currentPiece.current.ordinal();
            newPiece(false, nextPiece);
            pane.getChildren().remove(placed.size() + 4, placed.size() + 8);
            canSwapHold = false;
        } else {
            pane.getChildren().remove(placed.size() + 4, placed.size() + 8);
            int pieceBefore = currentPiece.current.ordinal();
            newPiece(true, holdPiece);
            holdPiece = pieceBefore;
            canSwapHold = false;
        }
        
    }
    
    void clearLines() {
        int cleared  = 0;
        for (int i = 23; i > 3; i--) {
            int amount = 0;
            for (PlacedRectangle square : placed) {
                if (square.y == i) {
                    amount++;
                } 
            }
            if (amount == widthSquares) { 
                cleared++;
                // palojen poistaminen kentältä
                for (int j = 0; j < placed.size(); j++) {
                    if (placed.get(j).getY() == i) {
                        pane.getChildren().remove(j + 4);
                        placed.remove(j);
                        j--;
                    }
                }
                // rivin yläpuolella olevien rivien alaspäin tuominen
                for (int h = 0; h < placed.size(); h++) {
                    if (placed.get(h).getY() < i) {
                        pane.getChildren().get(h + 4).setTranslateY(pane.getChildren().get(h + 4).getTranslateY() + squareWidth);
                        placed.get(h).setY(placed.get(h).getY() + 1);
                    }
                }
                for (int y = i; y > 3; y--) {
                    for (int x = 4; x < widthSquares + 4; x++) {
                        spots[x][y] = spots[x][y - 1];
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
            level++;
            reColorPlaced();
        } 
    }
    
    int checkRotate(int[][] coordinates) {
        int answer = 0;
        for (int i = 0; i < 4; i++) {
            if (spots[coordinates[i][0] + currentX][coordinates[i][1] + currentY] == 1) {
                answer = -1;
            }
        }
        if (answer == 0) {
            return answer;
        }
        // jos palikkaa ei saanut käännettyä, koitetaan se kääntää yhdelle korkeammalle riville
        answer = 1;
        for (int i = 0; i < 4; i++) {
            if (spots[coordinates[i][0] + currentX][coordinates[i][1] + currentY - 1] == 1) {
                answer = -1;
            }
        }
        return answer;
    }
    
    // move = -1 kun tarkastetaan vasemmalle liikkuminen ja 1 kun oikealle.
    boolean checkVertical(int move) {
        for (int i = 0; i < 4; i++) {
            if (spots[currentPiece.getCoords()[i][0] + currentX + move][currentPiece.getCoords()[i][1] + currentY] == 1) {
                return false;
            }
        }
        return true;
    }
    
    boolean checkBelow(int y) {
        for (int i = 0; i < 4; i++) {
            if (spots[currentPiece.getCoords()[i][0] + currentX][currentPiece.getCoords()[i][1] + y + 1] == 1) {
                return false;
            }
        }
        return true;
    }
    
}
