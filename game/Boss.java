package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import stage.GameStage;

// boss class
public class Boss extends Sprite {

	public final static int BOSS_WIDTH = 140;
	public final static Image BOSS_IMAGE = new Image("images/virus_boss.gif",Boss.BOSS_WIDTH,Boss.BOSS_WIDTH,false,false);
	public final static int MAX_X_POS = GameStage.WINDOW_WIDTH-BOSS_WIDTH;
	public final static int MIN_X_POS = GameStage.WINDOW_WIDTH/2;
	public final static int MAX_Y_POS = GameStage.WINDOW_HEIGHT-BOSS_WIDTH;
	public static final int INITIAL_HEALTH = 1000;

	private ArrayList<BossBullet> bullets;
	private boolean alive;
	//private int damage;  // boss damage attribute transferred to boss bullet
	private int health;

	// CONSTRUCTOR
	Boss(int x, int y) {
		super(x,y);
		this.alive = true;
		this.health = INITIAL_HEALTH;
		this.bullets = new ArrayList<BossBullet>();
		// Random randomDamage = new Random();
		// this.damage = randomDamage.nextInt(MAX_BOSS_DAMAGE+1 - MIN_BOSS_DAMAGE) + MIN_BOSS_DAMAGE;

		this.loadImage(BOSS_IMAGE);
	}

	void move() {
		Random randomX = new Random();
		Random randomY = new Random();

		this.x = randomX.nextInt(MAX_X_POS - MIN_X_POS) + MIN_X_POS;
		this.y = randomY.nextInt(MAX_Y_POS);
	}
	void shoot(){ // old - public
		// compute for the x and y initial position of the bullet
		int x = (int) (this.x); // + this.width+20
		int y = (int) (this.y + this.height/2);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */ // creating the bullet
		BossBullet bullet = new BossBullet(x, y);
		this.bullets.add(bullet);
    }

	// GETTERS
	public boolean isAlive() {
		return this.alive;
	}
	public int getHealth() {
		return this.health;
	}
	public ArrayList<BossBullet> getBullets() {
		return this.bullets;
	}

	// SETTERS
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public void setHealth(int health) {
		this.health = health;
	}
}
