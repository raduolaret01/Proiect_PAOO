package global.AppStates.Game.Level.HudElements;

import global.*;
import global.AppStates.Game.Game;
import global.AppStates.Game.Level.Level;
import global.Systems.Renderer;
import global.Systems.TileFactory;

public class ShotCounter extends GraphicObject {

    //Default size: 120 * 84
    public ShotCounter(int x, int y){
        super(45,x,y,120, 84);
        for(int i = 0; i < 3; ++i){
            shotsTiles[i] = TileFactory.MakeGameTile("Cartridge",posX + 76 - 32 * i,posY + 8,32,32);
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
        if(Game.currentLevel.getDucksShot() == 0){
            misses++;
        }
        if(misses >= 3){
            misses = 3;
            Level.setGameOver();
        }
        int i = 0;
        while(i < misses){
            shotsTiles[i] = TileFactory.MakeGameTile("Black",posX + 76 - 32 * i,posY + 8,32,32);
            ++i;
        }
        while(i < 3){
            shotsTiles[i] = TileFactory.MakeGameTile("Cartridge",posX + 76 - 32 * i,posY + 8,32,32);
            ++i;
        }
    }
}
