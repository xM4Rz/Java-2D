package marzejon.diego.smash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import marzejon.diego.smash.SmashMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "PCEP Smash";
	      config.width = 1600;
	      config.height = 900;
		new LwjglApplication(new SmashMain(), config);
	}
}

