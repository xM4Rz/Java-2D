package marzejon.diego.smash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreen implements Screen {

	final SmashMain game;
	OrthographicCamera camera;
	public int x = 0;
	Texture PlayerOneWins;
	Texture PlayerTwoWins;
	

	public GameOverScreen(final SmashMain gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 900);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		PlayerOneWins = new Texture("PlayerOneWins.png");
		PlayerTwoWins = new Texture("PlayerTwoWins.png");
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		// game.font.draw(game.batch, "Tap anywhere to begin!", 1175, 715);

		if (GameScreen.coinsGathered >= 50){
			game.batch.draw(PlayerOneWins, 0, 0);
			if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
		if (GameScreen.coinsGathered2 >= 50){
			game.batch.draw(PlayerTwoWins, 0, 0);
			if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				game.setScreen(new MainMenuScreen(game));
			}
		}

	
				
				

				



		game.batch.end();

		

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

