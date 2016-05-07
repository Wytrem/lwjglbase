package net.wytrem.lwjglbase;


import java.io.File;
import java.io.IOException;

import org.lwjgl.LWJGLUtil;


public class StartLwjgl
{
    /**
     * Starts the game.
     */
    public static void main(String[] args)
    {
        if (!init())
        {
            System.err.println("Couldn't load LWJGL, aborting.");
            return;
        }
        
        LwjglBase.instance = new LwjglBase();
        LwjglBase.instance.start();
    }

    private static boolean init()
    {
        try
        {
            LWJGLSetup.load(getBaseWorkingDirectory());
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    private static File getBaseWorkingDirectory()
    {
        String identifier = "lwjgl";
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (LWJGLUtil.getPlatform())
        {
            case LWJGLUtil.PLATFORM_LINUX:
                workingDirectory = new File(userHome, "." + identifier + "/");
                break;
            case LWJGLUtil.PLATFORM_WINDOWS:
                final String applicationData = System.getenv("APPDATA");
                final String folder = applicationData != null ? applicationData : userHome;

                workingDirectory = new File(folder, "." + identifier + "/");
                break;
            case LWJGLUtil.PLATFORM_MACOSX:
                workingDirectory = new File(userHome, "Library/Application Support/" + identifier);
                break;
            default:
                workingDirectory = new File(userHome, identifier + "/");
        }

        return workingDirectory;
    }
}
