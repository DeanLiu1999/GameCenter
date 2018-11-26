# GROUP 0642
## URL to clone
https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0642
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
a new game. The user can save the current game state and load it later. Moreover, the user can undo
in order to get the wagers back when his bank value is less than 1000. The user can checkout to
record their current bank value as the score in ScoreBoard.

Hangman has two modes: the standard mode and the infinite mode. In standard mode the user get 10 
lives and the regular hangman rule applied. In infinite mode the user can keep guessing the next
word as long as his number of lives is positive, and his score will be the number of words guessed
correctly.