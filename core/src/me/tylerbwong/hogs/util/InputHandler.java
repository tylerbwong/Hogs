package me.tylerbwong.hogs.util;

import com.badlogic.gdx.InputProcessor;

import me.tylerbwong.hogs.model.Hog;
import me.tylerbwong.hogs.world.HogWorld;

/**
 * @author Tyler Wong
 */

public class InputHandler implements InputProcessor {
   private HogWorld world;
   private Hog hog;

   public InputHandler(HogWorld world) {
      this.world = world;
      this.hog = world.getHog();
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {
      if (world.isReady()) {
         world.start();
      }

      hog.onClick();

      if (world.isGameOver() || world.isHighScore()) {
         // Reset all variables, go to GameState.READ
         world.restart();
      }

      return true;
   }

   @Override
   public boolean keyDown(int keycode) {
      return false;
   }

   @Override
   public boolean keyUp(int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped(char character) {
      return false;
   }

   @Override
   public boolean touchUp(int screenX, int screenY, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchDragged(int screenX, int screenY, int pointer) {
      return false;
   }

   @Override
   public boolean mouseMoved(int screenX, int screenY) {
      return false;
   }

   @Override
   public boolean scrolled(int amount) {
      return false;
   }
}
