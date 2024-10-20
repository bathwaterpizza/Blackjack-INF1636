package model;

import java.util.Scanner;

public class MainTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      boolean success;
      System.out.println("--- NEW GAME ---");

      Game.choiceClear();

      success = Game.player.incrementBet(ChipColor.GOLD);
      if (!success) {
        scanner.close();
        System.exit(0);
      }

      Game.choiceDeal();

      System.out.println("Player initial bet: " + Game.player.bet);
      System.out.println("Player hand: " + Game.player.hand.toString());
      System.out.println("Dealer hand: " + Game.dealer.hand.toString());

      while (!Game.roundOver) {
        System.out.print("Choice (hit, dou, sur, sta, exi): ");
        String action = scanner.nextLine().trim().toLowerCase();

        switch (action) {
          case "hit":
            // Implement hit logic
            System.out.println("You chose to hit.");
            Game.choiceHit();
            break;
          case "dou":
            // Implement double logic
            System.out.println("You chose to double.");
            Game.choiceDouble();
            break;
          case "sur":
            // Implement surrender logic
            System.out.println("You chose to surrender.");
            Game.choiceSurrender();
            break;
          case "sta":
            // Implement stand logic
            System.out.println("You chose to stand.");
            Game.choiceStand();
            break;
          case "exi":
            scanner.close();
            Game.choiceExit();
          default:
            System.out.println("Invalid action, try again.");
            break;
        }

        System.out.println("Current player hand: " + Game.player.hand.toString());
        System.out.println("Current dealer hand: " + Game.dealer.hand.toString());
      }

      System.out.println("Round over.");
      System.out.println("Final player hand: " + Game.player.hand.toString());
      System.out.println("Final dealer hand: " + Game.dealer.hand.toString());

      if (Game.won) {
        System.out.println("You won!");
      } else if (Game.tied) {
        System.out.println("It's a tie!");
      } else {
        System.out.println("You lost.");
      }

      System.out.println("Player balance: " + Game.player.balance);
    }
  }
}
