package me.tylerbwong.hogs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

import me.tylerbwong.hogs.services.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices {
   private GameHelper gameHelper;
   private final static int requestCode = 1;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
      initialize(new HogGame(this), config);

      gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
      gameHelper.enableDebugLog(false);

      GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
         @Override
         public void onSignInFailed() {
         }

         @Override
         public void onSignInSucceeded() {

         }
      };

      gameHelper.setup(gameHelperListener);
   }

   @Override
   protected void onStart() {
      super.onStart();
      gameHelper.onStart(this);
   }

   @Override
   protected void onStop() {
      super.onStop();
      gameHelper.onStop();
   }

   @Override
   protected void onResume() {
      super.onResume();
      gameHelper.onStart(this);
   }

   @Override
   protected void onPause() {
      super.onPause();
      gameHelper.onStop();
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      gameHelper.onActivityResult(requestCode, resultCode, data);
   }

   @Override
   public void signIn() {
      try {
         runOnUiThread(new Runnable() {
            @Override
            public void run() {
               gameHelper.beginUserInitiatedSignIn();
            }
         });
      }
      catch (Exception e) {
         Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
      }
   }

   @Override
   public void signOut() {
      try {
         runOnUiThread(new Runnable() {
            @Override
            public void run() {
               gameHelper.signOut();
            }
         });
      }
      catch (Exception e) {
         Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
      }
   }

   @Override
   public void rateGame() {
      String str = "Your PlayStore Link";
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
   }

   @Override
   public void unlockAchievement(int achievement) {
      String key = "";

      switch (achievement) {
         case 10:
            key = getString(R.string.achievement_10_pipes);
            break;
         case 20:
            key = getString(R.string.achievement_20_pipes);
            break;
         case 50:
            key = getString(R.string.achievement_50_pipes);
            break;
         case 100:
            key = getString(R.string.achievement_100_pipes);
            break;
         case 1000:
            key = getString(R.string.achievement_1000_pipes);
            break;
         default:
            break;
      }
      Games.Achievements.unlock(gameHelper.getApiClient(), key);
   }

   @Override
   public void submitScore(int highScore) {
      if (isSignedIn()) {
         Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_high_scores), highScore);
      }
   }

   @Override
   public void showAchievement() {
      if (isSignedIn()) {
         startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
      }
      else {
         signIn();
      }
   }

   @Override
   public void showScore() {
      if (isSignedIn()) {
         startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
               getString(R.string.leaderboard_high_scores)), requestCode);
      }
      else {
         signIn();
      }
   }

   @Override
   public boolean isSignedIn() {
      return gameHelper.isSignedIn();
   }

}
