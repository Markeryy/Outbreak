package game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import stage.GameStage;

// ship class (player)
public class Ship extends Sprite{

	private String name;
	private int health;
	private final int initialHealth = 100;
	private boolean alive;
	private boolean immortal;

	private ArrayList<Bullet> bullets;
	public final static Image SHIP_IMAGE_STAND = new Image("images/doctor_stand.png",Ship.SHIP_WIDTH,Ship.SHIP_HEIGHT,false,false);
	public final static Image SHIP_IMAGE_WALK = new Image("images/doctor_walk.gif",Ship.SHIP_WIDTH,Ship.SHIP_HEIGHT,false,false);
	public final static Image MASK_IMAGE_STAND = new Image("images/doctor_mask_stand.png",Ship.SHIP_WIDTH,Ship.SHIP_HEIGHT,false,false);
	public final static Image MASK_IMAGE_WALK = new Image("images/doctor_mask_walk.gif",Ship.SHIP_WIDTH,Ship.SHIP_HEIGHT,false,false);

	private final static int SHIP_HEIGHT = 87; // scaled down from 525
	private final static int SHIP_WIDTH = 50; // scaled down from 300

	// CONSTRUCTOR
	public Ship(String name, int x, int y){
		super(x,y);
		this.name = name;
		this.immortal = false;
		this.health = this.initialHealth;
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.SHIP_IMAGE_STAND);
	}

	// GETTERS
	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}
	public int getHealth() {
		return this.health;
	}
	void gotHit(Fish virus) {
		this.health -= virus.getDamage();
	}
	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}
	boolean getImmortal() {
		return this.immortal;
	}

	// SETTERS
	public void setHealth(int health) {
		this.health = health;
	}
	public void die() {
    	this.alive = false;
    }
	void setImmortal(boolean value) {
		this.immortal = value;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x); // + this.width+20
		int y = (int) (this.y + this.height/2);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */ // MARK: creating the bullet
		Bullet bullet = new Bullet(x, y);
		this.bullets.add(bullet);
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		if (this.x + this.dx >= GameStage.WINDOW_WIDTH-SHIP_WIDTH) { // MARK: if ship goes to EAST bound (add dx for the program to know if ship will go beyond the bounds)
			this.x = GameStage.WINDOW_WIDTH-SHIP_WIDTH; // MARK: subtract SHIP_WIDTH to take into consideration the png size of ship
		} else if (this.x + this.dx < 0) { // MARK: if ship goes to WEST bound
			this.x = 0;
		} else {
			this.x += this.dx; // MARK: add the dx (horizontal move value)
		}

		if (this.y + this.dy >= GameStage.WINDOW_HEIGHT-SHIP_HEIGHT) { // MARK: if ship goes to SOUTH bound (add dx for the program to know if ship will go beyond the bounds)
			this.y = GameStage.WINDOW_HEIGHT-SHIP_HEIGHT; // MARK: subtract SHIP_WIDTH to take into consideration the png size of ship
		} else if (this.y + this.dy < 0) { // MARK: if ship goes to NORTH bound
			this.y = 0;
		} else {
			this.y += this.dy; // MARK: add the dy (vertical move value)
		}

	}



}
