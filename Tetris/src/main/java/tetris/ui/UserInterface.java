/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.ui;

import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.geometry.Insets;
 
public class UserInterface extends Application {
    
    @Override
    public void start(Stage window) {
        
        
        window.setTitle("Tetris");
        
        Button button = new Button("A test button");
                
        BorderPane setUp = new BorderPane();
        setUp.setCenter(button);
        
        setUp.setPadding(new Insets(0,0,50,0));
        
        
        Scene scene = new Scene(setUp,400,300);
        
        window.setScene(scene);
        window.show();
                
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
