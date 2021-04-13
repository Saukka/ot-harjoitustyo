
package tetris.ui;

import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import tetris.domain.Tetris;
 
public class UserInterface extends Application {
    
    @Override
    public void start(Stage window) {
        
        window.setTitle("Tetris");
        
        Button startButton = new Button("Start");
                
        BorderPane setUp = new BorderPane();
        setUp.setCenter(startButton);
        
        Scene scene = new Scene(setUp,400,300);
        
        window.setScene(scene);
        
        Tetris tetris = new Tetris();
        
        
        startButton.setOnAction(e-> {
            tetris.start(window);
        });
        
        window.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
