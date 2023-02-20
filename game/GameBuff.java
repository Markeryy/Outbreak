package game;

import java.util.Random;

import javafx.scene.image.Image;

// buff class
public class GameBuff extends Sprite {

	private Ship affectedShip;
	private int type;

	public final static Image FACEMASK = new Image("images/buff_facemask.png",93,35,false,false);
	private static final Image VACCINE = new Image("images/buff_vaccine.png",50,50,false,false);

	// CONSTRUCTOR
	GameBuff(int x, int y, Ship affectedShip) {
		super(x,y);
		this.affectedShip = affectedShip;
		// Random type = new Random();
		// this.type = type.nextInt(2);
		this.type = 0;
		if (this.type == 0) {
			this.loadImage(FACEMASK);
			//this.affectShip(0); // called in GameTimer when buff is picked up
		} else if (this.type == 1) {
			this.loadImage(VACCINE);
			//this.affectShip(1); // called in GameTimer when buff is picked up
		}
	}

	// called in GameTimer when buff is picked up
	void affectShip(int type) {
		if (type == 0) {
			this.affectedShip.setHealth(this.affectedShip.getHealth() + 50);
		} else if (type == 1) {
			this.affectedShip.setImmortal(true);
		}
	}

	// GETTER
	public int getType() {
		return this.type;
	}

	// SETTER
	public void setAffectedShip(Ship ship) {
		this.affectedShip = ship;
	}
}
