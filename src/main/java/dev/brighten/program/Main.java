package dev.brighten.program;

import dev.brighten.program.hash.GeneralHash;
import dev.brighten.program.utils.reflection.WrappedClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main extends Application {

    private static Run run;
    public static void main(String[] args) {
        System.out.println("Calling onEnable in Run class...");
        run = new Run();
        run.onEnable();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(stream);

            oos.writeObject(new Run());
            String hash = GeneralHash.getSHAHash(stream.toByteArray(), GeneralHash.SHAType.SHA1);

            System.out.println("run checksum:" + hash);

            if(!hash.equals("2ff1a7424e61ebde3ab8eaf8ab38d8914173ff85")) {
                System.exit(0);
            } else System.out.println("Passed check.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Started ! Launching JavaFX...");
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Checksum Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stop() {
        System.out.println("FXML scene window closed. Exiting program...");
        System.exit(0);
    }
}
