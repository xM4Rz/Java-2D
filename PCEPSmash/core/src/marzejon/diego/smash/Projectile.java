package marzejon.diego.smash;

import java.awt.MouseInfo;
import java.awt.Point;

public class Projectile {

	private int x, y, speedX, speedY, startX, startY;
	private final int SPEED = 25;
	private boolean visible;
	private double deltaX, deltaY, totalXY, xPercent, yPercent;
	Point mouse = MouseInfo.getPointerInfo().getLocation();
	int mouseX = mouse.x - 20;
	int mouseY = mouse.y - 62;
	public Projectile(int x, int y) {
		startX = x;
		startY = y;
		this.x = x;
		this.y = y;
		visible = true;
	}

	public void update() {
		deltaX = Math.abs(mouseX - x);
		deltaY = Math.abs(mouseY - y);
		totalXY = deltaX + deltaY;
		if (totalXY != 0) {
			xPercent = deltaX / totalXY;
			yPercent = 1 - xPercent;
		}

		if (mouseX < startX && mouseY > startY) {
			speedX = (int) (-SPEED * xPercent);
			speedY = (int) (SPEED * yPercent);
		} else if (mouseX > startX && mouseY > startY) {
			speedX = (int) (SPEED * xPercent);
			speedY = (int) (SPEED * yPercent);
		} else if (mouseY < startY && mouseX > startX) {
			speedX = (int) (SPEED * xPercent);
			speedY = -(int) (SPEED * yPercent);
		} else if (mouseY < startY && mouseX < startX) {
			speedX = -(int) (SPEED * xPercent);
			speedY = -(int) (SPEED * yPercent);
		}
		x += speedX;
		y += speedY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}