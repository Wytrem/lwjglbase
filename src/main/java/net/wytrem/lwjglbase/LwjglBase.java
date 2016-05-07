package net.wytrem.lwjglbase;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class LwjglBase
{
    public static LwjglBase instance;

    private int displayWidth = 840;
    private int displayHeight = 480;

    private boolean isRunning;

    public void start()
    {
        // Init stuff...
        try
        {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            Display.setTitle("lwjgl base");
            Display.create();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

        // Called once to sync GL with display mode
        resize();

        // Enable alpha blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);

        // Do not forget that...
        isRunning = true;

        // Running
        run();
    }

    /**
     * Runs the main game loop.
     */
    private void run()
    {
        while (isRunning)
        {
            isRunning = !(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE));
            updateAll();

            Display.update();
            Display.sync(30);
        }

        System.exit(0);
    }

    int t = 0;

    private void updateAll()
    {
        t++;

        if (Display.wasResized())
        {
            displayWidth = Display.getWidth();
            displayHeight = Display.getHeight();
            resize();
        }
    }

    public void resize()
    {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, displayWidth, displayHeight, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
