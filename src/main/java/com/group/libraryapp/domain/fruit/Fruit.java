package com.group.libraryapp.domain.fruit;

public class Fruit {

    private Long id;

    protected Fruit() {
    }

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    private String name;

    private int price;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
