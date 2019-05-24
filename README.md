# GameCenter
A game center supporting three games
Group Project for CSC207 "Software Design"
# GROUP 0642
## URL to clone
https://github.com/DeanLiu1999/GameCenter
## Steps of setting up
1.Open Android Studio

2.Click on "Check out project from Version Control"

3.Choose "Git"

4.Paste the URL above to "URL" box and choose the directory where you want to do your work

5.Click on "Clone"

6.Click "Yes" for "Checkout From Version Control"

7.Select "Import project from external model" - "Android Gradle" and click "finish"

8.Click "OK" for "Gradle SYNC"

9.Click "No" for "Add File to Git"

10."File" -> "Open" -> Phase2 -> GameCenter -> "Open" -> "This Window"

11.Click "OK" for "Sync Android SDK"

12.Wait for Android Studio to finish building

Note: The app can run on virtual devices such as "Pixel 2 API 27" or "Nexus 5X API 27"

## Brief description
We have three games for the user to explore: SlidingTile, BlackJack and Hangman.

The main code for this project can be found in Phase2/GameCenter/app/src/main/java/group0642/csc207/fall18/gamecenter.

Some of the starter code are given by our professsor.

Raymond Lu: Java implementation for hangman, infinite mode for hangman, unit test for Word
         check unit test for all other classes to ensure code coverage

Dean Liu: Modify BoardManager to avoid unsolvable board, java implementation and images for blackjack, 
      organize Phase 1 code in bottom classes, unit test for Tile, StateManger, DeckManager, Card,
      java implementation and unit test for player against monster mode in hangman
      
Cheng Lu: Enhance Scoreboard class to support two new games, avoid abuse of "static" keyword
       wager, display, animation, undo and save for blackjack, 
       unit test for Scoreboard, BankManager, Android test for whole program if possible
       
Yilun Li: Enhance buttons, displays, game center and user accounts for Phase 2, 
       display and save for hangman game, timed mode for hangman if possible
       button and display for player against monster mode in hangman
       
Ryan Zhang: Eliminate code smell from Phase 1, unit test for BoardManager, Board
      improve the structure of activities (separation guied by design principles) in Phase 1 to 
      enhance code coverage

## Images citation
club_a.png, club_2.png, club_3.png, club_4.png, club_5.png, club_6.png, club_7.png, club_8.png,
club_9.png, club_10.png, club_j.png, club_j.png, club_q.png
are from http://www.4399.com/

level1.jpg, level2.jpg, level3.jpg, level4.jpg, level5.jpg, level6.jpg
are from kingdom rush

cardback.png is from Hearthstone

## Code coverage
class BlackjackGameActivity, class CustomAdapter, class EndingScore, class GameActivity,
class GestureDetectGridView, class HangmanActivity, class HangmanBattle, class HangmanModes,
class LoadActivity, class LoginActivity, class MovementController, class PasswordReset,
class Preface, class Register, class ScoreDisplay, class SaveManager, class ScorePerGame,
class SlidingTileSetting,class StartingActivity are not included in the unit tests either because
they are view classes or they can not be tested.
