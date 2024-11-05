package model;

import java.util.List;

// this record is used to share the game state with its observers
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
    int splitBet) {
}
