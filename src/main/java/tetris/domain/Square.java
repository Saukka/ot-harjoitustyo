
package tetris.domain;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

/**
 * Luokka palikan ruudulle
 */

public class Square {
    
    int x;
    int y;
    
    Rectangle square;
    
    Rectangle glow;
    Rectangle glow2;
    Rectangle corner;
    
    Rectangle[] all;
    
    public Square(double x, double y, int width) {
        
        this.x = (int) x;
        this.y = (int) y;
        
        square = new Rectangle(x, y, width, width);
        
        glow = new Rectangle(x + 19, y + 3, 6, 3);
        glow.setFill(Color.WHITE);
        
        glow2 = new Rectangle(x + 22, y + 6, 3, 3);
        glow2.setFill(Color.WHITE);
        
        corner = new Rectangle(x + 25, y, 3, 3);
        corner.setFill(Color.WHITE);
        
        all = new Rectangle[4];
        all[0] = square;
        all[1] = glow;
        all[2] = glow2;
        all[3] = corner;
    }
    
    void setColor(Color c) {
        square.setFill(c);
    }
    void setStroke(Color c) {
        square.setStroke(c);
        square.setStrokeWidth(3);
        square.setStrokeType(StrokeType.INSIDE);
    }

    Rectangle[] getSquare() {
        return all;
    }
    
    int getX() {
        return this.x;
    }
    int getY() {
        return this.y;
    }
    void setX(int x) {
        this.x = x;
        square.setX(x);
        glow.setX(x + 19);
        glow2.setX(x + 22);
        corner.setX(x + 25);
    }
    void setY(int y) {
        this.y = y;
        square.setY(y);
        glow.setY(y + 3);
        glow2.setY(y + 6);
        corner.setY(y);
    }
    
}