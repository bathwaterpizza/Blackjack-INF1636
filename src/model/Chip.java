package model;

class Chip {
  public ChipColor color;
  public int value;

  public Chip(ChipColor color) {
    this.color = color;
    this.value = getColorValue(color);
  }

  // get the value of the chip by its color
  public static int getColorValue(ChipColor color) {
    switch (color) {
      case BLACK:
        return 1;
      case PINK:
        return 5;
      case BLUE:
        return 10;
      case RED:
        return 20;
      case GREEN:
        return 50;
      case GOLD:
        return 100;
      default:
        System.out.println("Should not happen! unknown chip.");
        return -1;
    }
  }
}
