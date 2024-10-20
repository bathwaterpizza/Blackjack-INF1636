package model;

import java.util.Scanner;

public class MainTest {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      boolean success;
      System.out.println("\n-------- NEW ROUND --------\n");

      Game.choiceClear();

      System.out.print("Enter bet amount: ");
      int betAmount = scanner.nextInt();
      scanner.nextLine(); // consume newline

      success = Game.player.incrementBet(betAmount);
      if (!success) {
        System.out.print("No money to bet. Exiting.");
        scanner.close();
        System.exit(0);
      }

      success = Game.choiceDeal();
      if (!success) {
        System.out.print("Deal failed. Exiting.");
        scanner.close();
        System.exit(0);
      }

      System.out.println("Initial player hand: " + Game.player.hand.toString());
      System.out.println("Initial dealer hand: " + Game.dealer.hand.toString());
      System.out.println();

      while (!Game.roundOver) {
        System.out.print("Choice (hit, double, surrender, stand, split, exit): ");
        String action = scanner.nextLine().trim().toLowerCase();

        switch (action) {
          case "hit":
            System.out.println("You chose to hit.");
            Game.choiceHit();
            break;
          case "double":
            System.out.println("You chose to double.");
            Game.choiceDouble();

            System.out.println("Main bet is now " + Game.player.bet + ".");
            if (Game.split) {
              System.out.println("Split bet is now " + Game.player.splitBet + ".");
            }
            break;
          case "surrender":
            System.out.println("You chose to surrender.");
            Game.choiceSurrender();
            break;
          case "stand":
            System.out.println("You chose to stand.");
            Game.choiceStand();
            break;
          case "exit":
            scanner.close();
            Game.choiceExit();
          case "split":
            System.out.println("You chose to split.");
            Game.choiceSplit();

            System.out.println("Main bet is now " + Game.player.bet + ".");
            if (Game.split) {
              System.out.println("Split bet is now " + Game.player.splitBet + ".");
            }
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
            System.out.println("Now playing SPLIT hand!");
          } else {
            System.out.println("Now playing MAIN hand!");
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

      System.out.println();
      System.out.println("Final player balance: " + Game.player.balance);
    }
  }
}
