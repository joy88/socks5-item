package me.binf.socks5.client.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *                                              g
 */
public class FXMLFxApp extends Application {


    @Override
    public void start( Stage primaryStage ) throws Exception {
        VBox root = FXMLLoader.load(this.getClass().getResource("/view/PaneLayout.fxml"));
        Scene scene = new Scene( root, 500, 400 );
        primaryStage.setScene( scene );
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main( String[] args ) {
        Application.launch( FXMLFxApp.class );
    }

}
