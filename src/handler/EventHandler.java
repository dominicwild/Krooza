package handler;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;

/**
 * A class that was created in order to get around the fact that JSFML can only
 * poll events on every frame once. Otherwise errors are thrown. This class
 * congregates this data for easy use by other classes. - Dominic Wild
 */
public final class EventHandler {

    //Window
    private RenderWindow mainWindow;

    //Mouse Events
    private boolean mouseDownL,
            mouseReleasedL,
            mouseDownR,
            mouseReleasedR,
            mouseWheelMoved,
            mouseWheelDown,
            mouseWheelReleased;
    private int mouseWheelDelta;

    //Keyboard Events
    private ArrayList<Keyboard.Key> keyDownCode,
            keyReleasedCode;
    private boolean keyDown,
            keyReleased;

    //Window Events
    private boolean windowClose,
            windowOutFocus;



    /**
     * Constructor
     */
    public EventHandler(RenderWindow window) {
        this.mainWindow = window;
    }

    public void handleEvents() {
        //Reset Values
        this.resetValues();
        //Get Current Values
        for (Event e : this.mainWindow.pollEvents()) {
            this.handleMouseEvents(e);
            try {
                this.handleKeyboardEvents((KeyEvent) e);
            } catch (ClassCastException caseError) {
            }
            this.handleWindowEvents(e);
        }
    }

    private void resetValues() {

        //Mouse
        this.mouseDownL = false;
        this.mouseReleasedL = false;
        this.mouseDownR = false;
        this.mouseReleasedR = false;
        this.mouseWheelMoved = false;
        this.mouseWheelDown = false;
        this.mouseWheelReleased = false;
        this.mouseWheelDelta = 0;

        //Keyboard
        this.keyDown = false;
        this.keyReleased = false;
        this.keyDownCode = new ArrayList<Keyboard.Key>();
        this.keyReleasedCode = new ArrayList<Keyboard.Key>();

        //Window
        this.windowClose = false;
        this.windowOutFocus = false;

    }

    private void handleMouseEvents(Event e) {

        if (e.type == Event.Type.MOUSE_BUTTON_PRESSED && e.asMouseButtonEvent().button == Mouse.Button.LEFT) {
            this.mouseDownL = true;
            this.mouseReleasedL = false;
        }

        if (e.type == Event.Type.MOUSE_BUTTON_RELEASED && e.asMouseButtonEvent().button == Mouse.Button.LEFT) {
            this.mouseDownL = false;
            this.mouseReleasedL = true;
        }

        if (e.type == Event.Type.MOUSE_BUTTON_PRESSED && e.asMouseButtonEvent().button == Mouse.Button.RIGHT) {
            this.mouseDownR = true;
            this.mouseReleasedR = false;
        }

        if (e.type == Event.Type.MOUSE_BUTTON_RELEASED && e.asMouseButtonEvent().button == Mouse.Button.RIGHT) {
            this.mouseDownR = false;
            this.mouseReleasedR = true;
        }

        if (e.type == Event.Type.MOUSE_BUTTON_PRESSED && e.asMouseButtonEvent().button == Mouse.Button.MIDDLE) {
            this.mouseWheelDown = true;
            this.mouseWheelReleased = false;
        }

        if (e.type == Event.Type.MOUSE_BUTTON_RELEASED && e.asMouseButtonEvent().button == Mouse.Button.MIDDLE) {
            this.mouseWheelDown = false;
            this.mouseWheelReleased = true;
        }

        if (e.type == Event.Type.MOUSE_WHEEL_MOVED) {
            this.mouseWheelMoved = true;
            this.mouseWheelDelta = e.asMouseWheelEvent().delta;
        }



    }

    private void handleKeyboardEvents(KeyEvent e) {

        if (Keyboard.isKeyPressed(e.key)) {
            this.keyDownCode.add(e.key);
        }

        if (e.type == Event.Type.KEY_RELEASED) {
            this.keyReleasedCode.add(e.key);
        }

    }

    private void handleWindowEvents(Event e) {

        if (e.type == Event.Type.CLOSED) {
            this.windowClose = true;
        }

        if (e.type == Event.Type.LOST_FOCUS) {
            this.windowOutFocus = true;;
        }

    }

    //Getters
    public boolean isKeyDown(Keyboard.Key key) {
        for (Keyboard.Key keyDownCode1 : keyDownCode) {
            if (keyDownCode1 == key) {
                return true;
            }
        }
        return false;
    }

    public boolean isKeyHeldDown(Keyboard.Key key) {
        return Keyboard.isKeyPressed(key);
    }

    public boolean isKeyReleased(Keyboard.Key key) {
        for (int i = 0; i < keyReleasedCode.size(); i++) {
            if (this.keyReleasedCode.get(i) == key) {
                return true;
            }
        }

        return false;
    }

    public boolean isMouseWheelMoved() {
        return mouseWheelMoved;
    }

    public int getMouseWheelDelta() {
        return mouseWheelDelta;
    }

    public Vector2f getMousePosition() {
        return this.mainWindow.mapPixelToCoords(Mouse.getPosition(this.mainWindow));
    }

    public ArrayList<Keyboard.Key> getKeyDownCode() {
        return keyDownCode;
    }

    public ArrayList<Keyboard.Key> getKeyReleasedCode() {
        return keyReleasedCode;
    }

    public boolean isWindowClose() {
        return windowClose;
    }

    public boolean isWindowOutFocus() {
        return windowOutFocus;
    }

    public RenderWindow getMainWindow() {
        return mainWindow;
    }

    public boolean isMouseDownL() {
        return mouseDownL;
    }

    public boolean isMouseReleasedL() {
        return mouseReleasedL;
    }

    public boolean isMouseDownR() {
        return mouseDownR;
    }

    public boolean isMouseReleasedR() {
        return mouseReleasedR;
    }

    public boolean isMouseWheelDown() {
        return mouseWheelDown;
    }

    public boolean isMouseWheelReleased() {
        return mouseWheelReleased;
    }

    public boolean isKeyDown() {
        return keyDown;
    }

    public boolean isKeyReleased() {
        return keyReleased;
    }

}
