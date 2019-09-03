package Worlds;

import Game.Entities.Creatures.Enemy;
import Game.Entities.Creatures.Player;
import Game.Entities.Creatures.SkelyEnemy;
import Game.Entities.Statics.Bush;
import Game.Entities.Statics.ClosedChest;
import Game.Entities.Statics.Rock;
import Game.Entities.Statics.Tree;
import Main.Handler;

public class World2 extends BaseWorld {

    private Handler handler;
    private Player player;

    public World2(Handler handler, String path, Player player) {
        super(handler,path,player);
        this.handler = handler;
        this.player = player;
        
        this.quest.put("Stick", 3);
        this.quest.put("Rock", 4);
        this.quest.put("Wood", 4);
        this.quest.put("Skull", 4);
        
        entityManager.addEntity(new Tree(handler, 1449, 900));
        entityManager.addEntity(new Tree(handler, 634, 618));
        entityManager.addEntity(new Tree(handler, 415, 1735));
        entityManager.addEntity(new Tree(handler, 1066, 1598));

        entityManager.addEntity(new Rock(handler, 1251, 1788));
        entityManager.addEntity(new Rock(handler, 783, 1719));
        entityManager.addEntity(new Rock(handler, 496, 960));
        entityManager.addEntity(new Rock(handler, 1452, 1376));
        entityManager.addEntity(new Rock(handler, 1333, 1215));
        entityManager.addEntity(new Rock(handler, 1766, 1755));


        entityManager.addEntity(new Bush(handler, 52, 1791));
        entityManager.addEntity(new Bush(handler, 880, 1791));
        entityManager.addEntity(new Bush(handler, 1551, 1215));
        entityManager.addEntity(new Bush(handler, 847, 383));
        entityManager.addEntity(new Bush(handler, 1542, 1572));
        
        entityManager.addEntity(new SkelyEnemy(handler, 226	, 412));
        entityManager.addEntity(new SkelyEnemy(handler, 1449, 1146));
        entityManager.addEntity(new SkelyEnemy(handler, 1600, 1712));
        
        entityManager.addEntity(new Enemy(handler, 1066, 1733));
        entityManager.addEntity(new Enemy(handler, 243, 1027));
        entityManager.addEntity(new Enemy(handler, 634, 799));
        entityManager.addEntity(new Enemy(handler, 1567, 1712));
        
        entityManager.addEntity(new ClosedChest(handler, 200, 64, quest));
    }
}
