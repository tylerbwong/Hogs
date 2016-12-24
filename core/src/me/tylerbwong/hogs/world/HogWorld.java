package me.tylerbwong.hogs.world;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import me.tylerbwong.hogs.HogGame;
import me.tylerbwong.hogs.model.Hog;
import me.tylerbwong.hogs.util.AssetLoader;
import me.tylerbwong.hogs.util.HogState;
import me.tylerbwong.hogs.util.ScrollHandler;

/**
 * @author Tyler Wong
 */

public class HogWorld {
   private Hog hog;
   private HogGame game;
   private ScrollHandler scroller;
   private Rectangle ground;

   private int score = 0;
   private HogState currentState;

   public int midPointY;

   public HogWorld(HogGame game, int midPointY) {
      this.game = game;
      this.midPointY = midPointY;
      hog = new Hog(33, midPointY - 5, 17, 12);
      scroller = new ScrollHandler(this, midPointY + 66);
      ground = new Rectangle(0, midPointY + 66, 136, 11);
      currentState = HogState.READY;
   }

   public void updateRunning(float delta) {
      hog.update(delta);
      scroller.update(delta);

      // Add a delta cap so that if our game takes too long
      // to updateRunning, we will not break our collision detection.

      if (delta > .15f) {
         delta = .15f;
      }

      hog.update(delta);
      scroller.update(delta);

      if (scroller.collides(hog) && hog.isAlive()) {
         scroller.stop();
         hog.die();
         AssetLoader.dead.play();
      }

      if (Intersector.overlaps(hog.getBoundingCircle(), ground)) {
         scroller.stop();
         hog.die();
         hog.decelerate();
         currentState = HogState.GAMEOVER;
         AssetLoader.dead.play();

         if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
            game.playServices.submitScore(score);
            currentState = HogState.HIGHSCORE;
         }
      }

      if (score >= 10 && score < 20) {
         game.playServices.unlockAchievement(10);
      }
      else if (score >= 20 && score < 50) {
         game.playServices.unlockAchievement(20);
      }
      else if (score >= 50 && score < 100) {
         game.playServices.unlockAchievement(50);
      }
      else if (score >= 100 && score < 1000) {
         game.playServices.unlockAchievement(100);
      }
      else if (score >= 1000) {
         game.playServices.unlockAchievement(1000);
      }
   }

   public void update(float delta) {
      switch (currentState) {
         case READY:
            updateReady(delta);
            break;

         case RUNNING:
            updateRunning(delta);
            break;
         default:
            break;
      }
   }

   private void updateReady(float delta) {
      // Do nothing for now
   }

   public void restart() {
      currentState = HogState.READY;
      score = 0;
      hog.onRestart(midPointY - 5);
      scroller.onRestart();
      currentState = HogState.READY;
   }

   public boolean isReady() {
      return currentState == HogState.READY;
   }

   public boolean isGameOver() {
      return currentState == HogState.GAMEOVER;
   }

   public boolean isHighScore() {
      return currentState == HogState.HIGHSCORE;
   }

   public void start() {
      currentState = HogState.RUNNING;
   }

   public Hog getHog() {
      return hog;
   }

   public ScrollHandler getScroller() {
      return scroller;
   }

   public int getScore() {
      return score;
   }

   public void addScore(int increment) {
      score += increment;
   }
}
