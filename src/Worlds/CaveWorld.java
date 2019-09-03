package Worlds;
import Game.Entities.Creatures.Enemy;
import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Statics.ClosedChest;
import Game.Entities.Statics.Door;
import Main.Handler;

/**
 * Created by Elemental on 2/10/2017.
 */
public class CaveWorld extends BaseWorld{
    private Handler handler;
    private Player player;

    public CaveWorld(Handler handler, String path, Player player) {
        super(handler, path, player);
        this.handler = handler;
        this.player = player;
        this.newWorld = new World2(handler, "res/Maps/Map2.map", player);
        this.quest.put("Skull", 1);
        
        entityManager.addEntity(new Door(handler, 100, 0, newWorld));
        entityManager.addEntity(new ClosedChest(handler,250, 64, quest));
        entityManager.addEntity(new Enemy(handler, 800, 800));
    }
}
