package game;

import java.util.Random; // MARK: imported random for fish speed

import javafx.scene.image.Image;

// fish enemy class (virus)
public class Fish extends Sprite {
	public static final int MAX_FISH_SPEED = 3; // speed varies by 3
	public static final int MAX_FISH_DAMAGE = 20;
	public static final int MIN_FISH_DAMAGE = 10;

	// MARK: FISH IMAGES
	public final static Image FISH_IMAGE1 = new Image("images/virus_1.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static Image FISH_IMAGE2 = new Image("images/virus_2.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static Image FISH_IMAGE3 = new Image("images/virus_3.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static Image FISH_IMAGE4 = new Image("images/virus_4.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static Image FISH_IMAGE5 = new Image("images/virus_5.png",Fish.FISH_WIDTH+40,Fish.FISH_WIDTH,false,false);
	public final static Image FISH_IMAGE6 = new Image("images/virus_6.png",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static Image BOSS_IMAGE = new Image("images/virus_boss.gif",Fish.FISH_WIDTH,Fish.FISH_WIDTH,false,false);
	public final static int FISH_WIDTH=70;

	private int fishType;
	private boolean alive;
	private int speed;
	private int damage;

	//attribute that will determine if a fish will initially move to the right
	//private boolean moveRight;

	// CONSTRUCTOR
	Fish(int x, int y){
		super(x,y);

		/*
		 *TODO: Randomize speed of fish and moveRight's initial value
		 */
		this.alive = true;
		Random randomType = new Random();
		Random randomSpeed = new Random();
		Random randomDamage = new Random();

		this.fishType = randomType.nextInt(6);
		// set fish image depending on type (random)
		switch (this.fishType) {
			case 0: this.loadImage(Fish.FISH_IMAGE1);
						break;
			case 1: this.loadImage(Fish.FISH_IMAGE2);
						break;
			case 2: this.loadImage(Fish.FISH_IMAGE3);
						break;
			case 3: this.loadImage(Fish.FISH_IMAGE4);
						break;
			case 4: this.loadImage(Fish.FISH_IMAGE5);
						break;
			case 5: this.loadImage(Fish.FISH_IMAGE6);
						break;
		}

		this.speed = randomSpeed.nextInt(Fish.MAX_FISH_SPEED+1 - 1) + 1; // MARK: MAX+1 since nextInt is exclusive right
		//this.moveRight = random.nextBoolean();					// MARK: another + 1 to start with 1
		//this.damage = randomDamage.nextInt(Fish.MAX_FISH_DAMAGE+1) + 10; // MARK: random damage from 10 to 30
		this.damage = randomDamage.nextInt(Fish.MAX_FISH_DAMAGE+1 - MIN_FISH_DAMAGE) + MIN_FISH_DAMAGE;
	}

	//method that changes the x position of the fish
	void move(){
		/*
		 * TODO: 				If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
//		if (this.moveRight && this.x < GameStage.WINDOW_WIDTH-FISH_WIDTH) {
//			this.x += this.speed;
//		} else if (this.x >= GameStage.WINDOW_WIDTH-FISH_WIDTH) {
//			this.moveRight = !this.moveRight;
//		}
//
//		if (this.moveRight == false && this.x > 0) {
//			this.x -= this.speed;
//		} else if (this.x <= 0) {
//			this.moveRight = !this.moveRight;
//		}

		this.x -= this.speed;
		// if fish reaches the left bound
		if (this.x <= 0 - FISH_WIDTH) { // to consider the image size
			this.alive = false;
		}
	}

	//GETTERS
	public boolean isAlive() {
		return this.alive;
	}
	public int getDamage() {
		return this.damage;
	}

	// SETTER
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
