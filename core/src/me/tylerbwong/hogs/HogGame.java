package me.tylerbwong.hogs;

import com.badlogic.gdx.Game;

import me.tylerbwong.hogs.screen.HogScreen;
import me.tylerbwong.hogs.services.PlayServices;
import me.tylerbwong.hogs.util.AssetLoader;

public class HogGame extends Game {
   public static PlayServices playServices;

   public HogGame(PlayServices playServices) {
      this.playServices = playServices;
   }

   @Override
   public void create() {
      AssetLoader.load();
      setScreen(new HogScreen(this));
   }

   @Override
   public void dispose() {
      super.dispose();
      AssetLoader.dispose();
   }
}
