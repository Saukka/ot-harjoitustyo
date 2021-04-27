
package tetris.ui;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
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
        startButton.setLayoutX(180);
        startButton.setLayoutY(140);
        
        Label controls = new Label("Controls: \n Rotate left Z \n Hard drop SPACE \n Move piece LEFT, RIGHT");
        controls.setLayoutX(30);
        controls.setLayoutY(50);
                
        Pane setUp = new Pane();
        setUp.getChildren().addAll(startButton,controls);
        
        Scene scene = new Scene(setUp,600,560);
        
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
