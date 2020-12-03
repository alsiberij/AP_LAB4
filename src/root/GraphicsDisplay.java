package root;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;

public class GraphicsDisplay extends JPanel {

    private Double[][] graphicsData;

    private boolean showAxis = true;
    private boolean showMarkers = false;

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
        graphicsStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, new float[]{10,3,6,3,6,3,10,3,3}, 0);
        axisStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, null, 0);
        markerStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, null, 0);
        axisFont = new Font("Serif", Font.BOLD, 36);
    }

    public void showGraphics(Double[][] graphicsData) {
        this.graphicsData = graphicsData;
        repaint();
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
        repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
        repaint();
    }

    protected Point2D.Double createPoint(double x, double y) {
        double deltaX = x - xMin;
        double deltaY = yMax - y;
        return new Point2D.Double(deltaX * scale, deltaY * scale);
    }

    protected Point2D.Double shiftPoint(Point2D.Double source, double deltaX, double deltaY) {
        Point2D.Double result = new Point2D.Double();
        result.setLocation(source.getX() + deltaX, source.getY() + deltaY);
        return result;
    }

    protected void paintGraphics(Graphics2D canvas){
        canvas.setStroke(graphicsStroke);
        canvas.setColor(Color.darkGray);
        GeneralPath graphics = new GeneralPath();
        for (int i = 0; i < graphicsData.length; i++) {
            Point2D.Double point = createPoint(graphicsData[i][0], graphicsData[i][1]);
            if (i > 0) {
                graphics.lineTo(point.getX(), point.getY());
            } else {
                graphics.moveTo(point.getX(), point.getY());
            }
        }
        canvas.draw(graphics);
    }

    protected void paintAxis(Graphics2D canvas) {
        canvas.setStroke(axisStroke);
        canvas.setColor(Color.BLACK);
        canvas.setPaint(Color.BLACK);
        canvas.setFont(axisFont);
        FontRenderContext context = canvas.getFontRenderContext();

        if (xMin <= 0 && xMax >= 0) {
            canvas.draw(new Line2D.Double(createPoint(0, yMax), createPoint(0, yMin)));
            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = createPoint(0, yMax);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() + 5, arrow.getCurrentPoint().getY() + 20);
            arrow.lineTo(arrow.getCurrentPoint().getX() - 10, arrow.getCurrentPoint().getY());
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);
            Rectangle2D bounds = axisFont.getStringBounds("Y", context);
            Point2D.Double labelPos = createPoint(0, yMax);
            canvas.drawString("Y", (float) labelPos.getX() + 10, (float) (labelPos.getY() - bounds.getY()));
        }

        if (yMin <= 0 && yMax >= 0) {
            canvas.draw(new Line2D.Double(createPoint(xMin, 0), createPoint(xMax, 0)));
            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = createPoint(xMax, 0);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() - 20, arrow.getCurrentPoint().getY() - 5);
            arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY() + 10);
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);
            Rectangle2D bounds = axisFont.getStringBounds("X", context);
            Point2D.Double labelPos = createPoint(xMax, 0);
            canvas.drawString("X", (float) (labelPos.getX() - bounds.getWidth() - 10), (float) (labelPos.getY() + bounds.getY()));
        }
    }

    protected void paintMarkers(Graphics2D canvas) {
        canvas.setStroke(markerStroke);
        canvas.setColor(Color.RED);
        canvas.setPaint(Color.RED);

        double avg = 0;
        for (int i = 0; i < graphicsData.length; i++) {
            avg += graphicsData[i][1];
        }
        avg /= graphicsData.length;

        for (Double[] point : graphicsData) {
            if (point[1] >= avg * 2) {
                canvas.setColor(Color.RED);
                canvas.setPaint(Color.RED);
            } else {
                canvas.setColor(Color.BLUE);
                canvas.setPaint(Color.BLUE);
            }

            canvas.setStroke(markerStroke);
            Point2D.Double center = createPoint(point[0], point[1]);
            canvas.draw(new Line2D.Double(shiftPoint(center, -7, 0), shiftPoint(center, 7, 0)));
            canvas.draw(new Line2D.Double(shiftPoint(center, 0, 7), shiftPoint(center, 0, -7)));
            canvas.draw(new Line2D.Double(shiftPoint(center, 7, 7), shiftPoint(center, -7, -7)));
            canvas.draw(new Line2D.Double(shiftPoint(center, -7, 7), shiftPoint(center, 7, -7)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (graphicsData == null || graphicsData.length == 0) {
            return;
        }

        xMin = graphicsData[0][0];
        xMax = graphicsData[graphicsData.length - 1][0];
        yMin = graphicsData[0][1];
        yMax = yMin;

        for (int i = 1; i < graphicsData.length; i++) {
            if (graphicsData[i][1] < yMin) {
                yMin = graphicsData[i][1];
            }
            if (graphicsData[i][1] > yMax) {
                yMax = graphicsData[i][1];
            }
        }
        double scaleX = getSize().getWidth() / (xMax - xMin);
        double scaleY = getSize().getHeight() / (yMax - yMin);

        scale = Math.min(scaleX, scaleY);

        if (scale == scaleX) {
            double yInc = (getSize().getHeight() / scale - (yMax - yMin)) / 2;
            yMax += yInc;
            yMin -= yInc;
        }

        if (scale == scaleY) {
            double xInc = (getSize().getWidth() / scale - (xMax - xMin)) / 2;
            xMax += xInc;
            xMin -= xInc;
        }

        Graphics2D canvas = (Graphics2D) g;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();

        if (showAxis) {
            paintAxis(canvas);
        }

        paintGraphics(canvas);

        if (showMarkers) {
            paintMarkers(canvas);
        }

        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }
}
