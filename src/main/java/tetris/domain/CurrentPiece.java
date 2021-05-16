
package tetris.domain;


import javafx.scene.shape.Rectangle;

/**
 * Luokka ohjattavalle palikalle.
 */

public class CurrentPiece {
    
    int x;
    int y;
    
    Tetromino piece;
    
    Square[] pieceSquare;
    
    Rectangle[] ghostSquare;
    
    Board board;
    
    
    
    public CurrentPiece(Board board) {
        this.board = board;
        
        piece = new Tetromino();
        pieceSquare = new Square[4];
        
        ghostSquare = new Rectangle[4];
        
        for (int i = 0; i < 4; i++) {
            ghostSquare[i] = new Rectangle(0, -50, board.squareWidth - 2, board.squareWidth - 2);
            board.pane.getChildren().add(ghostSquare[i]);
        }
    }
    
    /**
     * Metodi liikuttaa palikkaa jos sitä on mahdollista liikuttaa.
     * @param move -1 jos vasemmalle, 1 jos oikealle
     */
    
    void moveVertical(int move) {
        if (board.checkVertical(move)) {
            for (int i = 0; i < 4; i++) {
                pieceSquare[i].setX(pieceSquare[i].getX() + (move * board.squareWidth));
            }
            x += move;
            updateGhost();
        }
    }
    
    /**
     * Metodi päivittää haamupalikan sijainnin ja värin.
     */
    
    void updateGhost() {
        int ghostY = y;
        while (board.checkBelow(ghostY)) {
            ghostY++;
        }
        for (int i = 0; i < 4; i++) {
            ghostSquare[i].setFill(piece.getColor(board.level, piece.shape.ordinal()));
            ghostSquare[i].setOpacity(0.35);
            ghostSquare[i].setX((x - 4 + piece.getCoords()[i][0]) * board.squareWidth + 2);
            ghostSquare[i].setY((ghostY - 4 + piece.getCoords()[i][1]) * board.squareWidth + 2);
        }
    }
    
    /**
     * Metodi kääntää palikkaa, jos kääntäminen on mahdollista.
     * @param right 1 jos käännetään oikealle.
     */
    void rotate(int right) {
        if (piece.shape.ordinal() == 1) {
            return;
        }
        
        int[][] newCoordinates;
        
        if (right == 1) {
            newCoordinates = piece.rotateRight();
        } else {
            newCoordinates = piece.rotateLeft();
        }
        
        int rotateHeight = board.checkRotate(newCoordinates);
        if (rotateHeight != -1) {
            piece.setCoords(newCoordinates);
            y -= rotateHeight;
            for (int i = 0; i < 4; i++) {
                pieceSquare[i].setX((x - 4 + newCoordinates[i][0]) * board.squareWidth + 2);
                pieceSquare[i].setY((y - 4 + newCoordinates[i][1]) * board.squareWidth + 2);
            }
            updateGhost();
        } 
    }
    
    /**
     * Metodi liikuttaa palikkaa alaspäin. Jos palikkaa ei voida liikuttaa alaspäin, palikka asetetaan.
     * @param squares kuinka monta ruutua alaspäin palikkaa liikutetaan.
     * @param softDrop onko kyseessä softdrop.
     */
    void moveDown(int squares, boolean softDrop) {
        if (squares == 1 && !board.checkBelow(y)) {
            board.place();
            return;
        }
        for (int i = 0; i < 4; i++) {
            pieceSquare[i].setY(pieceSquare[i].getY() + (board.squareWidth * squares));
        }
        y += squares;
        if (softDrop) {
            board.score++;
        }
    }
    
    /**
     * Metodi laskee kuinka alhaalle palikan voi tiputtaa, ja sitten asettaa sen sinne.
     */
    void hardDrop()  {
        
        int squares = 0;
        int dropY = y;
        
        while (board.checkBelow(dropY)) {
            dropY++;
            squares++;
        }
        moveDown(squares, false);
        board.score += squares * 2;
        board.place();
    }
    
}
