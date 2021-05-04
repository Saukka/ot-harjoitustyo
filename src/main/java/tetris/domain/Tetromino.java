
package tetris.domain;

import java.util.Random;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Luokassa on jokaisen palikan tiedot kuten koordinaatit ja palikan kääntäminen.
 */
public class Tetromino {

    
    Color[] colors;
    ArrayList<Integer> pieceBag = new ArrayList<>();
    Color[] strokeColor;
    
    
    // Palikoiden nimet	
    enum piece {
        EMPTY, SPIECE, SPiece, MIRRORSPIECE, LPIECE, MIRRORLPIECE, TPIECE, IPIECE
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
    
    public Tetromino() {
        currentCoords = new int[4][2];
        setCurrentShape(piece.EMPTY);
        for (int i = 1; i < 8; i++) {
            pieceBag.add(i);
        }
    }
    
    /**
     * Metodi palauttaa annetulle palikalle kuuluvan värin. colors-arrayn ensimmäisessä arvossa on reunaviivojen väri.
     * @param level taso millä ollaan
     * @param value palikan arvo
     * @return palikalle kuuluva väri
     */
    Color getColor(int level, int value) {
        if (level < 3) {
            colors = new Color[]{Color.web("0x29C343"), Color.web("0xF6FFFF"), Color.web("0x48F62D"), Color.web("0x3752FF"), Color.web("0x48F62D"), Color.web("0x3752FF"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        } else if (level < 5) {
            colors = new Color[]{Color.web("0x781E98"), Color.web("0xF6FFFF"), Color.web("0xFFB7FB"), Color.web("0xA824D8"), Color.web("0xFFB7FB"), Color.web("0xA824D8"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        } else if (level < 8) {
            colors = new Color[]{Color.web("0x5FD1A0"), Color.web("0xF6FFFF"), Color.web("0xA2A2FF"), Color.web("0x74FFC3"), Color.web("0xA2A2FF"), Color.web("0x74FFC3"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        }  else if (level < 10) {
            colors = new Color[]{Color.web("0x1E25F2"), Color.web("0xF6FFFF"), Color.web("0xB61B1B"), Color.web("0x333AFF"), Color.web("0xB61B1B"), Color.web("0x333AFF"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        } else if (level < 12) {
            colors = new Color[]{Color.web("0XE50101"), Color.web("0xF6FFFF"), Color.web("0xFFCC33"), Color.web("0xB61B1B"), Color.web("0xFFCC33"), Color.web("0xB61B1B"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        } else {
            colors = new Color[]{Color.web("0x3744F7"), Color.web("0xF6FFFF"), Color.web("0xA6FAFF"), Color.web("0x3A41FF"), Color.web("0xA6FAFF"), Color.web("0x3A41FF"), Color.web("0xF6FFFF"), Color.web("0xF6FFFF")};
        }
        return colors[value];
    }
    
    
    void setCurrentShape(piece piece) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(pieces[piece.ordinal()], 0, currentCoords, 0, 4);
        }
        current = piece;
    }
    
    void setRandomShape() {
        piece[] pieceValues = piece.values();
        setCurrentShape(pieceValues[new Random().nextInt(7) + 1]);
    }
    
    int width() {
        return maxX() - minX() + 1;
    }
    
    int height() {
        return maxY() - minY() + 1;
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
            newCoords[i][1] = -currentCoords[i][0];
        }
        return newCoords;
    }
    
    int[][] rotateRight() {
        int[][] newCoords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            newCoords[i][0] = -currentCoords[i][1];
            newCoords[i][1] = currentCoords[i][0];
        }
        return newCoords;
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
    
}