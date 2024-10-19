package model;

import java.util.Scanner;

public class MainTest {
  public static void main(String[] args) {
    boolean playing = true;
    Scanner scanner = new Scanner(System.in);

    while (playing) {
      System.out.println("--- NEW GAME ---");

      Game.choiceNewRound();

      Game.player.incrementBet(ChipColor.GOLD);
      Game.player.incrementBet(ChipColor.BLUE);

      Game.choiceDeal();

      System.out.println("Player initial bet: " + Game.player.bet);
      System.out.println("Player hand: " + Game.player.hand.toString());
      System.out.println("Dealer hand: " + Game.dealer.hand.toString());

      while (!Game.roundOver) {
        System.out.print("Choice (hit, dou, sur, sta, quit): ");
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
          case "quit":
            playing = false;
            break;
          default:
            System.out.println("Invalid action, try again.");
            break;
        }

        if (!playing)
          break;

        System.out.println("Current player hand: " + Game.player.hand.toString());
        System.out.println("Current dealer hand: " + Game.dealer.hand.toString());
      }

      System.out.println("Game over.");
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

    System.out.println("Quitting.");

    scanner.close();
  }
}
