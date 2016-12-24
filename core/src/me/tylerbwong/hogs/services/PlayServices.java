package me.tylerbwong.hogs.services;

/**
 * @author Tyler Wong
 */

public interface PlayServices {
   void signIn();
   void signOut();
   void rateGame();
   void unlockAchievement(int achievement);
   void submitScore(int highScore);
   void showAchievement();
   void showScore();
   boolean isSignedIn();
}
