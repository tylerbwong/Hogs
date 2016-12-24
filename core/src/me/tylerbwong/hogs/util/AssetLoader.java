package me.tylerbwong.hogs.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Tyler Wong
 */

public class AssetLoader {
   public static Texture texture;
   public static TextureRegion bg, grass;

   public static Animation hogAnimation;
   public static TextureRegion hog, hogDown, hogUp;
   public static TextureRegion pipeUp, pipeDown, bar;

   public static Sound flap, coin, dead;

   public static BitmapFont font, shadow;

   public static Preferences prefs;

   public static void load() {
      texture = new Texture(Gdx.files.internal("texture.png"));
      texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

      bg = new TextureRegion(texture, 0, 0, 138, 43);
      bg.flip(false, true);

      grass = new TextureRegion(texture, 0, 43, 165, 11);
      grass.flip(false, true);

      hogDown = new TextureRegion(texture, 138, 0, 17, 15);
      hogDown.flip(false, true);

      hog = new TextureRegion(texture, 155, 0, 17, 15);
      hog.flip(false, true);

      hogUp = new TextureRegion(texture, 172, 0, 17, 15);
      hogUp.flip(false, true);

      TextureRegion[] hogs = {hogDown, hog, hogUp};
      hogAnimation = new Animation(0.06f, hogs);
      hogAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

      pipeUp = new TextureRegion(texture, 194, 0, 24, 14);
      // Create by flipping existing pipeUp
      pipeDown = new TextureRegion(pipeUp);
      pipeDown.flip(false, true);

      bar = new TextureRegion(texture, 138, 20, 22, 3);
      bar.flip(false, true);

      font = new BitmapFont(Gdx.files.internal("text.fnt"));
      font.getData().setScale(.25f, -.25f);
      shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
      shadow.getData().setScale(.25f, -.25f);

      flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
      coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
      dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));

      // Create (or retrieve existing) preferences file
      prefs = Gdx.app.getPreferences("Hog");

      // Provide default high score of 0
      if (!prefs.contains("highScore")) {
         prefs.putInteger("highScore", 0);
      }
   }

   // Receives an integer and maps it to the String highScore in prefs
   public static void setHighScore(int val) {
      prefs.putInteger("highScore", val);
      prefs.flush();
   }

   // Retrieves the current high score
   public static int getHighScore() {
      return prefs.getInteger("highScore");
   }

   public static void dispose() {
      // We must dispose of the texture when we are finished.
      texture.dispose();
      dead.dispose();
      flap.dispose();
      coin.dispose();
      font.dispose();
      shadow.dispose();
   }
}
