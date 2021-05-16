
package tetris.domain;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import tetris.domain.Tetromino.shape;

/**
 * Luokka hoitaa kaikki pelialueella tapahtuvat asiat, kuten peliruutujen tilat ja palikan liikuttamisen.
 */
public class Board {
    
    final int squareWidth = 30;
    int widthSquares = 10;
    int heightSquares = 20;
    
    int widthPX;
    int heightPX;
    
    Pane pane;
    
    int[][] spots; // Pelialueen ruutujen tila pidetään tässä muuttujassa. Jos kohta xy on täynnä, niin spots[x][y] = 1, muuten spots[x][y] = 0
    
    // Lista pitää sisällään asetettujen neliöiden tiedot
    ArrayList<PlacedRectangle> placed;
    
    ArrayList<Integer> pieceBag;
    
    CurrentPiece current;
    
    
    int score;
    int level;
    int lines;
    int startLevel;
    boolean end;
    boolean check;
    
    int nextPiece;
    int holdPiece;
    boolean canSwapHold;
    
    public Board(int startLevel, boolean thin) { 
        
        if (thin) {
            widthSquares = 4;
        }
    
        widthPX = squareWidth * widthSquares + 2;
        heightPX = squareWidth * heightSquares + 2;
        
        pane = new Pane();
        pane.setPrefSize(widthPX, heightPX);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        spots = new int[18][26];
        placed = new ArrayList<>();
        
        // annetaan pelialueen ulkopuolella oleville koordinaateille arvot 1.
        for (int x = 0; x < 18; x++) {
            for (int y = 25; y > -1; y--) {
                if (x < 4 || x > 3 + widthSquares || y > heightSquares + 3) {
                    spots[x][y] = 1;
                }
            }
        }
        
        pieceBag = new ArrayList<>();
        
        for (int i = 1; i < 8; i++) {
            pieceBag.add(i);
        }
        nextPiece = getNextPiece();
        holdPiece = 0;
        canSwapHold = true;
        
        this.startLevel = startLevel;
        score = startingScore(startLevel);
        level = startLevel;
        lines = 0;
        end = false;
        
        current = new CurrentPiece(this);
    }
    
    int startingScore(int startLevel) {
        switch (startLevel) {
            case 12:
                return 80000;
            case 9:
                return 40000;
            case 6:
                return 20000;
            case 3:
                return 5000;
            default:
                return 0;
        }
    }
    
    void newPiece(boolean hold, int pieceValue) {
        current.piece.setCurrentShape(shape.values()[pieceValue]);
        
        current.x = widthSquares / 2 + 3;
        current.y = 3;
        
        drawPiece(current.x - 4, current.y - 4, current.piece, current.pieceSquare, pane);
        current.updateGhost();
        
        if (!hold) {
            nextPiece = getNextPiece();
        }
        
    }
    
    /**
     * Metodi piirtää palikan.
     * @param x x-koordinaatti piirretyn palikan keskipisteelle ruudulla
     * @param y y-koordinaatti piirretyn palikan keskipisteelle ruudulla
     * @param piece piirrettävä palikka
     * @param pane pane johon palikka piirretään
     */
    void drawPiece(double x, double y, Tetromino piece, Square[] square, Pane pane) {
        
        for (int i = 0; i < 4; i++) {
            square[i] = new Square((piece.getCoords()[i][0] + x) * squareWidth + 2, (piece.getCoords()[i][1] + y) * squareWidth + 2, squareWidth - 2);
            square[i].setColor(piece.getColor(level, piece.shape.ordinal()));
            if (piece.shape.ordinal() == 1 || piece.shape.ordinal() > 5) {
                square[i].setStroke(piece.getColor(level, 0));
            }
            pane.getChildren().addAll(square[i].getSquare());
        } 
    }
    
