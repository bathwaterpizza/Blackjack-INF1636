package model;

enum ChipColor {
  BLACK(1),
  PINK(5),
  BLUE(10),
  RED(20),
  GREEN(50),
  GOLD(100);

  private final int value;

  ChipColor(int value) {
    this.value = value;
  }

  public int toInt() {
    return value;
  }
}
