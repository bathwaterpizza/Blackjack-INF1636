package model;

// this enum contains the chips' colors and their respective values
public enum Chip {
  BLACK(1),
  PINK(5),
  BLUE(10),
  RED(20),
  GREEN(50),
  GOLD(100);

  private final int value;

  Chip(int value) {
    this.value = value;
  }

  public int toInt() {
    return value;
  }
}
