package Worlds;
import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Statics.*;
import Main.Handler;

/**
 * Created by Elemental on 1/2/2017.
 */
public class World1 extends BaseWorld{

    public World1(Handler handler, String path, Player player){
        super(handler,path,player);
        this.handler = handler;
        this.quest.put("Stick", 2);
        this.quest.put("Rock", 2);
        this.quest.put("Wood", 2);
        this.quest.put("Skull", 1);
        
        this.newWorld = new CaveWorld(handler,"res/Maps/caveMap.map",player);

        entityManager.addEntity(new Tree(handler, 100, 250));
        entityManager.addEntity(new Tree(handler, 77, 700));
        entityManager.addEntity(new Tree(handler, 533, 276));
        entityManager.addEntity(new Tree(handler, 765, 888));
        
        entityManager.addEntity(new Rock(handler, 100, 450));
        entityManager.addEntity(new Rock(handler, 684, 1370));
        entityManager.addEntity(new Rock(handler, 88, 1345));
        entityManager.addEntity(new Rock(handler, 700, 83));
        
        entityManager.addEntity(new Bush(handler, 350, 450));
        entityManager.addEntity(new Bush(handler, 550, 750));
        entityManager.addEntity(new Bush(handler, 1200, 450));
        entityManager.addEntity(new Bush(handler, 1220, 750));
        
        entityManager.addEntity(new ClosedChest(handler, 200, 64, quest));
        entityManager.addEntity(new Door(handler, 100, 0, newWorld));      
        entityManager.addEntity(new SkelyEnemy(handler, 800	, 800));
        
        
        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }
}