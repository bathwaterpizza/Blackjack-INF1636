package model;

import java.util.Scanner;

public class MainTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      boolean success;
      System.out.println("\n--- NEW GAME ---\n");

      Game.choiceClear();

      success = Game.player.incrementBet(ChipColor.GOLD);
      if (!success) {
        scanner.close();
        System.exit(0);
      }

      Game.choiceDeal();

      System.out.println("Player initial bet: " + Game.player.bet);
      System.out.println("Initial player hand: " + Game.player.hand.toString());
      System.out.println("Initial dealer hand: " + Game.dealer.hand.toString());
      System.out.println();

      while (!Game.roundOver) {
        System.out.print("Choice (hit, dou, sur, sta, spl, exi): ");
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
          case "spl":
            System.out.println("You chose to split.");
            Game.choiceSplit();
            break;
          default:
            System.out.println("Invalid action, try again.");
            break;
        }

        System.out.println();
        System.out.println("Current player hand: " + Game.player.hand.toString());
        if (Game.split) {
          System.out.println("Current split hand: " + Game.player.splitHand.toString());
        }
        System.out.println("Current dealer hand: " + Game.dealer.hand.toString());

        if (Game.split) {
          if (Game.splitPlaying) {
            System.out.println("Now playing split hand.");
          } else {
            System.out.println("Now playing main hand.");
          }
        }
      }

      System.out.println();
      System.out.println("Round over.");
      System.out.println("Final player hand: " + Game.player.hand.toString());
      if (Game.split) {
        System.out.println("Final split hand: " + Game.player.splitHand.toString());
      }
      System.out.println("Final dealer hand: " + Game.dealer.hand.toString());

      if (Game.won) {
        System.out.println("Main hand: You won!");
      } else if (Game.tied) {
        System.out.println("Main hand: It's a tie!");
      } else {
        System.out.println("Main hand: You lost.");
      }

      if (Game.split) {
        if (Game.splitWon) {
          System.out.println("Split hand: You won!");
        } else if (Game.splitTied) {
          System.out.println("Split hand: It's a tie!");
        } else {
          System.out.println("Split hand: You lost.");
        }
      }

      System.out.println("Player balance: " + Game.player.balance);
    }
  }
}
