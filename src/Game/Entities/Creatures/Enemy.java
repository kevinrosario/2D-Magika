package Game.Entities.Creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Game.Entities.EntityBase;
import Game.Inventories.Inventory;
import Game.Items.Item;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

public class Enemy extends CreatureBase {
	
	protected Animation animDown, animUp, animLeft, animRight;

	protected Boolean attacking = false;
	protected Boolean stuck = false;

	protected int animWalkingSpeed = 150;
	protected Inventory Skelyinventory;
	protected Rectangle SkelyCam;

	protected int healthcounter = 0;

	protected Random randint;
	protected int moveCount=0;
	protected int direction;
	
	public Enemy(Handler handler, float x, float y) {
		super(handler, x, y, CreatureBase.DEFAULT_CREATURE_WIDTH, CreatureBase.DEFAULT_CREATURE_HEIGHT);
		bounds.x=8*2;
		bounds.y=18*2;
		bounds.width=16*2;
		bounds.height=14*2;
		speed=2f;
		health= 60;

		SkelyCam= new Rectangle();

		randint = new Random();
		direction = randint.nextInt(4) + 1;

		animDown = new Animation(animWalkingSpeed, Images.Enemy_front);
		animLeft = new Animation(animWalkingSpeed,Images.Enemy_left);
		animRight = new Animation(animWalkingSpeed,Images.Enemy_right);
		animUp = new Animation(animWalkingSpeed,Images.Enemy_back);

		Skelyinventory= new Inventory(handler);
	}

	@Override
	public void tick() {
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		
		checkMove();
		move();

		moveCount ++;
		if(moveCount>=60){
			moveCount=0;
			direction = randint.nextInt(4) + 1;
		}

		if(isBeinghurt()){
			healthcounter++;
			if(healthcounter>=120){
				setBeinghurt(false);
			}
		}
		if(healthcounter>=120 && !isBeinghurt()){
			healthcounter=0;
		}

		Skelyinventory.tick();
	}
	
	@Override
	public void move() {
        if(!checkEntityCollisions(xMove, 0f))
            moveX();
        if(!checkEntityCollisions(0f, yMove))
            moveY();
	}
	
	public void checkMove() {
		SkelyCam.x = (int) (x - handler.getGameCamera().getxOffset() - (64 * 3));
		SkelyCam.y = (int) (y - handler.getGameCamera().getyOffset() - (64 * 3));
		SkelyCam.width = 64 * 7;
		SkelyCam.height = 64 * 7;
		
		if (SkelyCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset())
				|| SkelyCam.contains(handler.getWorld().getEntityManager().getPlayer().getX() - handler.getGameCamera().getxOffset() + handler.getWorld().getEntityManager().getPlayer().getWidth(), handler.getWorld().getEntityManager().getPlayer().getY() - handler.getGameCamera().getyOffset() + handler.getWorld().getEntityManager().getPlayer().getHeight())) {

			Rectangle cb = getCollisionBounds(0, 0);
			Rectangle ar = new Rectangle();
			int arSize = 13;
			ar.width = arSize;
			ar.height = arSize;

			if (lu) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y - arSize;
				
			} else if (ld) {
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;

			} else if (ll) {
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;

			} else if (lr) {
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;

			}
			
			for (EntityBase e : handler.getWorld().getEntityManager().getEntities()) {
				if (e.equals(this))
					continue;
				if (e.getCollisionBounds(0, 0).intersects(ar) && e.equals(handler.getWorld().getEntityManager().getPlayer())) {
					checkAttacks();
					return;
				}
			}
			
			if (x >= handler.getWorld().getEntityManager().getPlayer().getX() - 8 && x <= handler.getWorld().getEntityManager().getPlayer().getX() + 8) {//nada
				xMove = 0;
			} else if (x < handler.getWorld().getEntityManager().getPlayer().getX()) { //move right
				xMove = speed;
				
			} else if (x > handler.getWorld().getEntityManager().getPlayer().getX()) {//move left
				xMove = -speed;
			}

			if (y >= handler.getWorld().getEntityManager().getPlayer().getY() - 8 && y <= handler.getWorld().getEntityManager().getPlayer().getY() + 8) {//nada
				yMove = 0;
			} else if (y < handler.getWorld().getEntityManager().getPlayer().getY()) {//move down
				yMove = speed;

			} else if (y > handler.getWorld().getEntityManager().getPlayer().getY()) {//move up
				yMove = -speed;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(animDown,animUp,animLeft,animRight,Images.SkelyEnemy_front,Images.SkelyEnemy_back,Images.SkelyEnemy_left,Images.SkelyEnemy_right), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		g.setColor(Color.BLACK);
		g.drawRect((int)(x-handler.getGameCamera().getxOffset()-1),(int)(y-handler.getGameCamera().getyOffset()-21),51,11);
		if(this.getHealth() >=40){
			g.setColor(Color.GREEN);
			g.fillRect((int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-20),getHealth(),10);

		}else if(this.getHealth()>=20 && getHealth()<40){
			g.setColor(Color.YELLOW);
			g.fillRect((int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-20),getHealth(),10);

		}else if(this.getHealth() < 20){
			g.setColor(Color.RED);
			g.fillRect((int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-20),getHealth(),10);

		}
		g.setColor(Color.white);
		g.drawString("Health: " + getHealth(),(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-22));
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.skullItem.createNew((int)x + bounds.x,(int)y + bounds.y,1));
	}
}
