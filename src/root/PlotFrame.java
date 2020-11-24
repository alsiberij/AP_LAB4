package root;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

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

        constructMenu();
        //TODO
    }

    void constructMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        Action openFileAction = new AbstractAction("Открыть файл") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showOpenDialog(PlotFrame.this) == JFileChooser.APPROVE_OPTION) {
                    //TODO openFile(fileChooser.getSelectedFile());
                }
            }
        };
        fileMenu.add(openFileAction);

        JMenu plotMenu = new JMenu("График");
        menuBar.add(plotMenu);

        Action showAxisAction = new AbstractAction("Показать оси координат") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setShowAxis(showAxisMI.isSelected());
            }
        };
        showAxisMI = new JCheckBoxMenuItem(showAxisAction);
        plotMenu.add(showAxisMI);
        showAxisMI.setSelected(true);

        Action showMarkersAction = new AbstractAction("Показать маркеры точек") {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setShowMarkers(showMarkersMI.isSelected());
            }
        };
        showMarkersMI = new JCheckBoxMenuItem(showMarkersAction);
        plotMenu.add(showMarkersMI);
        showMarkersMI.setSelected(false);
        plotMenu.addMenuListener(new PlotMenuListener());
    }
}
