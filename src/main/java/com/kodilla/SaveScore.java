package com.kodilla;

import java.io.Serializable;

public class SaveScore implements Serializable {
    private int circleWins;
    private int crossWins;
    private int draws;
    private Difficulty difficulty;

    public SaveScore(int circleWins, int crossWins, int draws, Difficulty difficulty) {
        this.circleWins = circleWins;
        this.crossWins = crossWins;
        this.draws = draws;
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

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
