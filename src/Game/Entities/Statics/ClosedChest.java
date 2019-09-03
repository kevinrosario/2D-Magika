package Game.Entities.Statics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JOptionPane;

import Game.Entities.Creatures.Player;
import Game.GameStates.GameState;
import Game.GameStates.MenuState;
import Game.GameStates.State;
import Game.Items.Item;
import Game.Tiles.Tile;
import Main.Game;
import Main.Handler;
import Resources.Images;

public class ClosedChest extends StaticEntity {

	public Boolean EP = false;
	private boolean isOpened = false; 
	private Rectangle ir = new Rectangle();
	private Map<String, Integer> quest;
	private GameState gameState;

	public ClosedChest(Handler handler, float x, float y, Map<String, Integer> quest) {
		super(handler, x, y, Tile.TILEHEIGHT, 96);
		this.quest = quest;

		health= 100;
		bounds.x= 0;
		bounds.y= 0;
		bounds.width = 96;
		bounds.height = 64;

		ir.width = bounds.width;
		ir.height = bounds.height;

		int irx=(int)(bounds.x-handler.getGameCamera().getxOffset( )+x);
		int iry= (int)(bounds.y-handler.getGameCamera().getyOffset() +y +height);
		ir.y=iry;
		ir.x=irx;
	}

	@Override
	public void tick() {

		if(isBeinghurt()){
			setHealth(100);
		}

		if(handler.getKeyManager().attbut){
			EP = true;

		} else if(!handler.getKeyManager().attbut){
			EP = false;
		}
	}

	@Override
	public void render(Graphics g) {
		if(isOpened) {
			g.drawImage(Images.chest[1],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}else {
			g.drawImage(Images.chest[0],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);
		}

		g.setColor(Color.black);
		checkForPlayer(g, handler.getWorld().getEntityManager().getPlayer());
	}

	private void checkForPlayer(Graphics g, Player p) {
		Rectangle pr = p.getCollisionBounds(0,0);

		if(ir.contains(pr) && !EP && isOpened) {
			for(String name: quest.keySet()) {
				System.out.println(name + ": " + quest.get(name));
			}

			if(checkIfCountIsZero(quest.values())) {
				if(handler.getWorld().getEntityManager().getPlayer().worlds != 2) {
					handler.setShowDoor(true);
				} else {
					Object[] options = { "Exit Game", "Play Again" };
					new JOptionPane();
					int tryAgainLose = JOptionPane.showOptionDialog(null, "You defeated all enemies", "Game Finished", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
					if(tryAgainLose == 1) { //Try Again
						State.setState(handler.getGame().menuState);

					} else { // Exit Game
						System.exit(0);
					}
				}
			}
		} else if(ir.contains(pr) && EP){
			isOpened = true;
			for(String name : quest.keySet()) {
				for(Item item: handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems()) {
					if(name.equals(item.getName())){
						if(quest.get(name) > 0) {
							quest.put(name, quest.get(name)-1);
							item.setCount(item.getCount()-1);
						}
					} 
				}
			}
		}    
	}

	@Override
	public void die() {
	}
}
