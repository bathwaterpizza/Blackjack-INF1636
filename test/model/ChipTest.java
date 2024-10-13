package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChipTest {
  @Test
  public void testBlackChipValue() {
    Chip chip = new Chip(ChipColor.BLACK);
    assertEquals(1, chip.value);
  }

  @Test
  public void testPinkChipValue() {
    Chip chip = new Chip(ChipColor.PINK);
    assertEquals(5, chip.value);
  }

  @Test
  public void testBlueChipValue() {
    Chip chip = new Chip(ChipColor.BLUE);
    assertEquals(10, chip.value);
  }

  @Test
  public void testRedChipValue() {
    Chip chip = new Chip(ChipColor.RED);
    assertEquals(20, chip.value);
  }

  @Test
  public void testGreenChipValue() {
    Chip chip = new Chip(ChipColor.GREEN);
    assertEquals(50, chip.value);
  }

  @Test
  public void testGoldChipValue() {
    Chip chip = new Chip(ChipColor.GOLD);
    assertEquals(100, chip.value);
  }

  @Test(expected = UnknownChipException.class)
  public void testUnknownChipThrowsException() {
    Chip.getColorValue(null);
  }
}
