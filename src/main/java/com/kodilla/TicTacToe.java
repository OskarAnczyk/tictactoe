package com.kodilla;

import javafx.application.Application;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TicTacToeBoard board = new TicTacToeBoard();

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(board.makeBoard());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
