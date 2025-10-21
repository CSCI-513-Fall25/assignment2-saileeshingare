package com.columbus;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private int x;
    private int y;
    private List<PirateShip> observers = new ArrayList<>();
    private Runnable gameOverCallback;

    public Ship(int x, int y, Runnable gameOverCallback) {
        this.x = x;
        this.y = y;
        this.gameOverCallback = gameOverCallback;
    }

    public void addObserver(PirateShip pirate) {
        observers.add(pirate);
    }

    public void removeObserver(PirateShip pirate) {
        observers.remove(pirate);
    }

    private void notifyObservers() {
        for (PirateShip pirate : observers) {
            pirate.update(x, y);
        }
        checkPirateCollision();
    }

    private void checkPirateCollision() {
        for (PirateShip pirate : observers) {
            if (pirate.getX() == x && pirate.getY() == y && gameOverCallback != null) {
                gameOverCallback.run();
            }
        }
    }

    public void moveNorth(OceanMap map) {
        if (y > 0 && map.isOcean(x, y - 1)) { y--; notifyObservers(); }
    }

    public void moveSouth(OceanMap map) {
        if (y < map.getDimension() - 1 && map.isOcean(x, y + 1)) { y++; notifyObservers(); }
    }

    public void moveEast(OceanMap map) {
        if (x < map.getDimension() - 1 && map.isOcean(x + 1, y)) { x++; notifyObservers(); }
    }

    public void moveWest(OceanMap map) {
        if (x > 0 && map.isOcean(x - 1, y)) { x--; notifyObservers(); }
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
