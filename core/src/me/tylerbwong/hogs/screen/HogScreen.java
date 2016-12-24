package me.tylerbwong.hogs.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import me.tylerbwong.hogs.HogGame;
import me.tylerbwong.hogs.util.InputHandler;
import me.tylerbwong.hogs.world.HogRenderer;
import me.tylerbwong.hogs.world.HogWorld;

/**
 * @author Tyler Wong
 */

public class HogScreen implements Screen {
   private HogWorld world;
   private HogRenderer renderer;

   private float runTime = 0;

   public HogScreen(HogGame game) {
      float screenWidth = Gdx.graphics.getWidth();
      float screenHeight = Gdx.graphics.getHeight();
      float gameWidth = 136;
      float gameHeight = screenHeight / (screenWidth / gameWidth);
      int midPointY = (int) (gameHeight / 2);

      world = new HogWorld(game, midPointY);
      renderer = new HogRenderer(world, (int) gameHeight, midPointY);

      Gdx.input.setInputProcessor(new InputHandler(world));
   }

   @Override
   public void render(float delta) {
      runTime += delta;
      world.update(delta);
      renderer.render(runTime);
   }

   @Override
   public void resize(int width, int height) {
      Gdx.app.log("GameScreen", "resizing");
   }

   @Override
   public void show() {
      Gdx.app.log("GameScreen", "show called");
   }

   @Override
   public void hide() {
      Gdx.app.log("GameScreen", "hide called");
   }

   @Override
   public void pause() {
      Gdx.app.log("GameScreen", "pause called");
   }

   @Override
   public void resume() {
      Gdx.app.log("GameScreen", "resume called");
   }

   @Override
   public void dispose() {
      // Leave blank
   }
}
