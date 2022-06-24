package com.example.demo.unchanged;

public interface ViewController {
    public void initialPhase();
    public void planningPhase();
    public void actionPhase();
    public void waitPhase();
    public void displayMessage(String message);
    public void update(updateMessage update);
    public void playerDisconnected(String playerName);
}
