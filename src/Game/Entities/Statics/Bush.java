package Game.Entities.Statics;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Game.Items.Item;
import Game.Tiles.Tile;
import Main.Handler;
import Resources.Images;

public class Bush extends StaticEntity {

    private File audioFile;
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private Random randInt;
    private int RNGR;

    public Bush(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILEHEIGHT, Tile.TILEWIDTH);
        
        bounds.x=0;
        bounds.y=0;
        bounds.width = 64;
        bounds.height = 64;
        health=16;

        try {
            audioFile = new File("res/music/Chopping.wav");
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            format = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.setMicrosecondPosition(2000);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void tick() {
        if(isBeinghurt()){
            audioClip.start();
        }
        if(!isBeinghurt() && !handler.getKeyManager().attbut){
            audioClip.stop();
        }
        if(!isActive()){
            audioClip.stop();
        }
    }

    @Override
    public void render(Graphics g) {
        renderLife(g);
        g.drawImage(Images.blocks[16],(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()),width,height,null);

    }

    @Override
    public void die() {
        randInt = new Random();
        RNGR = randInt.nextInt(1) + 1;
        if(RNGR==1){
            handler.getWorld().getItemManager().addItem(Item.stickItem.createNew((int)x + bounds.x + (randInt.nextInt(32) -32),(int)y + bounds.y+(randInt.nextInt(32) -32),(randInt.nextInt(3) +1)));
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
