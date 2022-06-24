package com.example.demo.unchanged;
/**
 * The type of each assistentCard's deck
  */
public enum Mage {
    SkyMagician("SkyMagician"),
    VioletWitch("VioletWitch"),
    YellowKing("YellowKing"),
    HerbMagician("HerbMagician");

    private String name;

    Mage(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
