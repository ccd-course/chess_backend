package com.chess.backend.gamemodel;

import com.chess.backend.gamemodel.contants.Color;

public class Player {
    private int id;
    private String name;
    private Enum<Color> color;

    public Player(int id, String name, Enum<Color> color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum<Color> getColor() {
        return color;
    }

    public void setColor(Enum<Color> color) {
        this.color = color;
    }
}