    /**
     * Metodi asettaa palikan peliruudulle. 
     * Jos palikkaa ei saada asetettua pelialueen sisälle, peli loppuu.
     */
    void place() {
        boolean endGame = true;
        for (int i = 0; i < 4; i++) {
            placed.add(new PlacedRectangle(current.piece.getCoords()[i][0] + current.x, current.piece.getCoords()[i][1] + current.y, current.piece.shape.ordinal(), current.pieceSquare[i]));
            spots[current.piece.getCoords()[i][0] + current.x][current.piece.getCoords()[i][1] + current.y] = 1;
            if (current.piece.getCoords()[i][1] + current.y > 4) {
                endGame = false;
            }
            
        }
        end = endGame;
        canSwapHold = true;
        clearLines();
        newPiece(false, nextPiece);
    }
    /**
     * Tason vaihtuessa metodi uudelleenvärittää asetetut palikat.
     */
    void reColorPlaced() {
        for (int i = 0; i < placed.size(); i++) {
            placed.get(i).reColor(level);
        }
    }
    /**
     * Pussi-menetelmää käyttävä metodi, joka antaa seuraavan palikan.
     * Pussissa on jokaista palikkaa yksi, ja ne annetaan satunnaisessa järjestyksessä. Pussin tyhjennettyä se täytetään uudelleen.
     * @return seuraava palikka
     */
    int getNextPiece() {
        int random = new Random().nextInt(pieceBag.size());
        int piece = pieceBag.get(random);
        pieceBag.remove(random);
        if (pieceBag.isEmpty()) {
            for (int i = 1; i < 8; i++) {
                pieceBag.add(i);
            }
        }
        return piece;
    }
    
    void swapHold() {
        if (holdPiece == 0) {
            holdPiece = current.piece.shape.ordinal();
            newPiece(false, nextPiece);
            pane.getChildren().remove(placed.size() * 4 + 4, placed.size() * 4 + (5 * 4));
            canSwapHold = false;
        } else {
            pane.getChildren().remove(placed.size() * 4 + 4, placed.size() * 4 + (5 * 4));
            int pieceBefore = current.piece.shape.ordinal();
            newPiece(true, holdPiece);
            holdPiece = pieceBefore;
            canSwapHold = false;
        }
        
    }
    /**
     * Metodi käy jokaisen rivin läpi ja katsoo, onko rivi täynnä. Jos rivi on täynnä, rivi sorrutetaan ja yllä olevia rivejä tuodaan alaspäin.
     * Mahdollisten rivien sorruttamisien jälkeen sorruttamisesta annetaan pisteet.
     */
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
                        pane.getChildren().remove(4 + (j * 4), 8 + (j * 4));
                        placed.remove(j);
                        j--;
                    }
                }
                // rivin yläpuolella olevien rivien alaspäin tuominen
                for (int h = 0; h < placed.size(); h++) {
                    if (placed.get(h).getY() < i) {
                        placed.get(h).setY(placed.get(h).getY() + 1);
                        for (int a = 0; a < 4; a++) {
                            pane.getChildren().get(4 + (4 * h) + a).setTranslateY(pane.getChildren().get(4 + (4 * h) + a).getTranslateY() + squareWidth);
                        }
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
        if (lines >= level * 10 - ((startLevel - 1) * 10)) {
            level++;
            reColorPlaced();
        } 
    }
    
    /**
     * Metodi tarkastaa, voiko palikkaa kääntää. Aluksi katsotaan, voiko palikkaa normaalisti kääntää annetuille koordinaateille. Jos ei, testataan, jos palikan voisi kääntää yhden korkeammalle riville.
     * @param coordinates tarkistettavat koordinaatit.
     * @return vastaus voiko palikkaa kääntää, ja minne se käännetään. Jos palikkaa ei voi kääntää, return = -1
     */
    int checkRotate(int[][] coordinates) {
        int answer = 0;
        for (int i = 0; i < 4; i++) {
            if (spots[coordinates[i][0] + current.x][coordinates[i][1] + current.y] == 1) {
                answer = -1;
            }
        }
        if (answer == 0) {
            return answer;
        }
        // jos palikkaa ei saanut käännettyä, koitetaan se kääntää yhdelle korkeammalle riville
        answer = 1;
        for (int i = 0; i < 4; i++) {
            if (spots[coordinates[i][0] + current.x][coordinates[i][1] + current.y - 1] == 1) {
                answer = -1;
            }
        }
        return answer;
    }
    
    // move = -1 kun tarkastetaan vasemmalle liikkuminen ja 1 kun oikealle.
    boolean checkVertical(int move) {
        for (int i = 0; i < 4; i++) {
            if (spots[current.piece.getCoords()[i][0] + current.x + move][current.piece.getCoords()[i][1] + current.y] == 1) {
                return false;
            }
        }
        return true;
    }
    
    boolean checkBelow(int y) {
        for (int i = 0; i < 4; i++) {
            if (spots[current.piece.getCoords()[i][0] + current.x][current.piece.getCoords()[i][1] + y + 1] == 1) {
                return false;
            }
        }
        return true;
    }
    
}