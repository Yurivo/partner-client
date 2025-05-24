package com.example.partnerclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Main extends Application {

    private static Process serverProcess;

    public static void main(String[] args) {
        try {
            File serverJar = new File("partner-service-0.0.1-SNAPSHOT.jar");
            if (!serverJar.exists()) {
                System.err.println("Файл partner-service-0.0.1-SNAPSHOT.jar не найден!");
                System.exit(1);
            }

            ProcessBuilder pb = new ProcessBuilder("java", "-jar", serverJar.getAbsolutePath());
            pb.inheritIO();
            serverProcess = pb.start();

            int attempts = 0;
            while (!isPortOpen("localhost", 8080) && attempts < 20) {
                Thread.sleep(500);
                attempts++;
            }

            if (!isPortOpen("localhost", 8080)) {
                System.err.println("Сервер не запустился на порту 8080!");
                System.exit(1);
            }

            launch(args);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/partnerclient/main-view.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Партнёрская система");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (serverProcess != null && serverProcess.isAlive()) {
            serverProcess.destroy();
            System.out.println("Сервер завершён");
        }
    }

    private static boolean isPortOpen(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
