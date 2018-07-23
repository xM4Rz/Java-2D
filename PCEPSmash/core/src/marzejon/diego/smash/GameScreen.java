package marzejon.diego.smash;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final SmashMain game;

	Texture dropImage;
	Texture player1Image;
	Sound coinSound;
	Sound boop;
	Sound oof;
	Music rainMusic;
	int dir, dir2;
	OrthographicCamera camera;
	Rectangle player1;
	Rectangle player2;
	Array<Rectangle> raindrops;
	Array<Rectangle> bullets;
	Array<Rectangle> bullets2;
	long lastDropTime;
	static int coinsGathered;
	static int coinsGathered2;
	int life = 3;
	int life2 = 3;
	public static boolean jumping;
	public static int speed = 21, speed2 = 21;
	public static int state;
	public static int state2;
	public static int orientation;
	public static int orientation2;
	Texture backgroundTexture;
	int shotDirection;
	int shotDirection2;
	Sprite player1Sprite;
	Sprite player1Sprite2;
	public static boolean running = false;
	Rectangle bullet;
	Rectangle bullet2;
	Texture bulletTex;
	Sprite bulletSprite;
	public static String char1 = "wildcat";
	public static String char2 = "rock";

	public GameScreen(final SmashMain gam) {
		this.game = gam;

		try {
			dropImage = new Texture(Gdx.files.internal("coin1.png"));
		} catch (Exception e) {
			System.out.println("Cannot find image");
			
		}
		player1Image = new Texture(Gdx.files.internal("pixelarttest2.png"));
		backgroundTexture = new Texture("gamemap1.png");
		bulletTex = new Texture("laser.png");

		bulletSprite = new Sprite(bulletTex);
		player1Sprite = new Sprite(player1Image);
		player1Sprite2 = new Sprite(player1Image);

		// load the drop sound effect and the rain background "music"
		coinSound = Gdx.audio.newSound(Gdx.files.internal("coinSound.wav"));
		oof = Gdx.audio.newSound(Gdx.files.internal("oof.wav"));
		boop = Gdx.audio.newSound(Gdx.files.internal("cartoon004.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("BackgroundMusic.mp3"));
		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);

		// create a Rectangle to logically represent the player1

		if (char1.equals("rock")) {
			player1 = new Rectangle();
			player1.x = 1600 / 2 - 64 / 2; // center the player1 horizontally
			player1.y = 350; // bottom left corner of the player1 is 20 pixels
								// above
			player1.width = 200;
			player1.height = 200;
		}

		if (char1.equals("wildcat")) {
			player1 = new Rectangle();
			player1.x = 1600 / 2 - 64 / 2; // center the player1 horizontally
			player1.y = 350; // bottom left corner of the player1 is 20 pixels
								// above
			player1.width = 200;
			player1.height = 150;
		}

		if (char1.equals("chief")) {
			player1 = new Rectangle();
			player1.x = 1600 / 2 - 64 / 2; // center the player1 horizontally
			player1.y = 350; // bottom left corner of the player1 is 20 pixels
								// above
			player1.width = 100;
			player1.height = 200;
		}

		if (char2.equals("rock")) {
			player2 = new Rectangle();
			player2.x = 1600 / 2 - 64 / 2 + 64; // center the player1
												// horizontally
			player2.y = 350; // bottom left corner of the player1 is 20 pixels
								// above
			player2.width = 200;
			player2.height = 200;
		}

		if (char2.equals("wildcat")) {
			player2 = new Rectangle();
			player2.x = 1600 / 2 - 64 / 2 + 64; // center the player1
												// horizontally
			player2.y = 350; // bottom left corner of the player1 is 20 pixels
								// above
			player2.width = 200;
			player2.height = 150;
		}

		if (char2.equals("chief")) {
			player2 = new Rectangle();
			player2.x = 1600 / 2 - 64 / 2 + 64; // center the player1
												// horizontally
			player2.y = 350; // bottom left corner of the player1 is 20 pixels
								// above
			player2.width = 100;
			player2.height = 200;
		}
		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();

		bullets = new Array<Rectangle>();
		spawnBullet(player1);

		bullets2 = new Array<Rectangle>();
		spawnBullet(player2);

		coinsGathered = 5;
		coinsGathered2 = 5;

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 1600 - 64);
		raindrop.y = 800;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void spawnBullet(Rectangle player) {
		if (player == player1) {

			Rectangle bullet = new Rectangle();
			bullet.x = player.x;
			bullet.y = player.y + player.height / 2;

			bullet.width = 30;
			bullet.height = 10;
			bullets.add(bullet);
		}
		if (player == player2) {

			Rectangle bullet2 = new Rectangle();
			bullet2.x = player2.x;
			bullet2.y = player2.y + player2.height / 2;

			bullet2.width = 30;
			bullet2.height = 10;
			bullets2.add(bullet2);
		}

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.stateTime += Gdx.graphics.getDeltaTime();
		game.stateTimeWildcat += Gdx.graphics.getDeltaTime() / 10;
		game.stateTimeChief += Gdx.graphics.getDeltaTime() / 5;
		game.stateTimeRock += Gdx.graphics.getDeltaTime() / 5;
		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
		TextureRegion currentFrame = game.walkAnimation.getKeyFrame(game.stateTime);
		TextureRegion currentFrameChief = game.walkAnimationChief.getKeyFrame(game.stateTimeChief);
		TextureRegion currentFrameWildcat = game.walkAnimationWildcat.getKeyFrame(game.stateTimeWildcat);
		TextureRegion currentFrameRock = game.walkAnimationRock.getKeyFrame(game.stateTimeRock);

		///////////////////////// DRAWING////////////////////////////////////

		game.batch.begin();
		game.batch.draw(backgroundTexture, 0, 0);

		
		
		if (char1.equals("wildcat")) {
			if (!currentFrameWildcat.isFlipX() && orientation == -1)
				currentFrameWildcat.flip(true, false);
			if (currentFrameWildcat.isFlipX() && orientation == 1)
				currentFrameWildcat.flip(true, false);
			game.batch.draw(currentFrameWildcat, player1.x, player1.y);
		}
		if (char1.equals("rock")) {
			if (!currentFrameRock.isFlipX() && orientation == -1)
				currentFrameRock.flip(true, false);
			if (currentFrameRock.isFlipX() && orientation == 1)
				currentFrameRock.flip(true, false);
			game.batch.draw(currentFrameRock, player1.x, player1.y);
		}
		if (char1.equals("chief")) {
			if (!currentFrameChief.isFlipX() && orientation == -1)
				currentFrameChief.flip(true, false);
			if (currentFrameChief.isFlipX() && orientation == 1)
				currentFrameChief.flip(true, false);
			game.batch.draw(currentFrameChief, player1.x, player1.y);
		}
		if (char2.equals("chief")) {
			if (!currentFrameChief.isFlipX() && orientation2 == -1)
				currentFrameChief.flip(true, false);
			if (currentFrameChief.isFlipX() && orientation2 == 1)
				currentFrameChief.flip(true, false);
			game.batch.draw(currentFrameChief, player2.x, player2.y);
		}

		if (char2.equals("wildcat")) {
			if (!currentFrameWildcat.isFlipX() && orientation2 == -1)
				currentFrameWildcat.flip(true, false);
			if (currentFrameWildcat.isFlipX() && orientation2 == 1)
				currentFrameWildcat.flip(true, false);
			game.batch.draw(currentFrameWildcat, player2.x, player2.y);
		}
		if (char2.equals("rock")) {
			if (!currentFrameRock.isFlipX() && orientation2 == -1)
				currentFrameRock.flip(true, false);
			if (currentFrameRock.isFlipX() && orientation2 == 1)
				currentFrameRock.flip(true, false);
			game.batch.draw(currentFrameRock, player2.x, player2.y);
		}

		////////////////////////////////////////////////////////////////////////////////

		// game.batch.draw(player1Sprite, player1.x, player1.y);
		// game.batch.draw(player1Sprite2, player2.x, player2.y);
		game.font.draw(game.batch, "Coins Collected P1: " + coinsGathered, 0, 900);
		game.font.draw(game.batch, "Coins Collected P2: " + coinsGathered2, 1250, 900);

		for (Rectangle raindrop : raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		for (Rectangle bullet : bullets) {
			game.batch.draw(bulletSprite, bullet.x, bullet.y);
		}
		for (Rectangle bullet2 : bullets2) {
			game.batch.draw(bulletSprite, bullet2.x, bullet2.y);
		}
		game.batch.end();

		// process user input

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player1.x -= 400 * Gdx.graphics.getDeltaTime();

			if (char1.equalsIgnoreCase("wildcat")) {
				if (game.walkAnimationWildcat.isAnimationFinished(game.stateTimeWildcat) && state == 0) {
					game.stateTimeWildcat = 0;
				}
			}
			if (char1.equalsIgnoreCase("rock")) {
				if (game.walkAnimationRock.isAnimationFinished(game.stateTimeRock) && state == 0) {
					game.stateTimeRock = 0;
				}
			}
			if (char1.equalsIgnoreCase("chief")) {
				if (game.walkAnimationChief.isAnimationFinished(game.stateTimeChief) && state == 0) {
					game.stateTimeChief = 0;
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player1.x += 400 * Gdx.graphics.getDeltaTime();

			if (char1.equalsIgnoreCase("wildcat")) {
				if (game.walkAnimationWildcat.isAnimationFinished(game.stateTimeWildcat) && state == 0) {
					game.stateTimeWildcat = 0;
				}
			}
			if (char1.equalsIgnoreCase("rock")) {
				if (game.walkAnimationRock.isAnimationFinished(game.stateTimeRock) && state == 0) {
					game.stateTimeRock = 0;
				}
			}
			if (char1.equalsIgnoreCase("chief")) {
				if (game.walkAnimationChief.isAnimationFinished(game.stateTimeChief) && state == 0) {
					game.stateTimeChief = 0;
				}
			}

		}
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			orientation = 1;
		}
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			orientation = -1;
		}

		if ((Gdx.input.isKeyJustPressed(Keys.UP) && state == 0)) {
			state = 1;
			running = false;
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			player2.x -= 400 * Gdx.graphics.getDeltaTime();
			if (char2.equalsIgnoreCase("wildcat")) {
				if (game.walkAnimationWildcat.isAnimationFinished(game.stateTimeWildcat) && state == 0) {
					game.stateTimeWildcat = 0;
				}
			}
			if (char2.equalsIgnoreCase("rock")) {
				if (game.walkAnimationRock.isAnimationFinished(game.stateTimeRock) && state == 0) {
					game.stateTimeRock = 0;
				}
			}
			if (char2.equalsIgnoreCase("chief")) {
				if (game.walkAnimationChief.isAnimationFinished(game.stateTimeChief) && state == 0) {
					game.stateTimeChief = 0;
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			player2.x += 400 * Gdx.graphics.getDeltaTime();
			if (char2.equalsIgnoreCase("wildcat")) {
				if (game.walkAnimationWildcat.isAnimationFinished(game.stateTimeWildcat) && state == 0) {
					game.stateTimeWildcat = 0;
				}
			}
			if (char2.equalsIgnoreCase("rock")) {
				if (game.walkAnimationRock.isAnimationFinished(game.stateTimeRock) && state == 0) {
					game.stateTimeRock = 0;
				}
			}
			if (char2.equalsIgnoreCase("chief")) {
				if (game.walkAnimationChief.isAnimationFinished(game.stateTimeChief) && state == 0) {
					game.stateTimeChief = 0;
				}
			}

		}
		if (Gdx.input.isKeyJustPressed(Keys.D)) {
			orientation2 = 1;
		}
		if (Gdx.input.isKeyJustPressed(Keys.A)) {
			orientation2 = -1;
		}
		if ((Gdx.input.isKeyJustPressed(Keys.W) && state2 == 0)) {
			state2 = 1;
		}
		if ((Gdx.input.isKeyJustPressed(Keys.ESCAPE))) {
			game.setScreen(new MainMenuScreen(game));
			rainMusic.stop();
		}

		////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////

		// make sure the player1 stays within the screen bounds
		if (player1.x < 0)
			player1.x = 0;
		if (player1.x > 1600 - 64)
			player1.x = 1600 - 64;

		if (player2.x < 0)
			player2.x = 0;
		if (player2.x > 1600 - 64)
			player2.x = 1600 - 64;

		if (player1.x > 330 && player1.x < 470 && state != 1) {
			state = -1;
		}
		if (player1.x > 1100 && player1.x < 1600 && state != 1) {
			state = -1;
		}
		if (player2.x > 330 && player2.x < 470 && state2 != 1) {
			state2 = -1;
		}
		if (player2.x > 1100 && player2.x < 1600 && state2 != 1) {
			state2 = -1;
		}

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();

		if (Gdx.input.isKeyJustPressed(Keys.Q) && orientation2 == -1) {
			if (coinsGathered2 >= 3) {
				spawnBullet(player2);
				coinsGathered2 -= 3;
				dir2 = -1;
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.Q) && orientation2 == 1) {
			if (coinsGathered2 >= 3) {
				spawnBullet(player2);
				coinsGathered2 -= 3;
				dir2 = 1;
			}
		}

		if (Gdx.input.isKeyJustPressed(Keys.ENTER) && orientation == -1) {
			if (coinsGathered >= 3) {
				spawnBullet(player1);
				coinsGathered -= 3;
				dir = -1;
			}
		}

		if (Gdx.input.isKeyJustPressed(Keys.ENTER) && orientation == 1) {
			if (coinsGathered >= 3) {
				spawnBullet(player1);
				coinsGathered -= 3;
				dir = 1;
			}
		}
		
		if(coinsGathered >=50 || coinsGathered2 >=50){
			game.setScreen(new GameOverScreen(game));
			rainMusic.stop();
		}
		
		

		if (state == 1) {
			// We're ascending. Decrease the Y coordinate of the sprite by the
			// speed.
			player1.y += speed;
			speed--;
			if (speed <= 0) {
				System.out.println("Max jump height: " + player1.y);
				speed = 0;
				state = -1;
			}

		} else if (state == -1) {
			// We're descending. Increase the Y coordinate by the speed (at
			// first, it's 0).
			player1.y -= speed;
			speed++; // Increase the speed, so the character falls gradually
						// faster.
			if (player1.y <= 350 && player1.y >= 320 && (player1.x > 415 && player1.x < 1100)) {
				// If we reached the original Y coordinate, we hit the ground.
				// Mark the
				// character as standing.
				state = 0;
				player1.y = 350;
				speed = 21;
			}
			if (player1.y <= 400 && player1.y >= 370 && (player1.x >= 0 && player1.x < 330)) {
				// If we reached the original Y coordinate, we hit the ground.
				// Mark the
				// character as standing.
				state = 0;
				player1.y = 400;
				speed = 21;
			}
			if (player1.y <= 280 && player1.y >= 250 && (player1.x > 1130 && player1.x < 1600)) {
				// If we reached the original Y coordinate, we hit the ground.
				// Mark the
				// character as standing.
				state = 0;
				player1.y = 280;
				speed = 21;
			} else if (player1.y <= 0) {
				life--;
				player1.y = 800;
				player1.x = 800;
				state = -1;
				speed = 3;
				if (coinsGathered2 > 0)
					coinsGathered = coinsGathered / 2;
			}
		}

		if (state2 == 1) {
			// We're ascending. Decrease the Y coordinate of the sprite by the
			// speed.
			player2.y += speed2;
			speed2--; // The character needs to jump smoothly, so the speed
						// should decrease as he
						// ascends.
			if (speed2 <= 0) {
				System.out.println("Max jump height: " + player1.y);
				speed2 = 0;
				state2 = -1;
			}

		} else if (state2 == -1) {
			// We're descending. Increase the Y coordinate by the speed (at
			// first, it's 0).
			player2.y -= speed2;
			speed2++; // Increase the speed, so the character falls gradually
						// faster.
			if ((player2.y <= 350 && player2.y >= 320) && (player2.x > 415 && player2.x < 1100)) {
				// If we reached the original Y coordinate, we hit the ground.
				// Mark the
				// character as standing.
				state2 = 0;
				player2.y = 350;
				speed2 = 21;
			}
			if ((player2.y <= 400 && player2.y >= 370) && (player2.x >= 0 && player2.x < 330)) {

				state2 = 0;
				player2.y = 400;
				speed2 = 21;
			}
			if ((player2.y <= 280 && player2.y >= 250) && (player2.x > 1130 && player2.x < 1600)) {
				// If we reached the original Y coordinate, we hit the ground.
				// Mark the
				// character as standing.
				state2 = 0;
				player2.y = 280;
				speed2 = 21;
			} else if (player2.y <= 0) {
				life2--;
				player2.y = 800;
				player2.x = 800;
				state2 = -1;
				speed2 = 3;
				if (coinsGathered2 > 0)
					coinsGathered2 = coinsGathered2 / 2;
			}
		}

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (raindrop.overlaps(player1)) {
				coinsGathered++;
				coinSound.play();
				spawnRaindrop();
				iter.remove();
				
			}
			if (raindrop.overlaps(player2)) {
				coinsGathered2++;
				coinSound.play();
				spawnRaindrop();
				iter.remove();
				
			}

		}

		Iterator<Rectangle> bulletIter = bullets.iterator();
		while (bulletIter.hasNext()) {
			Rectangle bullet = bulletIter.next();
			bullet.x += 400 * dir * Gdx.graphics.getDeltaTime();
			if (bullet.x < 0 || bullet.x > 1600)
				bulletIter.remove();
			if (bullet.overlaps(player2)) {
				coinsGathered2 -= 5;
				oof.play();
				bulletIter.remove();
			}

		}

		Iterator<Rectangle> bulletIter2 = bullets2.iterator();
		while (bulletIter2.hasNext()) {
			Rectangle bullet2 = bulletIter2.next();
			bullet2.x += 400 * dir2 * Gdx.graphics.getDeltaTime();
			if (bullet2.x < 0 || bullet2.x > 1600)
				bulletIter2.remove();
			if (bullet2.overlaps(player1)) {
				coinsGathered -= 5;
				oof.play();
				bulletIter2.remove();
			}

		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		player1Image.dispose();
		coinSound.dispose();
		rainMusic.dispose();
	}

}