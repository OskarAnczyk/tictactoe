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
import javafx.scene.text.Text;

import java.io.*;
import java.util.Random;

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
    private Button buttonDifficulty;
    private File save = new File("save.file");
    private File leaderBoard = new File("leaderBoard.file");
    private Text text;
    private Text textLeaderBoard = new Text();
    private SaveScore saveScore;


    public Scene makeBoard() {
        setDifficulty(Difficulty.EASY);
        loadScore();
        setLeaderBoard();
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

        Button buttonReset = new Button("Reset");
        buttonReset.setOnMouseClicked(e -> {
            resetBoard();
            resetResults();
            setText();
        });
        buttonReset.setPrefHeight(100);
        buttonReset.setPrefWidth(100);

        this.buttonDifficulty  = new Button(this.difficulty.toString());
        buttonDifficulty.setOnMouseClicked(e -> {
            changeDifficulty();
            setText();
        });
        buttonDifficulty.setPrefHeight(100);
        buttonDifficulty.setPrefWidth(100);

        Button buttonSave = new Button("Save");
        buttonSave.setPrefSize(100,100);
        buttonSave.setOnMouseClicked(e -> {
            saveGame();
        });

        Button buttonLoad = new Button("Load");
        buttonLoad.setPrefSize(100,100);
        buttonLoad.setOnMouseClicked(e -> {
            loadGame();
            setText();
            lockDifficultyButton();
        });

        text = new Text("Circle wins: " + getCircleWins() +
                "\nCross wins: " + getCrossWins() +
                "\nDraws: " + getDraws());

        Button buttonSaveScore = new Button("Save score");
        buttonSaveScore.setPrefSize(100,100);
        buttonSaveScore.setOnMouseClicked(e -> {
            saveScore();
            loadScore();
            setLeaderBoard();
        });

        Button buttonResetScoreBoard = new Button("Reset score");
        buttonResetScoreBoard.setPrefSize(100,100);
        buttonResetScoreBoard.setOnMouseClicked(e -> {
            SaveScore saveScore1 = new SaveScore(0,0,0,Difficulty.EASY);
            this.saveScore = saveScore1;
            saveScore(saveScore1);
            setLeaderBoard();
        });

        flowPane.getChildren().add(buttonReset);
        flowPane.getChildren().add(buttonDifficulty);
        flowPane.getChildren().add(buttonSave);
        flowPane.getChildren().add(buttonLoad);
        flowPane.getChildren().add(text);
        flowPane.getChildren().add(buttonSaveScore);
        flowPane.getChildren().add(buttonResetScoreBoard);
        flowPane.getChildren().add(textLeaderBoard);
        flowPane.setPrefSize(900, 100);

        return flowPane;
    }

    private void loadScore(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(leaderBoard));
            SaveScore saveScore;
            saveScore = (SaveScore) ois.readObject();
            this.saveScore = saveScore;

            ois.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void saveScore(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(leaderBoard));
            SaveScore saveScore = new SaveScore(this.circleWins,this.crossWins,this.draws,this.difficulty);
            oos.writeObject(saveScore);
            oos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void saveScore(SaveScore saveScore){
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(leaderBoard));
            oos.writeObject(saveScore);
            oos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void setLeaderBoard(){
        if(saveScore != null){
            this.textLeaderBoard.setText("Saved score:" +
                    "\nCircle wins: " + saveScore.getCircleWins() +
                    "\nCross wins: " + saveScore.getCrossWins() +
                    "\nDraws: " + saveScore.getDraws() +
                    "\nDifficulty: " + saveScore.getDifficulty());
        } else {
            this.textLeaderBoard.setText("Saved score:" +
                    "\nCircle wins: ??" +
                    "\nCross wins: ??" +
                    "\nDraws: ??" +
                    "\nDifficulty: ??");
        }
    }

    private void loadGame() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(save));
            Save results;
            results = (Save) ois.readObject();
            setCircleWins(results.getCircleWins());
            setCrossWins(results.getCrossWins());
            setDraws(results.getDraws());
            setDifficulty(results.getDifficulty());
            this.buttonDifficulty.setText(this.difficulty.toString());


            FieldType[][] loadedFields = results.getFields();
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[0].length; j++) {
                    this.fields[i][j].setFieldType(loadedFields[i][j]);
                    this.fields[i][j].setImage(chooseImage(loadedFields[i][j]));
                }
            }

            ois.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Image chooseImage(FieldType fieldType){
        switch (fieldType){
            case PIECE_O:
                return CIRCLE_IMAGE;
            case PIECE_X:
                return CROSS_IMAGE;
            case EMPTY:
                return EMPTY_IMAGE;
        }
        return EMPTY_IMAGE;
    }

    private void saveGame() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(save));
            Save results = new Save(this.circleWins, this.crossWins, this.draws, transferToFieldTypeArray(),this.getDifficulty());
            oos.writeObject(results);
            oos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private FieldType[][] transferToFieldTypeArray(){
        FieldType[][] arrayFieldType = new FieldType[this.fields.length][this.fields[0].length];

        for (int i = 0; i < this.fields.length; i++) {
            for (int j = 0; j < this.fields[0].length; j++) {
                arrayFieldType[i][j] = this.fields[i][j].getFieldType();
            }
        }

        return arrayFieldType;
    }

    private void changeDifficulty() {
        switch(this.difficulty){
            case EASY:
                setDifficulty(Difficulty.HARD);
                this.buttonDifficulty.setText("HARD");
                break;
            case HARD:
                setDifficulty(Difficulty.EASY);
                this.buttonDifficulty.setText("EASY");
                break;
        }
        resetResults();
    }

    private void resetResults(){
        setCircleWins(0);
        setCrossWins(0);
        setDraws(0);
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
                this.buttonDifficulty.setDisable(true);
            } else {
                return;
            }

            if (checkWin(FieldType.PIECE_O)) {
                circleWin();
                setText();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wynik rozgrywki");
                alert.setHeaderText("Gratulacje! Wygrales!");
                alert.setContentText("Aktualny wynik to:\nKolko: " + this.circleWins + "\nKrzyzyk: " + this.crossWins + "\nRemis: " + this.draws);
                alert.showAndWait();

                resetBoard();
                return;
            }

            if(checkDraw()){
                isDraw();
                setText();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wynik rozgrywki");
                alert.setHeaderText("Remis.");
                alert.setContentText("Aktualny wynik to:\nKolko: " + this.circleWins + "\nKrzyzyk: " + this.crossWins + "\nRemis: " + this.draws);
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
            } else if(this.difficulty == Difficulty.HARD){
                boolean moveFound = false;
                Random rand = new Random();
                while(!moveFound){
                    int i = rand.nextInt(3);
                    int j = rand.nextInt(3);
                    if(this.fields[i][j].getFieldType() == FieldType.EMPTY){
                        this.fields[i][j].setFieldType(FieldType.PIECE_X);
                        this.fields[i][j].setImage(CROSS_IMAGE);
                        moveFound = true;
                    }
                }
            }

            if (checkWin(FieldType.PIECE_X)) {
                crossWin();
                setText();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Wynik rozgrywki");
                alert.setHeaderText("Niestety przegrales...");
                alert.setContentText("Aktualny wynik to:\nKolko: " + this.circleWins + "\nKrzyzyk: " + this.crossWins + "\nRemis: " + this.draws);
                alert.showAndWait();

                resetBoard();
                return;
            }

            e.consume();
        });
    }

    private void lockDifficultyButton(){
        boolean doLock = false;
        for (int i = 0; i < this.fields.length; i++) {
            for (int j = 0; j < this.fields[0].length; j++) {
                if(this.fields[i][j].getFieldType() != FieldType.EMPTY){
                    doLock = true;
                }
            }
        }
        this.buttonDifficulty.setDisable(doLock);
    }

    private boolean checkDraw() {
        boolean isDraw = true;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(this.fields[i][j].getFieldType() == FieldType.EMPTY){
                    isDraw = false;
                }
            }
        }
        return isDraw;
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
        this.buttonDifficulty.setDisable(false);
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                fields[i][j].setImage(EMPTY_IMAGE);
                fields[i][j].setFieldType(FieldType.EMPTY);
            }
        }
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

    private void setText(){
        this.text.setText("Circle wins: " + getCircleWins() +
                "\nCross wins: " + getCrossWins() +
                "\nDraws: " + getDraws());
    }
}
