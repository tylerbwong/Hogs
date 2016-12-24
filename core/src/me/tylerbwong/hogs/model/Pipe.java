package me.tylerbwong.hogs.model;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * @author Tyler Wong
 */

public class Pipe extends Scrollable {
   private Random random;
   private Rectangle pipeUp, pipeDown, barUp, barDown;
   private float groundY;

   private boolean isScored = false;

   public static final int VERTICAL_GAP = 50;
   public static final int PIPE_WIDTH = 24;
   public static final int PIPE_HEIGHT = 11;

   // When Pipe's constructor is invoked, invoke the super (Scrollable)
   // constructor
   public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
      super(x, y, width, height, scrollSpeed);
      // Initialize a Random object for Random number generation
      random = new Random();
      pipeUp = new Rectangle();
      pipeDown = new Rectangle();
      barUp = new Rectangle();
      barDown = new Rectangle();
      this.groundY = groundY;
   }

   @Override
   public void update(float delta) {
      // Call the updateRunning method in the superclass (Scrollable)
      super.update(delta);

      // The set() method allows you to set the top left corner's x, y
      // coordinates,
      // along with the width and height of the rectangle

      barUp.set(position.x, position.y, width, height);
      barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
            groundY - (position.y + height + VERTICAL_GAP));

      // Our skull width is 24. The bar is only 22 pixels wide. So the skull
      // must be shifted by 1 pixel to the left (so that the skull is centered
      // with respect to its bar).

      // This shift is equivalent to: (SKULL_WIDTH - width) / 2
      pipeUp.set(position.x - (PIPE_WIDTH - width) / 2, position.y + height
            - PIPE_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT);
      pipeDown.set(position.x - (PIPE_WIDTH - width) / 2, barDown.y,
            PIPE_WIDTH, PIPE_HEIGHT);

   }

   @Override
   public void reset(float newX) {
      // Call the reset method in the superclass (Scrollable)
      super.reset(newX);
      // Change the height to a random number
      height = random.nextInt(90) + 15;
      isScored = false;
   }

   public boolean isScored() {
      return isScored;
   }

   public void setScored(boolean scored) {
      isScored = scored;
   }

   public boolean collides(Hog hog) {
      if (position.x < hog.getX() + hog.getWidth()) {
         return (Intersector.overlaps(hog.getBoundingCircle(), barUp)
               || Intersector.overlaps(hog.getBoundingCircle(), barDown)
               || Intersector.overlaps(hog.getBoundingCircle(), pipeUp) || Intersector
               .overlaps(hog.getBoundingCircle(), pipeDown));
      }
      return false;
   }

   public void onRestart(float x, float scrollSpeed) {
      velocity.x = scrollSpeed;
      reset(x);
   }
}
