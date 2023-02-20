package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import stage.GameOverStage;
import stage.GameStage;
import stage.PanelText;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	private Boss boss;

	// constants for text position in rendering status
	private final int hpTextX = 40;
	private final int timeTextX = 350;
	private final int fishKilledTextX = 570;
	private final int textY = 40;

	private long timeReference;
	private long startSpawn;
	private long startMove;
	private long startSpawnBuff;
	private boolean hasMask;

	private ArrayList<GameTimeSeconds> timers;
	private static int timerIndex = 0;
	private ArrayList<Fish> fishes;
	private ArrayList<GameBuff> buffs;
	public static final int INITIAL_FISH = 7;
	public static final int INITIAL_BUFF = 0;
	public static final int winGameTime = 60; // in seconds
	public static final int bossFightSec = 30; // in seconds
	public static final int MAX_IMMO_TIMER = 5;
	public static boolean bossSpawned = false;
	public static boolean bossDead = false;
	public static boolean ImmoTimerStarted = false;
	public static int fishKilled = 0;

	// CONSTRUCTOR
	public GameTimer(GraphicsContext gc, Scene theScene){
		this.gc = gc;
		this.theScene = theScene;
		this.timeReference = System.nanoTime();
		this.startSpawn = System.nanoTime();	//get current nanotime
		this.startMove = System.nanoTime();
		this.startSpawnBuff = System.nanoTime();	//get current nanotime
		//this.startImmortal = System.nanoTime();
		this.myShip = new Ship("Peter",100,100);
		//instantiate the ArrayList of Fish
		this.fishes = new ArrayList<Fish>();
		this.buffs = new ArrayList<GameBuff>();
		this.timers = new ArrayList<GameTimeSeconds>();
		//call the spawnFishes method
		this.spawnFishes(INITIAL_FISH);
		this.hasMask = false;
		//call method to handle mouse click event
		this.handleKeyPressEvent();
		this.initStatus();
		this.initTimer();
	}

	// HANDLE METHOD
	@Override
	public void handle(long currentNanoTime) {

		// MARK: clear canvas
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		// MARK: stores game time in seconds
		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long appStartSec = TimeUnit.NANOSECONDS.toSeconds(this.timeReference);

		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);
		long startMove = TimeUnit.NANOSECONDS.toSeconds(this.startMove);
		long startSpawnSecBuff = TimeUnit.NANOSECONDS.toSeconds(this.startSpawnBuff);

		// will be used as time (in seconds)
		long gameTimeSec = (currentSec - appStartSec);

		// check gameover
		this.checkGameOver(gameTimeSec);

		// every 3 seconds, spawn fish
		this.spawnFishesEvery3Sec(currentSec, startSec, currentNanoTime);

		if (gameTimeSec > bossFightSec) {  // boss fight starts
			this.spawnBoss();
			if (this.boss.isAlive()) {
				this.controlBoss(currentSec, startMove, currentNanoTime);
				this.BossBulletHitsFish();
				this.checkBossDeath();
			}
		}

		// spawn buff every 10 seconds
		this.spawnBuffsEvery10(currentSec, startSpawnSecBuff, currentNanoTime);
		this.removeBuffsEvery5(currentSec, startSpawnSecBuff);
		this.collideBuff();

		if (this.myShip.getImmortal() == true) {
			// one-time starting of immortal timer
			if (!GameTimer.ImmoTimerStarted) {
				GameTimeSeconds time = this.timers.get(timerIndex);
				timerIndex += 1;
				time.start();
				GameTimer.ImmoTimerStarted = true;
			}
			this.textRenderImmortal(this.gc, GameTimeSeconds.secondsPassed);
			// if timer reaches 3 seconds
			if (GameTimeSeconds.secondsPassed == 3) {
				this.myShip.setImmortal(false);
				GameTimer.ImmoTimerStarted = false;
			}
		}

		this.myShip.move();
		/*
		 * TODO: Call the moveBullets and moveFishes methods
		 */
		this.moveBullets();
		this.moveFishes();
		this.myShip.render(this.gc);  //render the ship

		/*
		 * TODO: Call the renderFishes and renderBullets methods
		 */
		this.renderFishes();
		this.renderBullets();
		this.renderBuffs();
		this.bulletHitsFish();  // MARK: check bullet-fish collision
		this.fishHitsShip();  // MARK: check fish-ship collision

		this.textRender(this.gc, gameTimeSec);
	}

	// FISH METHODS
	private void renderFishes() {
		for (Fish f : this.fishes){
			f.render(this.gc);
		}
	}
	private void spawnFishes(int howMany){
		Random r = new Random();
		for(int i=0;i<howMany;i++){
			int x = GameStage.WINDOW_WIDTH;
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-Fish.FISH_WIDTH);
			/*
			 *TODO: Add a new object Fish to the fishes arraylist
			 */ //CHECK
			Fish fish = new Fish(x,y);
			this.fishes.add(fish);
		}
	}
	private void spawnFishesEvery3Sec(long currentSec, long startSec, long currentNanoTime) {
		if((currentSec - startSec) > 3){
			this.spawnFishes(3);
			this.startSpawn = currentNanoTime;
		}
	}
	private void moveFishes(){
		//Loop through the fishes arraylist
		for(int i = 0; i < this.fishes.size(); i++){
			Fish f = this.fishes.get(i);
			/*
			 * TODO:  *If a fish is alive, move the fish. Else, remove the fish from the fishes arraylist.
			 */
			if (f.isAlive()) {
				f.move();
			} else {
				this.fishes.remove(f); // remove fish from arraylist
			}
		}
	}

	// BULLET METHODS
	private void renderBullets() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		for (Bullet b : this.myShip.getBullets()) {
			b.render(this.gc);
		}
	}
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myShip.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if (b.visible) {
				b.move();
			} else {
				bList.remove(b); // MARK: remove bullet from arraylist
			}
		}
	}
	private void bulletHitsFish() {
		try {
			for (int i=0; i<this.fishes.size(); i++) {
				for (int j=0; j<this.myShip.getBullets().size(); j++) {
					if (this.myShip.getBullets().get(j).collidesWith(this.fishes.get(i))) {
						this.fishes.get(i).setAlive(false);
						this.myShip.getBullets().get(j).setVisible(false);
						GameTimer.fishKilled += 1;
					}
				}
			}
		} catch (Exception e) {}
	}

	// BOSS METHODS
	private void spawnBoss() {
		if (GameTimer.bossSpawned == false) {
			this.boss = new Boss(250,250);
			GameTimer.bossSpawned = true;
		}
	}
	private void renderBoss() {
		this.boss.render(this.gc);
	}
	private void moveBoss() {
		if (this.boss.isAlive()) {
			this.boss.move();
		}
	}
	private void controlBoss(long currentSec, long startMove, long currentNanoTime) {
		if (GameTimer.bossDead == false) {
			if ((currentSec - startMove) > 1){
				this.moveBoss();
				this.boss.shoot();
				this.startMove = currentNanoTime;
			}
			this.textRenderBoss(this.gc, this.boss.getX(), this.boss.getY());
			this.moveBossBullets();
			this.renderBossBullets();
			this.bulletHitsBoss();
			this.renderBoss();
		}
	}
	private void checkBossDeath() {
		if (this.boss.getHealth() <= 0) {
			GameTimer.fishKilled += 1;
			this.boss.setAlive(false);
			GameTimer.bossDead = true;
		}
	}
	private void renderBossBullets() {
		/*
		 *TODO: Loop through the bullets arraylist of myShip
		 *				and render each bullet to the canvas
		 */
		for (BossBullet b : this.boss.getBullets()) {
			b.render(this.gc);
		}
	}
	private void moveBossBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<BossBullet> bList = this.boss.getBullets();

		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bList.size(); i++){
			BossBullet b = bList.get(i);
			/*
			 * TODO:  If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list.
			 */
			if (b.visible) {
				b.move();
			} else {
				bList.remove(b); // MARK: remove bullet from arraylist
			}
		}
	}

	// BUFF METHODS
	private void renderBuffs() {
		for (GameBuff b : this.buffs){
			b.render(this.gc);
		}
	}
	private void spawnBuffs() {
		Random pos = new Random();
		GameBuff buff = new GameBuff(pos.nextInt(GameStage.WINDOW_WIDTH/2), pos.nextInt(GameStage.WINDOW_HEIGHT-50), this.myShip);
		this.buffs.add(buff);
	}
	private void spawnBuffsEvery10(long currentSec, long startSpawnSecBuff, long currentNanoTime) {
		if ((currentSec - startSpawnSecBuff) > 9) { // 10 seconds - old 9
			spawnBuffs();
			this.startSpawnBuff = currentNanoTime;
		}
	}
	private void removeBuffsEvery5(long currentSec, long startSpawnSecBuff) {
		if (currentSec - startSpawnSecBuff == 5) { // old 5
			for (int i=0; i<this.buffs.size(); i++) {
				this.buffs.get(i).setVisible(false);
				this.buffs.remove(i);
			}
		}
	}

	// collision methods
	private void collideBuff() {
		try {
			for (int i=0; i<this.buffs.size(); i++) {
				if (this.myShip.collidesWith(this.buffs.get(i))) {
					this.buffs.get(i).affectShip(this.buffs.get(i).getType());
					if (this.buffs.get(i).getType() == 0) {  // secondary buff effects (primary effects of gamebuff class)
						this.hasMask = true;
					}
					this.buffs.get(i).setVisible(false);
					this.buffs.remove(i);
				}
			}
		} catch (Exception e) {}
	}
	private void BossBulletHitsFish() {
		try {
			for (int i=0; i<this.boss.getBullets().size(); i++) {
				if (this.boss.getBullets().get(i).collidesWith(this.myShip)) {

					// if ship is not immortal
					if (!this.myShip.getImmortal()) {
						this.myShip.setHealth(this.myShip.getHealth()
								- this.boss.getBullets().get(i).getBulletDamage());
					}

					this.boss.getBullets().get(i).setVisible(false);
				}
			}
		} catch (Exception e) {}
	}
	private void bulletHitsBoss() {
		try {
			for (int i=0; i<this.myShip.getBullets().size(); i++) {
				if (this.myShip.getBullets().get(i).collidesWith(this.boss)) {
					this.myShip.getBullets().get(i).setVisible(false);
					this.boss.setHealth(this.boss.getHealth() - this.myShip.getBullets().get(i).getBulletDamage());
				}
			}
		} catch (Exception e) {}
	}
	private void fishHitsShip() {
		try {
			for (int i=0; i<this.fishes.size(); i++) {
				if (this.myShip.collidesWith(this.fishes.get(i))) {
					if (!this.myShip.getImmortal()) {
						this.myShip.gotHit(this.fishes.get(i));
					}
					this.fishes.get(i).setAlive(false);
				}
			}
		} catch (Exception e) {}
	}

	// listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
            	if(code == KeyCode.UP ||
            	   code == KeyCode.LEFT ||
            	   code == KeyCode.DOWN ||
            	   code == KeyCode.RIGHT) {

            		if (hasMask) {
            			myShip.loadImage(Ship.MASK_IMAGE_WALK);
            		} else {
            			myShip.loadImage(Ship.SHIP_IMAGE_WALK);
            		}

            	}
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
            	if (hasMask) {
            		myShip.loadImage(Ship.MASK_IMAGE_STAND);
            	} else {
            		myShip.loadImage(Ship.SHIP_IMAGE_STAND);
            	}
            	KeyCode code = e.getCode();

                stopMyShip(code);
            }
		});
    }

	// move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.UP) this.myShip.setDY(-3);

		if(ke==KeyCode.LEFT) this.myShip.setDX(-3);

		if(ke==KeyCode.DOWN) this.myShip.setDY(3);

 		if(ke==KeyCode.RIGHT) this.myShip.setDX(3);

		if(ke==KeyCode.SPACE) this.myShip.shoot();

		//System.out.println(ke+" key pressed.");
   	}

	// stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

	// check game over
	private void checkGameOver(long gameTime) {
		if (this.myShip.getHealth() <= 0) {
			this.myShip.die();
			GameOverStage gameover = new GameOverStage(0);
			GameStage.stage.setScene(gameover.getScene());
			this.stop();
		} else if (GameTimer.bossDead && gameTime >= winGameTime) {
			GameOverStage gameover = new GameOverStage(1);
			GameStage.stage.setScene(gameover.getScene());
			this.stop();
		}
	}

	// initialize timer for immortality
	private void initTimer() {
		for (int i=0; i<MAX_IMMO_TIMER; i++) {
			GameTimeSeconds timer = new GameTimeSeconds();
			this.timers.add(timer);
		}
	}

	// UI IN CLASS

	// initialize the text status in gc
	private void initStatus() {
		this.gc.setFill(Color.CHARTREUSE);
		try {
			this.gc.setFont(Font.loadFont(new FileInputStream(new File(PanelText.FONT_PATH)), 20));
		} catch (FileNotFoundException e) {
			this.gc.setFont(Font.font("Verdana", 20));
		}
	}
	// render text (health, time, fish killed)
	private void textRender(GraphicsContext gc, long currentSec) {
        //this.gc.setTextAlign(TextAlignment.LEFT);
        //this.gc.setTextBaseline(VPos.CENTER);
        this.gc.fillText("Health: " + this.myShip.getHealth(), this.hpTextX, this.textY);
        this.gc.fillText("Time: " + currentSec, this.timeTextX, this.textY);
        this.gc.fillText("Virus Killed: " + GameTimer.fishKilled, this.fishKilledTextX, this.textY);
    }
	private void textRenderBoss(GraphicsContext gc, int bossX, int bossY) {
		this.gc.fillText("Boss Health: " + this.boss.getHealth(), bossX-60, bossY+10);
	}
	private void textRenderImmortal(GraphicsContext gc, int seconds) {
		int secondsPassed = seconds+1;
		this.gc.fillText("VACCINATED! " + secondsPassed, this.myShip.getX()-60, this.myShip.getY());
	}

	// GETTER
	public ArrayList<GameTimeSeconds> getTimers() {
		return this.timers;
	}


}

