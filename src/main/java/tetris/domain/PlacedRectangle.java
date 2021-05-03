
package tetris.domain;

import javafx.scene.shape.Rectangle;

// Asetetun ruudun info
public class PlacedRectangle {
    
    Tetromino tetromino = new Tetromino();
    
    int x;
    int y;
    int pieceValue;
    Rectangle rectangle;
    
    
    public PlacedRectangle(int x, int y, int piece, Rectangle r) {
        this.x = x;
        this.y = y;
        this.pieceValue = piece;
        this.rectangle = r;
        
    }
    
    void reColor(int level) {
        rectangle.setFill(tetromino.getColor(level, pieceValue));
        rectangle.setStroke(tetromino.getColor(level, 0));
    }
    
    int getY() {
        return y;
    }
    void setY(int set) {
        this.y = set;
    }
    
}
