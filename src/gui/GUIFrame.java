package src.gui;

import java.awt.*;
import javax.swing.*;

import src.InteractionHandler;
import src.gui.panels.RootPanel;

public class GUIFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private InteractionHandler interactionHandler;

    // keeps track of the last 10 pages the user has visited
    // might help with reducing load times for the back button
    // since the components are already created
    private Component[] navFlow = new Component[10];

    private Toolkit tk;
    private Dimension dim;

    private RootPanel rootPanel = new RootPanel(this);

    private void updateFlow(Component panel) {
        for (int i = 0; i < navFlow.length; i++) {
            if (navFlow[i] == null) {
                navFlow[i] = panel;
                break;
            }
        }
        // if the user has visited 10 pages, remove the first one
        if (navFlow[9] != null) {
            for (int i = 0; i < navFlow.length - 1; i++) {
                navFlow[i] = navFlow[i + 1];
            }
            navFlow[9] = null;
        }
    }

    private void changeTitle(String title) {
        if (title != null) {
            setTitle("Build-a-Bike Ltd. | " + title);
        }
    }

    public GUIFrame(InteractionHandler handler) {

        interactionHandler = handler;

        setTitle("Build-a-Bike Ltd.");

        tk = Toolkit.getDefaultToolkit();
        dim = tk.getScreenSize();

        setSize( dim.width / 2, dim.height / 2 );
        setLocation(dim.width / 4, dim.height / 4);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new FlowLayout());
        setBackground(Color.white);
        add(rootPanel);
        setJMenuBar(new MenuBar(this, interactionHandler));
        setVisible(true);
    }

    public void navigateTo(Component component) {
        // getContentPane().removeAll();
        // add(component);
        // setVisible(true);
        // repaint();
        // check if the name of the component matches the last non-null element in the navFlow array


        // update the flow if the name of the component is not the same as the last non-null element in the navFlow array
        if (getFlow() != null) {
            if (!getFlow().getName().equals(component.getName())) {
                // System.out.println("Updating flow...");
                updateFlow(component);
            }
        }
        else {
            // System.out.println("getFlow() is null");
            updateFlow(component);
        }
        // System.out.println(getCompleteFlow().toString());
        updateMenu();
        changeTitle(component.getName());
        rootPanel.navigateTo(component, component.getName());
        revalidate();
        repaint();
    }

    

    public void navigateBack(int index, String title) {
        if (navFlow[index] != null) {
            for (int i = index + 1; i < navFlow.length; i++) {
                navFlow[i] = null;
            }
            updateMenu();
            if (title == null) {
                title = (navFlow[index].getName());
            }

            changeTitle(title);
            rootPanel.navigateTo(navFlow[index], title);
            revalidate();
            repaint();
        }

    }

    public void navigateBack() {
        navigateBack(getFlowLength()-2, null);
    }

    public void updateMenu() {
        System.out.println("Updating menu...");
        setJMenuBar(new MenuBar(this, interactionHandler));
        revalidate();
        // repaint();
    }

    public Component getFlow() {
        for (int i = navFlow.length - 1; i >= 0; i--) {
            if (navFlow[i] != null) {
                return navFlow[i];
            }
        }
        return null;
    }

    public Component getFlow(int index) {
        for (int i = navFlow.length - 1; i >= 0; i--) {
            if (navFlow[i] != null) {
                if (i - index >= 0) {
                    return navFlow[i - index];
                }
            }
        }
        return null;
    }

    public Component[] getCompleteFlow() {
        return navFlow;
    }

    public int getFlowLength() {
        int length = 0;
        for (int i = 0; i < navFlow.length; i++) {
            if (navFlow[i] != null) {
                length++;
            }
        }
        return length;
    }

    public Toolkit getToolkit() {
        return Toolkit.getDefaultToolkit();
    }

    public Dimension getFrameSize() {
        System.out.printf("%s", this.getBounds().getSize());
        return this.getBounds().getSize();
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }
}
    
