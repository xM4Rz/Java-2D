package marzejon.diego.smash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SmashMain extends Game {

  	SpriteBatch batch;
	BitmapFont font;
	Texture welcome;
	
	// Objects used
		Animation<TextureRegion> walkAnimation;
		Animation<TextureRegion> walkAnimationChief;
		Animation<TextureRegion> walkAnimationWildcat;
		Animation<TextureRegion> walkAnimationRock;// Must declare frame type (TextureRegion)
		Texture walkSheet;
		Texture chiefSheet;
		Texture wildcatSheet;
		Texture rockSheet;
		SpriteBatch spriteBatch;

		// A variable for tracking elapsed time for the animation
		float stateTime;
		float stateTimeChief;
		float stateTimeWildcat;
		float stateTimeRock;
		private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		font.getData().setScale(2);
		this.setScreen(new MainMenuScreen(this));
		welcome = new Texture(Gdx.files.internal("welcome.png"));
		
		chiefSheet = new Texture(Gdx.files.internal("chiefsheet.png"));
		wildcatSheet = new Texture(Gdx.files.internal("wildcatsheet.png"));
		rockSheet = new Texture(Gdx.files.internal("rocksheet.png"));
		walkSheet = new Texture(Gdx.files.internal("sprite-animation4.png"));

		// Use the split utility method to create a 2D array of TextureRegions. This is 
		// possible because this sprite sheet contains frames of equal size and they are 
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);
		TextureRegion[][] cs = TextureRegion.split(chiefSheet, chiefSheet.getWidth()/2, chiefSheet.getHeight()/3);
		TextureRegion[][] ws = TextureRegion.split(wildcatSheet, wildcatSheet.getWidth(), wildcatSheet.getHeight()/2);
		TextureRegion[][] rs = TextureRegion.split(rockSheet, rockSheet.getWidth()/2, rockSheet.getHeight()/2);
		// Place the regions into a 1D array in the correct order, starting from the top 
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		TextureRegion[] walkFramesChief = new TextureRegion[2 * 3];
		int indexC = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				walkFramesChief[indexC++] = cs[i][j];
			}
		}
		
		TextureRegion[] walkFramesWildcat = new TextureRegion[1 * 2];
		int indexW = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 1; j++) {
				walkFramesWildcat[indexW++] = ws[i][j];
			}
		}

		
		TextureRegion[] walkFramesRock = new TextureRegion[2 * 2];
		int indexR = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				walkFramesRock[indexR++] = rs[i][j];
			}
		}


		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
		walkAnimationChief = new Animation<TextureRegion>(0.025f, walkFramesChief);
		walkAnimationWildcat = new Animation<TextureRegion>(0.025f, walkFramesWildcat);
		walkAnimationRock = new Animation<TextureRegion>(0.025f, walkFramesRock);
		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		stateTimeChief = 0f;
		stateTimeWildcat = 0f;
		stateTimeRock = 0f;
		stateTime = 0f;
	}
	

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}