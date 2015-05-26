package com.rasalhague.noise_trigger_server;

import com.rasalhague.noise_trigger_server.client.Client;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ClientInputEventHandler implements Client.ClientInputListener
{
    private static final String COMMAND_NEXT    = "Next";
    private static final String COMMAND_PREVIEW = "Preview";

    private Robot robot;

    private ClientInputEventHandler()
    {
        try
        {
            robot = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }

    public static ClientInputEventHandler getInstance()
    {
        return ClientInputEventHandlerHolder.instance;
    }

    @Override
    public void inputLineEvent(String inputLine)
    {
        switch (inputLine)
        {
            case COMMAND_NEXT:
            {
                performRightPress();

                break;
            }
            case COMMAND_PREVIEW:
            {
                performLeftClick();

                break;
            }
        }
    }

    private void performLeftClick()
    {
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);

        System.out.println("VK_LEFT");
    }

    private void performRightPress()
    {
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);

        System.out.println("VK_RIGHT");
    }

    private static class ClientInputEventHandlerHolder
    {
        static ClientInputEventHandler instance = new ClientInputEventHandler();
    }
}
