package game;

import javafx.scene.image.Image;
import stage.GameStage;

// ship bullet class
public class Bullet extends Sprite {

	private final int BULLET_SPEED = 4; //original value = 20
	public final static Image BULLET_IMAGE = new Image("images/pill.png",Bullet.BULLET_WIDTH,Bullet.BULLET_HEIGHT,false,false);
	public final static int BULLET_WIDTH = 30;
	public final static int BULLET_HEIGHT = 15;
	private int bulletDamage;

	// CONSTRUCTOR
	Bullet(int x, int y){ // old - public
		super(x,y);
		this.bulletDamage = 20;
		this.loadImage(Bullet.BULLET_IMAGE);
	}

	//method that will move/change the x position of the bullet
	void move(){ // old - public
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		this.x += this.BULLET_SPEED; // MARK: add bullet speed to the x position
		if (this.x >= GameStage.WINDOW_WIDTH + BULLET_WIDTH) {
			this.visible = false;
		}
	}

	// GETTER
	public int getBulletDamage() {
		return this.bulletDamage;
	}
}