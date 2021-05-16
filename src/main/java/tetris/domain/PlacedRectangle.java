
package tetris.domain;

/**
 * Luokka pitää tallessa asetetun neliön infon.
 */
public class PlacedRectangle {
    
    Tetromino tetromino = new Tetromino();
    
    int x;
    int y;
    int pieceValue;
    Square square;
    
    
    public PlacedRectangle(int x, int y, int piece, Square s) {
        this.x = x;
        this.y = y;
        this.pieceValue = piece;
        this.square = s;
        
    }
    
    void reColor(int level) {
        square.setColor(tetromino.getColor(level, pieceValue));
        if (pieceValue == 1 || pieceValue > 5) {
            square.setStroke(tetromino.getColor(level, 0));
        }
    } 
    
    int getY() {
        return this.y;
    }
    void setY(int set) {
        this.y = set;
    }
    
}
