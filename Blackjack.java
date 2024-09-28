package com.txtlearn.blackjack01;
import java.util.*;

public class Blackjack {
   public static void main(String[] args) {
	  
      Scanner s = new Scanner(System.in);
      // Initialize various flags for tracking the game state
      boolean hasExited = false;
      boolean invalidInput = false;
      boolean gameOver = false;
      boolean isRich = false;
      boolean playerIsLeaving = false;
      
      // Create the necessary decks: playing deck, player's deck, and dealer's deck
      Deck playingDeck = new Deck();
      Deck playerDeck = new Deck();
      Deck dealerDeck = new Deck();
      
      double playerMoney;  // To store player's total money
      int keepPlaying = 0; // To track if the player wants to keep playing or exit
      int hitOrStand = 0;  // To track player's decision to hit or stand during the game

      // Initialize the playing deck by creating a full deck and shuffling it
      playingDeck.createFullDeck();
      playingDeck.shuffle();

      // Introduction message
      System.out.println("Welcome to Blackjack!\nThe goal of the game is to get closer than the dealer to the number 21 without going over.\n");
      System.out.println("How much would you like to get in chips? (Maximum is $10,000)");

      // Input loop to capture the player's initial money amount with validation
      while (true) {
          try {
             playerMoney = s.nextDouble();  
             
             // Check if the input amount is valid (between 0 and 10,000)
             if (playerMoney <= 0.0 || playerMoney > 10000.0) {
                System.out.println("That is not a valid number. Please enter a number between 0 and 10,000.00!");
             } else {
                break; // Exit loop once a valid input is received
             }
          } catch (InputMismatchException e) {
             System.out.println("Invalid input! Please enter a numeric value.");
             s.next();  // Clear invalid input
          }
       }

      // Store the initial amount of money the player starts with
      double initialMoney = playerMoney; 

      // Main game loop; continue as long as the player has money and the game isn't over
      while(playerMoney > 0.0D && !gameOver) {
         
    	 // Ask the player how much they want to bet
         System.out.println("You have $" + playerMoney + ", how much would you like to bet? ");
         double playerBet;
         while (true) {
             try {
                playerBet = s.nextDouble();  // Get the player's bet amount
                
                // Ensure the bet is not greater than what the player currently has
                if (playerBet > playerMoney) {
                   System.out.println("You cannot bet more than you have!");
                   System.out.println("Please enter a number between 0 and " + playerMoney + "!");
                } else if (playerBet < 0) {
                	System.out.println("You cannot bet below $0!");
                    System.out.println("Please enter a number between 0 and " + playerMoney + "!");
                } else {
                   break;  // Exit loop with a valid bet amount
                }
             } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                s.next(); 
             }
          }

         // If the bet is valid, start the hand
         if (playerBet < playerMoney) {
            System.out.println("Hand is now starting.");
         }

         boolean endOfRound = false;  // Track if the current hand has ended
         
         // Player and dealer draw initial cards
         playerDeck.draw(playingDeck);  // Player draws 2 cards
         playerDeck.draw(playingDeck);  
         dealerDeck.draw(playingDeck);  // Dealer draws 1 card

         // Player's turn: loop until they decide to stand or bust
         do {
            System.out.println("Your hand:");
            System.out.println(playerDeck.toString());
            System.out.println("Your hand is valued at: " + playerDeck.cardsValue());
            
            // Check if the player has busted (i.e., hand value > 21)
            if (playerDeck.cardsValue() > 21) {
               System.out.println("Bust. your hand is valued at " + playerDeck.cardsValue());
               playerMoney -= playerBet;  
               endOfRound = true;
               break; // End player's turn if they bust
            }

            // Show the dealer's first card (other card is hidden)
            System.out.println("Dealer's Hand: " + dealerDeck.getCard(0).toString() + " and [Hidden]");
            System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());

            // If the player has blackjack (21), automatically end their turn
            if (playerDeck.cardsValue() == 21) {
            	System.out.println("WINNER WINNER, CHICKEN DINNER! YOU JUST GOT A BLACKJACK!!");
               break;
            }

            // Ask if the player wants to hit (draw another card) or stand (keep current hand)
            
            while (true) {
            	System.out.println("Would you like to hit (1) or stand (2)?");
                try {
                   hitOrStand = s.nextInt();  
                   if (hitOrStand == 1) {
                	   playerDeck.draw(playingDeck); // Player draws a card if they choose to hit
                       System.out.println("You draw a " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
                       break;
                   } else if (hitOrStand == 2) {
                	   break; // Player stands
                   }else {
                	   System.out.println("Invalid input! Please enter either 1 to hit or 2 to stand.");
                   }
                } catch (InputMismatchException e) {
                   System.out.println("Invalid input! Please enter either 1 to hit or 2 to stand.");
                   s.next(); 
                }
            }
         
         } while(hitOrStand != 2); // Continue if the player hasn't stood yet
         
         // Dealer's turn
         dealerDeck.draw(playingDeck);  // Dealer reveals second card
         System.out.println("Dealer's cards: " + dealerDeck.toString());
         
         // Check if the dealer has won immediately
         if (dealerDeck.cardsValue() > playerDeck.cardsValue() && !endOfRound) {
            System.out.println("Dealer Wins!");
            playerMoney -= playerBet; 
            endOfRound = true;
         }

         // Dealer must draw until their hand's value is at least 17
         while(dealerDeck.cardsValue() < 17 && !endOfRound) {
            dealerDeck.draw(playingDeck);
            System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
         }
         
         // Dealer busts, player wins
         System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());
         if (dealerDeck.cardsValue() > 21 && !endOfRound) {
            System.out.println("Dealer busts! You win!");
            playerMoney += playerBet; 
            endOfRound = true;
         }

