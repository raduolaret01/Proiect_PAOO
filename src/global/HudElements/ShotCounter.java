package global.HudElements;

import global.*;

public class ShotCounter extends GraphicObject {

    public ShotCounter(int x, int y, int w, int h){
        super(46, x, y, w, h);
        for(int i = 0; i < 3; ++i){
            shotsTiles[i] = TileFactory.MakeTile("Cartridge",posX + 38 - 16*i,posY + 4,16,16);
        }
    }

    //Default size: 120 * 84
    public ShotCounter(int x, int y){
        super(46,x,y,120, 84);
        for(int i = 0; i < 3; ++i){
            shotsTiles[i] = TileFactory.MakeTile("Cartridge",posX + 76 - 32 * i,posY + 8,32,32);
        }
    }

    private Tile[] shotsTiles = new Tile[3];
    private int misses;

    @Override
    public void draw() {
        Renderer.getInstance().DrawObject(this);
        for(Tile shot : shotsTiles){
            shot.draw();
        }
    }

    @Override
    public void update() {
        if(Game.getDucksShot() == 0){
            misses++;
        }
        if(misses > 3){
            misses = 0;
        }
        int i = 0;
        while(i < misses){
            shotsTiles[i] = TileFactory.MakeTile("Black",posX + 76 - 32 * i,posY + 8,32,32);
            ++i;
        }
        while(i < 3){
            shotsTiles[i] = TileFactory.MakeTile("Cartridge",posX + 76 - 32 * i,posY + 8,32,32);
            ++i;
        }
    }
}
