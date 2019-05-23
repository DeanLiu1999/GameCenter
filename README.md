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

## Brief description to functionality
We have three games now: SlidingTile, BlackJack and Hangman.

SlidingTile is the same as Phase 1, except we now avoid unsolvable boards when user starts new game.

We implement BlackJack using the regular rule. The user can add wagers and then press Deal to start
a new game. The user can save the current game state and load it later.The game will also be
automatically saved at save points and at the moment that the app is closed during the game.
Moreover, the user can undo in order to get the wagers back before the user make any move after
dealing. The user can cash out to record their current bank value as the score in ScoreBoard.

Hangman has two modes: the standard mode and the infinite mode. In standard mode the user get 10 
lives and the regular hangman rule applied. In infinite mode the user can keep guessing the next
word as long as his number of lives is positive, and his score will be the number of words guessed
correctly.

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
