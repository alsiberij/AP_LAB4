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

    public GraphicsDisplay() {
        setBackground(Color.WHITE);
        graphicsStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, null, 0);
        axisStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, null, 0);
        markerStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, null, 0);
        axisFont = new Font("Serif", Font.BOLD, 36);
    }

    
}
