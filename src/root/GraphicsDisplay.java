package root;

import javax.swing.*;
import java.awt.*;

public class GraphicsDisplay extends JPanel {

    private Double[][] graphicsData;

    private boolean showAxis = true;
    private boolean showMarkers = true;

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    private double scale;

    private BasicStroke graphicsStroke;
    private BasicStroke axisStroke;
    private BasicStroke markerStroke;

    private Font axisFont;

    
}
