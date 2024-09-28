package com.txtlearn.blackjack01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Deck {
   // The deck is represented as an ArrayList of cards
   private ArrayList<Card> cards = new ArrayList();
   
   // A synthetic field for handling switch cases based on card values
   private static volatile int[] $SWITCH_TABLE$com$txtlearn$blackjack01$Value;

   // Method to create a full deck of 52 cards by iterating over all suits and values
   public void createFullDeck() {
      // Iterate over each Suit (Spades, Hearts, etc.)
      Suit[] var4;
      int var3 = (var4 = Suit.values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Suit cardSuit = var4[var2];  // Current suit
         
         // Iterate over each Value (Ace, 2, 3,..., King)
         Value[] var8;
         int var7 = (var8 = Value.values()).length;

         for(int var6 = 0; var6 < var7; ++var6) {
            Value cardValue = var8[var6];  // Current card value
            // Add the card with current suit and value to the deck
            this.cards.add(new Card(cardSuit, cardValue));
         }
      }

   }

   // Method to shuffle the deck
   public void shuffle() {
      // Temporary deck to hold shuffled cards
      ArrayList<Card> tmpDeck = new ArrayList();
      Random random = new Random();  // Random generator for shuffling
      int originalSize = this.cards.size();  // Original size of the deck

      // Randomly remove cards from the original deck and add them to the temporary deck
      for(int i = 0; i < originalSize; ++i) {
         int randomCardIndex = random.nextInt(this.cards.size());  // Get a random index
         tmpDeck.add(this.cards.remove(randomCardIndex));  // Remove and add to temporary deck
      }

      // Assign the shuffled cards back to the main deck
      this.cards = tmpDeck;
   }

   // Method to print all cards in the deck as a string
   public String toString() {
      String cardListOutput = "";  // Stores the list of cards

      // Iterate through the deck and append each card to the output string
      Card aCard;
      for(Iterator var3 = this.cards.iterator(); var3.hasNext(); cardListOutput = cardListOutput + "\n" + aCard.toString()) {
         aCard = (Card)var3.next();
      }

      return cardListOutput;  // Return the list of cards as a string
   }

   // Method to remove a card from the deck based on its index
   public void removeCard(int i) {
      this.cards.remove(i);  // Remove card at index `i`
   }

   // Method to add a card to the deck
   public void addCard(Card addCard) {
      this.cards.add(addCard);  // Add the specified card to the deck
   }

   // Method to get a card from the deck at a specific index
   public Card getCard(int i) {
      return this.cards.get(i);  // Return the card at index `i`
   }

   // Method for drawing a card from another deck (usually for player or dealer)
   public void draw(Deck initial) {
      // Draw the top card from the `initial` deck and add it to the current deck
      this.cards.add(initial.getCard(0));
      initial.removeCard(0);  // Remove the card from the `initial` deck
   }

   // Method to get the size of the deck
   public int deckSize() {
      return this.cards.size();  // Return the number of cards in the deck
   }

   // Method to calculate the total value of cards in the deck (handles aces as 1 or 11)
   public int cardsValue() {
      int tv = 0;  // Total value of the cards
      int aces = 0;  // Count of aces in the deck (since aces can be worth 1 or 11)
      Iterator var4 = this.cards.iterator();

      // Iterate over the deck to calculate the value of each card
      while(var4.hasNext()) {
         Card aCard = (Card)var4.next();
         // Switch case to determine the value of each card
         switch($SWITCH_TABLE$com$txtlearn$blackjack01$Value()[aCard.getValue().ordinal()]) {
         case 1:
            tv += 2;  // Cards 2-10 add their face value
            break;
         case 2:
            tv += 3;
            break;
         case 3:
            tv += 4;
            break;
         case 4:
            tv += 5;
            break;
         case 5:
            tv += 6;
            break;
         case 6:
            tv += 7;
            break;
         case 7:
            tv += 8;
            break;
         case 8:
            tv += 9;
            break;
         case 9:
         case 10:
         case 11:
         case 12:
            tv += 10;  // Face cards (Jack, Queen, King) are worth 10
            break;
         case 13:
            ++aces;  // Aces are handled later since their value depends on the total
         }
      }

      // Handle the value of aces
      for(int i = 0; i < aces; ++i) {
         if (tv > 10) {
            ++tv;  // If the total value is more than 10, treat ace as 1
         } else {
            tv += 11;  // Otherwise, treat ace as 11
         }
      }

      return tv;  // Return the total value of the deck
   }

   // Method to move all cards from the current deck to another deck
   public void moveAlltoDeck(Deck moveTo) {
      int thisDeckSize = this.cards.size();  // Get the current size of the deck

      // Move each card to the `moveTo` deck
      for(int i = 0; i < thisDeckSize; ++i) {
         moveTo.addCard(this.getCard(i));
      }

      // Remove all cards from the current deck after moving them
      for(int i = 0; i < thisDeckSize; ++i) {
         this.removeCard(0);  // Remove the top card each time
      }

   }

   // Synthetic method to handle switch cases for card values
   static int[] $SWITCH_TABLE$com$txtlearn$blackjack01$Value() {
      int[] var10000 = $SWITCH_TABLE$com$txtlearn$blackjack01$Value;
      if (var10000 != null) {
         return var10000;  // Return cached value if already initialized
      } else {
         // Create a switch table to map each card value to an integer
         int[] var0 = new int[Value.values().length];

         try {
            var0[Value.ACE.ordinal()] = 13;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[Value.EIGHT.ordinal()] = 7;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[Value.FIVE.ordinal()] = 4;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[Value.FOUR.ordinal()] = 3;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[Value.JACK.ordinal()] = 10;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[Value.KING.ordinal()] = 12;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[Value.NINE.ordinal()] = 8;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[Value.QUEEN.ordinal()] = 11;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[Value.SEVEN.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[Value.SIX.ordinal()] = 5;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Value.TEN.ordinal()] = 9;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Value.THREE.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Value.TWO.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
         }

         // Cache the table for future use
         $SWITCH_TABLE$com$txtlearn$blackjack01$Value = var0;
         return var0;  // Return the switch table
      }
   }
}
