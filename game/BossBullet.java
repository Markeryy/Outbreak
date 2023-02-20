package game;

import java.util.Random;

import javafx.scene.image.Image;

// boss bullet class
public class BossBullet extends Sprite {

	private int bulletDamage;
	private final int BULLET_SPEED = 2;
	public final static Image BULLET_IMAGE = new Image("images/boss_bullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 50;
	public static final int MAX_BOSS_DAMAGE = 30;
	public static final int MIN_BOSS_DAMAGE = 20;

	// CONSTRUCTOR
	BossBullet(int x, int y){  // old - public
		super(x,y);
		Random randomDamage = new Random();
		this.bulletDamage = randomDamage.nextInt(MAX_BOSS_DAMAGE+1 - MIN_BOSS_DAMAGE) + MIN_BOSS_DAMAGE;
		this.loadImage(BossBullet.BULLET_IMAGE);
	}

	void move(){ // old - public
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		this.x -= this.BULLET_SPEED; // MARK: add bullet speed to the x position
		if (this.x <= 0 - BULLET_WIDTH) {
			this.visible = false;
		}
	}

	// GETTER
	public int getBulletDamage() {
		return this.bulletDamage;
	}
}
