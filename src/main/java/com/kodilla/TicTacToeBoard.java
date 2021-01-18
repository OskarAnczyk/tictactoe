package com.kodilla;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TicTacToeBoard {
    private final Image BACKGROUND_IMAGE = new Image("file:src/main/resources/tictactoeboard.png");
    private final Image CIRCLE_IMAGE = new Image("file:src/main/resources/circle.png");
    private final Image CROSS_IMAGE = new Image("file:src/main/resources/cross.png");
    private final Image EMPTY_IMAGE = new Image("file:src/main/resources/empty.png");
    private Field[][] fields = new Field[3][3];
    private BorderPane board = new BorderPane();


    public Scene makeBoard() {
        this.board.setTop(makeFlowPane());

        GridPane gridPane = makeGridPane(makeBackground());
        makeStartingBoard();
        setMoveBehaviour(this.fields);
        transferFromFieldToGridPane(gridPane);

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
        gridPane.setBackground(background);
        return gridPane;
    }

    private void transferFromFieldToGridPane(GridPane gridPane) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridPane.add(this.fields[i][j],i,j,2,2);
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

        flowPane.setPrefSize(900, 100);

        return flowPane;
    }

    private void makeStartingBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Field field = new Field(FieldType.EMPTY);
                field.setImage(EMPTY_IMAGE);
                this.fields[i][j] = field;
            }
        }
    }

    private void setMoveBehaviour(Field[][] fields) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                makeMove(fields[i][j]);
            }
        }
    }

    private void makeMove(Field field) {
        field.setOnMouseClicked(e -> {
            if (field.getFieldType() == FieldType.EMPTY) {
                field.setFieldType(FieldType.PIECE_O);
                field.setImage(CIRCLE_IMAGE);
                System.out.println("malfajt");
            }


            e.consume();
        });
    }
}
