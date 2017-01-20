package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires.wireComponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;


public class Arrow extends Polygon {

    Line line;
    Rotate rz;
    double angle;

    public Arrow(Line line) {

        /* create a new Polygon */
        super(0, -4, 5, 13, -5, 13);
        this.line = line;
        init();
    }

    private void init() {

        /* the arrowhead color */
        setFill(Color.BLACK);

        /* set a rotate */
        rz = new Rotate();
        {
            rz.setAxis(Rotate.Z_AXIS);
        }
        getTransforms().addAll(rz);

        /* update the rotation of arrow head */
        update();
    }

    public void update() {

        /* relocate the arrowhead */
        setTranslateX(line.getStartX());
        setTranslateY(line.getStartY());

        /* we need to determine the Hypotenuse */
        double a = line.getStartY() - line.getEndY();
        double b = line.getEndX() - line.getStartX();

        /* the lenght of it, using the "Satz des Phytagoras" */
        double c = Math.sqrt((a * a) + (b * b));

        /* now we can determine the angle with sinus*/
        angle = Math.asin(a / c);

        /* we need the angle in degrees */
        angle = Math.toDegrees(angle);

        /* we rotate it always by 90 and add the angle depending on arrow direction */
        if (line.getStartX() >= line.getEndX()) {
            angle = 90 + angle;
        }

        if (line.getStartX() < line.getEndX()) {
            angle = -90 + (angle * -1);
        }

        /* rotate the arrow */
        rz.setAngle(angle);

    }
}
