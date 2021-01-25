package com.kodilla;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TicTacToeBoard {
    private final Image BACKGROUND_IMAGE = new Image("file:src/main/resources/tictactoeboard.png");
    private final Image CIRCLE_IMAGE = new Image("file:src/main/resources/circle.png");
    private final Image CROSS_IMAGE = new Image("file:src/main/resources/cross.png");
    private final Image EMPTY_IMAGE = new Image("file:src/main/resources/empty.png");
    private Field[][] fields = new Field[3][3];
    private BorderPane board = new BorderPane();
    private Difficulty difficulty;
    private GridPane gridPane;
    private int circleWins;
    private int crossWins;
    private int draws;


    public Scene makeBoard() {
        setDifficulty(Difficulty.EASY);
        setCircleWins(0);
        setCrossWins(0);
        setDraws(0);
        this.board.setTop(makeFlowPane());

        gridPane = makeGridPane(makeBackground());
        makeStartingBoard();
        transferFromFieldToGridPane();

        this.board.setBottom(gridPane);

        Scene scene = new Scene(board, 900, 1000, Color.DARKGRAY);
        return scene;
    }

    private GridPane makeGridPane(Background background) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        gridPane.setVgap(300);
        gridPane.setHgap(300);
        gridPane.setGridLinesVisible(true);
        gridPane.setBackground(background);
        return gridPane;
    }

    private void transferFromFieldToGridPane() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridPane.add(this.fields[i][j], i, j, 2, 2);
            }
        }
    }

    private Background makeBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(BACKGROUND_IMAGE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        return new Background(backgroundImage);
    }

    private FlowPane makeFlowPane() {
        FlowPane flowPane = new FlowPane();

        Button buttonReset = new Button("reset");

        buttonReset.setOnMouseClicked(e -> {
            resetBoard();
        });

        buttonReset.setPrefHeight(100);
        buttonReset.setPrefWidth(100);


        flowPane.getChildren().add(buttonReset);
        flowPane.setPrefSize(900, 100);

        return flowPane;
    }

    private void makeStartingBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Field field = new Field(FieldType.EMPTY);
                field.setImage(EMPTY_IMAGE);
                setOnClickAction(field);
                this.fields[i][j] = field;
            }
        }
    }

    private void setOnClickAction(Field field) {
        field.setOnMouseClicked(e -> {
            if (field.getFieldType() == FieldType.EMPTY) {
                field.setImage(CIRCLE_IMAGE);
                field.setFieldType(FieldType.PIECE_O);

            } else {
                return;
            }

            if (checkWin(FieldType.PIECE_O)) {
                System.out.println("Wygrana");
                circleWin();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Look, an Information Dialog");
                alert.setContentText("I have a great message for you!");
                alert.showAndWait();

                resetBoard();
                return;
            }

            if (this.difficulty == Difficulty.EASY) {
                boolean moveFound = false;
                for (int i = 0; i < 3; i++) {
                    for (int k = 0; k < 3; k++) {
                        if (this.fields[i][k].getFieldType() == FieldType.EMPTY) {
                            this.fields[i][k].setFieldType(FieldType.PIECE_X);
                            this.fields[i][k].setImage(CROSS_IMAGE);
                            moveFound = true;
                            break;
                        }
                    }
                    if (moveFound) {
                        break;
                    }
                }
            } else {

            }

            if (checkWin(FieldType.PIECE_X)) {
                crossWin();
                System.out.println("Przegrana");
                resetBoard();
                return;
            }

            e.consume();
        });
    }

    private boolean checkWin(FieldType field) {
        if (this.fields[0][0].getFieldType() == field && this.fields[0][1].getFieldType() == field && this.fields[0][2].getFieldType() == field) {
            return true;
        }

        if (this.fields[1][0].getFieldType() == field && this.fields[1][1].getFieldType() == field && this.fields[1][2].getFieldType() == field) {
            return true;
        }

        if (this.fields[2][0].getFieldType() == field && this.fields[2][1].getFieldType() == field && this.fields[2][2].getFieldType() == field) {
            return true;
        }

        if (this.fields[0][0].getFieldType() == field && this.fields[1][0].getFieldType() == field && this.fields[2][0].getFieldType() == field) {
            return true;
        }

        if (this.fields[0][1].getFieldType() == field && this.fields[1][1].getFieldType() == field && this.fields[2][1].getFieldType() == field) {
            return true;
        }

        if (this.fields[0][2].getFieldType() == field && this.fields[1][2].getFieldType() == field && this.fields[2][2].getFieldType() == field) {
            return true;
        }

        if (this.fields[0][0].getFieldType() == field && this.fields[1][1].getFieldType() == field && this.fields[2][2].getFieldType() == field) {
            return true;
        }

        if (this.fields[0][2].getFieldType() == field && this.fields[1][1].getFieldType() == field && this.fields[2][0].getFieldType() == field) {
            return true;
        }

        return false;
    }

    private void resetBoard() {
        makeStartingBoard();
        this.gridPane.getChildren().removeAll();
        transferFromFieldToGridPane();
    }

    private void circleWin() {
        setCircleWins(getCircleWins() + 1);
    }

    private void crossWin() {
        setCrossWins(getCrossWins() + 1);
    }

    private void isDraw() {
        setDraws(getDraws() + 1);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getCircleWins() {
        return circleWins;
    }

    public void setCircleWins(int circleWins) {
        this.circleWins = circleWins;
    }

    public int getCrossWins() {
        return crossWins;
    }

    public void setCrossWins(int crossWins) {
        this.crossWins = crossWins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
}
