package model;

import java.util.Scanner;

public class ConsoleGameTest {
  public static void run() {
    Game game = Game.getAPI();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      boolean success;
      System.out.println("\n-------- NEW ROUND --------\n");

      game.choiceClear();

      System.out.print("Enter bet amount: ");
      int betAmount = scanner.nextInt();
      scanner.nextLine(); // consume newline

      success = game.player.incrementBet(betAmount);
      if (!success) {
        System.out.println("No money to bet. Exiting.");
        scanner.close();
        System.exit(0);
      }

      success = game.choiceDeal();
      if (!success) {
        System.out.println("Deal failed. Exiting.");
        scanner.close();
        System.exit(0);
      }

      System.out.println("Initial player hand: " + game.player.hand.toString());
      System.out.println("Initial dealer hand: " + game.dealer.hand.toString());
      System.out.println();

      while (!game.roundOver) {
        System.out.print("Choice (hit, double, surrender, stand, split, exit): ");
        String action = scanner.nextLine().trim().toLowerCase();

        switch (action) {
          case "hit":
            System.out.println("You chose to hit.");
            game.choiceHit();
            break;
          case "double":
            System.out.println("You chose to double.");
            success = game.choiceDouble();

            if (success && !game.roundOver) {
              System.out.println("Main bet is now " + game.player.bet + ".");
              if (game.split) {
                System.out.println("Split bet is now " + game.player.splitBet + ".");
              }
            }
            break;
          case "surrender":
            System.out.println("You chose to surrender.");
            game.choiceSurrender();
            break;
          case "stand":
            System.out.println("You chose to stand.");
            game.choiceStand();
            break;
          case "exit":
            scanner.close();
            game.choiceExit();
          case "split":
            System.out.println("You chose to split.");
            success = game.choiceSplit();

            if (success && !game.roundOver) {
              System.out.println("Main bet is now " + game.player.bet + ".");
              if (game.split) {
                System.out.println("Split bet is now " + game.player.splitBet + ".");
              }
            }
            break;
          default:
            System.out.println("Invalid action, try again.");
            break;
        }

        if (!game.roundOver) {
          System.out.println();
          System.out.println("Current player hand: " + game.player.hand.toString());
          if (game.split) {
            System.out.println("Current split hand: " + game.player.splitHand.toString());
          }
          System.out.println("Current dealer hand: " + game.dealer.hand.toString());
        }

        if (game.split && !game.roundOver) {
          if (game.splitPlaying) {
            System.out.println("Now playing SPLIT hand!");
          } else {
            System.out.println("Now playing MAIN hand!");
          }
        }
      }

      System.out.println();
      System.out.println("Round over.");
      System.out.println("Final player hand: " + game.player.hand.toString());
      if (game.split) {
        System.out.println("Final split hand: " + game.player.splitHand.toString());
      }
      System.out.println("Final dealer hand: " + game.dealer.hand.toString());

      if (game.won) {
        System.out.println("Main hand: You won!");
      } else if (game.tied) {
        System.out.println("Main hand: It's a tie!");
      } else {
        System.out.println("Main hand: You lost.");
      }

      if (game.split) {
        if (game.splitWon) {
          System.out.println("Split hand: You won!");
        } else if (game.splitTied) {
          System.out.println("Split hand: It's a tie!");
        } else {
          System.out.println("Split hand: You lost.");
        }
      }

      System.out.println();
      System.out.println("Final player balance: " + game.player.balance);
    }
  }
}
