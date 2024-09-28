package com.txtlearn.blackjack01;

public class Card {
   // Each card has a suit (Hearts, Spades, etc.) and a value (Ace, 2, 3, ..., King)
   private Suit suit;
   private Value value;

   // Constructor to create a new card with a specified suit and value
   public Card(Suit suit, Value value) {
      this.value = value;  // Set the value of the card (e.g., Ace, 2, 3,..., King)
      this.suit = suit;    // Set the suit of the card (e.g., Hearts, Spades)
   }

   // Method to return the card as a string, formatted as "Suit-Value"
   public String toString() {
      return this.suit.toString() + "-" + this.value.toString();  // Example: "Hearts-7"
   }

   // Getter method to retrieve the value of the card
   public Value getValue() {
      return this.value;
   }
}
