package model;

class UnknownChipException extends RuntimeException {
  public UnknownChipException(String message) {
    super(message);
  }
}

class Chip {
  public ChipColor color;
  public int value;

  public Chip(ChipColor color) {
    this.color = color;
    this.value = getColorValue(color);
  }

  public static int getColorValue(ChipColor color) {
    if (color == null) {
      throw new UnknownChipException("Unknown chip color");
    }

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
        throw new UnknownChipException("Unknown chip color");
    }
  }
}
