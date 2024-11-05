package model;

import java.util.List;

// this record is used as an argument to pass the game state to game observers
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
