package model;

import static org.junit.Assert.*;  

import org.junit.Test;  

public class CardTest {

    @Test
    public void TesteValorAs() {
        Card carta = new Card(Suit.CLUBS, Rank.ACE);
        assertEquals(1, carta.getValue());
    }

    @Test
    public void TesteValor5() {
        Card carta = new Card(Suit.DIAMONDS, Rank.FIVE);
        assertEquals(5, carta.getValue());
    }

    @Test
    public void TesteValor10() {
        Card carta = new Card(Suit.HEARTS, Rank.TEN);
        assertEquals(10, carta.getValue());
    }

    @Test
    public void TesteValorRainha() {
        Card carta = new Card(Suit.SPADES, Rank.QUEEN);
        assertEquals(10, carta.getValue());
    }

    @Test
    public void TesteValorValete() {
        Card carta = new Card(Suit.SPADES, Rank.JACK);
        assertEquals(10, carta.getValue());
    }

    @Test
    public void TesteValorIgual() {
        Card carta1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card carta2 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        assertTrue(carta1.equals(carta2));
    }

    @Test
    public void TesteValorDiferenteNaipe() {
        Card carta1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card carta2 = new Card(Suit.HEARTS, Rank.SEVEN);
        assertFalse(carta1.equals(carta2));
    }

    @Test
    public void TesteValorDiferenteValor() {
        Card carta1 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card carta2 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        assertFalse(carta1.equals(carta2));
    }
}


