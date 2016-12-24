package me.tylerbwong.hogs.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.tylerbwong.hogs.model.Grass;
import me.tylerbwong.hogs.model.Hog;
import me.tylerbwong.hogs.model.Pipe;
import me.tylerbwong.hogs.util.AssetLoader;
import me.tylerbwong.hogs.util.ScrollHandler;

/**
 * @author Tyler Wong
 */

public class HogRenderer {
   private Hog hog;
   private TextureRegion bg, grass;
   private Animation hogAnimation;
   private TextureRegion hogMid, hogDown, hogUp;
   private TextureRegion pipeUp, pipeDown, bar;

   private HogWorld world;
   private OrthographicCamera camera;
   private ShapeRenderer shapeRenderer;

   private SpriteBatch batcher;
   private int midPointY;
   private int gameHeight;

   private ScrollHandler scroller;
   private Grass frontGrass, backGrass;
   private Pipe pipe1, pipe2, pipe3;

   public HogRenderer(HogWorld world, int gameHeight, int midPointY) {
      this.world = world;

      this.gameHeight = gameHeight;
      this.midPointY = midPointY;

      camera = new OrthographicCamera();
      camera.setToOrtho(true, 136, 204);

      batcher = new SpriteBatch();
      batcher.setProjectionMatrix(camera.combined);

      shapeRenderer = new ShapeRenderer();
      shapeRenderer.setProjectionMatrix(camera.combined);

      initGameObjects();
      initAssets();
   }

   private void initGameObjects() {
      hog = world.getHog();
      scroller = world.getScroller();
      frontGrass = scroller.getFrontGrass();
      backGrass = scroller.getBackGrass();
      pipe1 = scroller.getPipe1();
      pipe2 = scroller.getPipe2();
      pipe3 = scroller.getPipe3();
   }

   private void initAssets() {
      bg = AssetLoader.bg;
      grass = AssetLoader.grass;
      hogAnimation = AssetLoader.hogAnimation;
      hogMid = AssetLoader.hog;
      hogDown = AssetLoader.hogDown;
      hogUp = AssetLoader.hogUp;
      pipeUp = AssetLoader.pipeUp;
      pipeDown = AssetLoader.pipeDown;
      bar = AssetLoader.bar;
   }

   private void drawGrass() {
      // Draw the grass
      batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
            frontGrass.getWidth(), frontGrass.getHeight());
      batcher.draw(grass, backGrass.getX(), backGrass.getY(),
            backGrass.getWidth(), backGrass.getHeight());
   }

   private void drawPipeTops() {
      // Temporary code! Sorry about the mess :)
      // We will fix this when we finish the Pipe class.

      batcher.draw(pipeUp, pipe1.getX() - 1,
            pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
      batcher.draw(pipeDown, pipe1.getX() - 1,
            pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

      batcher.draw(pipeUp, pipe2.getX() - 1,
            pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
      batcher.draw(pipeDown, pipe2.getX() - 1,
            pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

      batcher.draw(pipeUp, pipe3.getX() - 1,
            pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
      batcher.draw(pipeDown, pipe3.getX() - 1,
            pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
   }

   private void drawPipes() {
      // Temporary code! Sorry about the mess :)
      // We will fix this when we finish the Pipe class.
      batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
            pipe1.getHeight());
      batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
            pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

      batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
            pipe2.getHeight());
      batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
            pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

      batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
            pipe3.getHeight());
      batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
            pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
   }

   public void render(float runTime) {
      // Fill the entire screen with black, to prevent potential flickering.
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      // Begin ShapeRenderer
      shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

      // Draw Background color
      shapeRenderer.setColor(207 / 255.0f, 255 / 255.0f, 235 / 255.0f, 1);
      shapeRenderer.rect(0, 0, 136, midPointY + 66);

      // Draw Grass
      shapeRenderer.setColor(126 / 255.0f, 211 / 255.0f, 23 / 255.0f, 1);
      shapeRenderer.rect(0, midPointY + 66, 136, 11);

      // Draw Dirt
      shapeRenderer.setColor(148 / 255.0f, 79 / 255.0f, 28 / 255.0f, 1);
      shapeRenderer.rect(0, midPointY + 77, 136, 52);

      // End ShapeRenderer
      shapeRenderer.end();

      // Begin SpriteBatch
      batcher.begin();

      // Disable transparency
      // This is good for performance when drawing images that do not require
      // transparency.
      batcher.disableBlending();
      batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);

      drawGrass();
      drawPipes();

      // The hog needs transparency, so we enable that again.
      batcher.enableBlending();

      drawPipeTops();

      if (hog.shouldntFlap()) {
         batcher.draw(hogMid, hog.getX(), hog.getY(),
               hog.getWidth() / 2.0f, hog.getHeight() / 2.0f,
               hog.getWidth(), hog.getHeight(), 1, 1, hog.getRotation());
      }
      else {
         batcher.draw((TextureRegion) hogAnimation.getKeyFrame(runTime), hog.getX(),
               hog.getY(), hog.getWidth() / 2.0f,
               hog.getHeight() / 2.0f, hog.getWidth(), hog.getHeight(),
               1, 1, hog.getRotation());
      }

      if (world.isReady()) {
         // Draw shadow first
         AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2)
               - (42), 76);
         // Draw text
         AssetLoader.font.draw(batcher, "Touch me", (136 / 2)
               - (42 - 1), 75);
      }
      else {
         if (world.isGameOver() || world.isHighScore()) {
            if (world.isGameOver()) {
               AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
               AssetLoader.font.draw(batcher, "Game Over", 24, 55);

               AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
               AssetLoader.font.draw(batcher, "High Score:", 22, 105);

               String highScore = AssetLoader.getHighScore() + "";

               // Draw shadow first
               AssetLoader.shadow.draw(batcher, highScore, (136 / 2)
                     - (3 * highScore.length()), 128);
               // Draw text
               AssetLoader.font.draw(batcher, highScore, (136 / 2)
                     - (3 * highScore.length() - 1), 127);
            }
            else {
               AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
               AssetLoader.font.draw(batcher, "High Score!", 18, 55);
            }

            AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
            AssetLoader.font.draw(batcher, "Try again?", 24, 75);

            // Convert integer into String
            String score = world.getScore() + "";

            // Draw shadow first
            AssetLoader.shadow.draw(batcher, score,
                  (136 / 2) - (3 * score.length()), 12);
            // Draw text
            AssetLoader.font.draw(batcher, score,
                  (136 / 2) - (3 * score.length() - 1), 11);

         }

         // Convert integer into String
         String score = world.getScore() + "";

         // Draw shadow first
         AssetLoader.shadow.draw(batcher, "" + world.getScore(), (136 / 2)
               - (3 * score.length()), 12);
         // Draw text
         AssetLoader.font.draw(batcher, "" + world.getScore(), (136 / 2)
               - (3 * score.length() - 1), 11);
      }

      batcher.end();
   }
}
