package marzejon.diego.smash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
	final SmashMain game;
	OrthographicCamera camera;
	public int x = 0;

	public MainMenuScreen(final SmashMain gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(game.welcome, 0, 0);
		// game.font.draw(game.batch, "Tap anywhere to begin!", 1175, 715);

		
		

	
				
				

				if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
					GameScreen.char1 = "chief";
				}
				if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
					GameScreen.char1 = "rock";
				}
				if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
					GameScreen.char1 = "wildcat";
				
			}
			

				if (Gdx.input.isKeyJustPressed(Keys.A)) {
					GameScreen.char2 = "chief";
				}
				if (Gdx.input.isKeyJustPressed(Keys.S)) {
					GameScreen.char2 = "rock";
				}
				if (Gdx.input.isKeyJustPressed(Keys.D)) {
					GameScreen.char2 = "wildcat";
				}



		game.batch.end();

		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
			dispose();

		}

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
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
	}
}
