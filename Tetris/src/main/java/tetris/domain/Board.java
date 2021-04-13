
package tetris.domain;


import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import tetris.domain.Tetramino.piece;
import javafx.scene.shape.Rectangle;



public class Board {
    
    final int widthPX = 280;
    final int heightPX = 560;
    
    int widthSquares = 10;
    int heightSquares = 20;
    
    int squareWidth = widthPX/widthSquares;
    
    Pane grid = new Pane();
    Scene scene = new Scene(grid,widthPX,heightPX);
    
    Tetramino currentPiece;
    int currentX;
    int currentY;
    
    Rectangle[] rectangle;

    public Board() {
        rectangle = new Rectangle[4];
    }
    
    void newPiece() {
        currentPiece = new Tetramino();
        currentPiece.setRandomShape();
        
        currentX = widthSquares/2-1;
        currentY = currentPiece.maxY();
        
        drawPiece();   
    }
    
    
    Scene getScene() {
        return this.scene;
    }
    
    void drawPiece() {
        int[][] coords = currentPiece.getCoords();
        for (int i = 0; i<4; i++) {
            rectangle[i] = new Rectangle((coords[i][0]+currentX)*squareWidth,(coords[i][1]+currentY)*squareWidth, squareWidth, squareWidth);
            grid.getChildren().add(rectangle[i]);
        }
    }
     
    void movePieceLeft() {
        for (int i = 0; i<4; i++) {
            rectangle[i].setTranslateX(-squareWidth);
        }
    }
    void movePieceRight() {
        for (int i = 0; i<4; i++) {
            rectangle[i].setTranslateX(squareWidth);
        }
    }
    
}