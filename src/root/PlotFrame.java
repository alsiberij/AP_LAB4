package root;

import javax.swing.*;

public class PlotFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private JFileChooser fileChooser = null;

    private JCheckBoxMenuItem showAxisMI;
    private JCheckBoxMenuItem showMarkersMI;

    private GraphicsDisplay display = new GraphicsDisplay();

    private boolean fileLoaded = false;

    
}
