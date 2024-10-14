package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChipTest {

  /**
   * Test to verify that a BLACK chip has the correct value of 1.
   */
  @Test
  public void testBlackChipValue() {
    Chip chip = new Chip(ChipColor.BLACK);
    assertEquals(1, chip.value);
  }

  /**
   * Test to verify that a PINK chip has the correct value of 5.
   */
  @Test
  public void testPinkChipValue() {
    Chip chip = new Chip(ChipColor.PINK);
    assertEquals(5, chip.value);
  }

  /**
   * Test to verify that a BLUE chip has the correct value of 10.
   */
  @Test
  public void testBlueChipValue() {
    Chip chip = new Chip(ChipColor.BLUE);
    assertEquals(10, chip.value);
  }

  /**
   * Test to verify that a RED chip has the correct value of 20.
   */
  @Test
  public void testRedChipValue() {
    Chip chip = new Chip(ChipColor.RED);
    assertEquals(20, chip.value);
  }

  /**
   * Test to verify that a GREEN chip has the correct value of 50.
   */
  @Test
  public void testGreenChipValue() {
    Chip chip = new Chip(ChipColor.GREEN);
    assertEquals(50, chip.value);
  }

  /**
   * Test to verify that a GOLD chip has the correct value of 100.
   */
  @Test
  public void testGoldChipValue() {
    Chip chip = new Chip(ChipColor.GOLD);
    assertEquals(100, chip.value);
  }
}
