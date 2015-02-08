package com.sample;

import java.io.File;

import com.sample.parser.Parser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(new File("guiImpl.fxml").toURI().toURL());
    	Scene scene = new Scene(root);
        primaryStage.setTitle("Exemple");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.requestFocus();
    }

    public static void main(String[] args) {
		try {
			Parser p = new Parser();
			p.parseBPMN();
			p.parseFXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
        launch(args);
    }
}
