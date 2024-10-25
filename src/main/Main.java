package main;

import model.ConsoleGameTest;
import controller.Controller;

public class Main {
  public static void main(String[] args) {
    // ConsoleGameTest.run();

    Controller gameController = new Controller();
    gameController.initGameUI();
  }
}
