package global;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class LeaderboardMenu extends Menu {

    private Tile backdropPanel = new Tile(69,708,400,504, 440);
    private ScoreEntry[] scoreTable;
    private Tile[][] boardTiles;

    @Override
    protected void init() {
        buttons = new Button[1];
        this.window = Application.getWindow();
        scoreTable = DataManager.getScores();
        boardTiles = new Tile[DataManager.getNumberOfScoreEntries()][12];
        createTable();

        buttons[0] = TileFactory.MakeButton("Back",870, 820, 180,100);
        background = TileFactory.MakeBGTile((int)(Math.random() * 2d) + 1);
        title = new Tile(131, 600,80,800,300);

        pressedButton = -1;

        int tens = 1;


        // Setup key callbacks.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Setup button click callback
        glfwSetMouseButtonCallback(window, (window, button, action, mods) ->{
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS){
                if(buttons[0].mouseOver(pointer.posX+25, pointer.posY+25)){
                    pressedButton = 0;
                }
            }
        });
    }

    @Override
    protected int loop() {
        // Set the clear color
        glClearColor(1f, 1f, 1f, 1.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            Timer.setDeltaTime();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            background.draw();
            backdropPanel.draw();
            title.draw();

            buttons[0].draw();

            for(Tile[] tileSet : boardTiles){
                for(Tile tile : tileSet){
                    if(tile != null){
                        tile.draw();
                    }
                }
            }

            pointer.draw();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events.
            glfwPollEvents();
            switch (pressedButton) {
                case -1:
                    break;
                case 0:
                    return 0;
                default:
                    throw new IllegalStateException("Illegal pressedButton value at NewTopScoreMenu: " + pressedButton);
            }
        }
        return 0;
    }

    private void createTable(){
        for(int i = 0; i < boardTiles.length; ++i){
            for(int j = 0; j < scoreTable[i].name.length(); ++j){
                boardTiles[i][j] = TileFactory.MakeSBTile((char)(scoreTable[i].name.charAt(j) + 32),760 + 32 * j, 480 + 32 * i, 32, 32);
            }
            int tens = 1;
            for(int j = 11; j >= 6; --j){
                int temp = (scoreTable[i].score / tens) % 10;
                boardTiles[i][j] = TileFactory.MakeSBTile((char)(temp + 48), 776 + 32 * j, 480 + 32 * i,32,32);
                tens *= 10;
            }

        }
    }
}