         // Check if it's a tie (push)
         if (playerDeck.cardsValue() == dealerDeck.cardsValue() && !endOfRound) {
            System.out.println("Push");
            endOfRound = true;
         }

       //If player got blackjack (card value = 21), player wins 1.5x their bet.
         if (playerDeck.cardsValue() == 21 && dealerDeck.cardsValue() != 21 && !gameOver) {
        	 playerMoney += 1.5*playerBet; 
             endOfRound = true;
         }
         
         // Player wins if their hand is better than the dealer's
         if (playerDeck.cardsValue() > dealerDeck.cardsValue() && !endOfRound) {
            System.out.println("You win the hand!");
            playerMoney += playerBet; 
            endOfRound = true;
         } else if (!endOfRound) {
            System.out.println("You lose the hand.");
            playerMoney -= playerBet;
            endOfRound = true;
         }

         // Reset decks for the next hand
         playerDeck.moveAlltoDeck(playingDeck);
         dealerDeck.moveAlltoDeck(playingDeck);
         System.out.println("End of Hand.\n");
         System.out.println("You currently have $" + playerMoney + ".");

         // Check if the player is broke
         if (playerMoney == 0.0D) {
            break;
         }

         // Check if the player is too rich and must leave (Casino thinks player is winning too much)
         if (playerMoney > 100000.0D) {
            System.out.println("Please leave the casino or you will be forced to leave.");
            isRich = true;
            hasExited = false;
            break;
         }

         // Ask if the player wants to keep playing
         while (true) {
         	System.out.println("Would you like to stay (1) or leave (2)?");
             try {
                keepPlaying = s.nextInt();  
                if (keepPlaying == 1) {
                    break;
                } else if (keepPlaying == 2) {
             	   break; // Player stands
                }else {
             	   System.out.println("Invalid input! Please enter either 1 to stay or 2 to leave.");
                }
             } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter either 1 to stay or 2 to leave.");
                s.next(); 
             }
         } 

         // Move to the next hand or end the game
         if (keepPlaying == 1) {
            System.out.println("Next hand starting now.");
         }

         if (playerIsLeaving) {
            break;
         }
      }

      // Game over messages based on the game outcome
      if (!invalidInput && !isRich) {
         System.out.println("Game Over. You are broke.");
      }

      // Final message when the player exits
      if (hasExited && !isRich) {
         if (playerMoney < initialMoney) {
            System.out.println("You have lost: $" + (initialMoney - playerMoney) + "!");
            System.out.println("Thank you for playing! We hope to see you soon!");
         } else {
            System.out.println("You have won: $" + (playerMoney - initialMoney) + "!");
            System.out.println("Thank you for playing! We hope to see you soon!");
         }
      }

   }
}
