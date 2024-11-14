package model;

import java.io.Serializable;
import java.util.List;

// this record is used to share the game state with its observers.
// note that it is NOT used for saving/loading the game
public record GameState(
    List<Integer> dealerCards,
    int dealerPoints,
    List<Integer> playerCards,
    List<Integer> playerSplitCards,
    int playerPoints,
    int playerSplitPoints,
    boolean splitPlaying,
    double balance,
    int totalBet,
    int bet,
    int splitBet) implements Serializable {
}
