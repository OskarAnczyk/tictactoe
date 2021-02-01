package com.kodilla;

import java.io.Serializable;

public class Save implements Serializable {

    private int circleWins;
    private int crossWins;
    private int draws;
    private FieldType[][] fields;

    public Save(int circleWins, int crossWins, int draws, FieldType[][] fields) {
        this.circleWins = circleWins;
        this.crossWins = crossWins;
        this.draws = draws;
        this.fields = fields;
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
}
