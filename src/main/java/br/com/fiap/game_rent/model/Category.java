package br.com.fiap.game_rent.model;

import java.util.Random;

public class Category {
    private Long id;
    private String name;
    private String icon;
    
    public Category(Long id, String name, String icon) {
        this.id = Math.abs(new Random().nextLong());
        this.name = name;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
   
    public void setId(Long id){
        this.id = id;
    }

    public String toString() {
        return id + " - " + name + " - " + icon;
    }
}
