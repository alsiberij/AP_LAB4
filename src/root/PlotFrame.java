package root;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class PlotFrame extends JFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private JFileChooser fileChooser = null;

    private JCheckBoxMenuItem showAxisMI;
    private JCheckBoxMenuItem showMarkersMI;

    private GraphicsDisplay display = new GraphicsDisplay();

    private boolean fileLoaded = false;

    private class PlotMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
            showAxisMI.setEnabled(fileLoaded);
            showMarkersMI.setEnabled(fileLoaded);
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }

    public PlotFrame() {
        super("Построение графиков функций на основе подготовленных файлов");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        setExtendedState(MAXIMIZED_BOTH);

        //TODO
    }
}
