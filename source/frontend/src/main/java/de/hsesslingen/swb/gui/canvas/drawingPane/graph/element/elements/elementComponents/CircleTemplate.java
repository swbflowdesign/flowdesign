package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleTemplate {

    Circle circle;
    double radius;


    public CircleTemplate(double radius) {
        this.radius = radius;

        /*Create new element*/
        circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        circle.setRadius(radius);
    }
}
