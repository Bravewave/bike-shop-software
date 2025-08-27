package src;



import src.gui.GUIFrame;
import src.gui.misc.UtilitiesFrame;
import src.gui.panels.HomePanel;

// Initial entry point for the application
// sorry in advance for anyone that has to read through this code :(
public class App {

    public static void main(String[] args) {
        GUIFrame gui = new GUIFrame(new InteractionHandler());
        gui.navigateTo(new HomePanel(gui));
    }
}
