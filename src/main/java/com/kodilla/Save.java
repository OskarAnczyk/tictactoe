package com.kodilla;

import java.io.Serializable;

public class Save implements Serializable {

    private int circleWins;
    private int crossWins;
    private int draws;
    private FieldType[][] fields;
    private Difficulty difficulty;

    public Save(int circleWins, int crossWins, int draws, FieldType[][] fields,Difficulty difficulty) {
        this.circleWins = circleWins;
        this.crossWins = crossWins;
        this.draws = draws;
        this.fields = fields;
        this.difficulty = difficulty;
    }

    public int getCircleWins() {
        return circleWins;
    }

    public int getCrossWins() {
        return crossWins;
    }

    public int getDraws() {
        return draws;
    }

    public FieldType[][] getFields() {
        return fields;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }
}
