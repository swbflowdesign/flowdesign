package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire;

import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.FlowProperties;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class WireDataType extends Pane {

    private Text dataType;
    private Graph graph;
    private Line line;
    private double angle;
    private Connector sourceConnector;
    private Connector targetConnector;
    private Tooltip tooltip;

    public WireDataType(Graph graph, Line line, Connector sourceConnector, Connector targetConnector) {

        this.line = line;
        dataType = new Text("( )");
        tooltip = new Tooltip("Impliziete Datentyp Konvertierung, \n da der Ausgangs Daytentyp ungleich dem Eingangs Datentyp ist.");
        this.sourceConnector = sourceConnector;
        this.targetConnector = targetConnector;
        this.graph = graph;

        dataType.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {

                    //Implicit dataType conversion: change input or output depending on where the user clicks
                    if (!sourceConnector.getDataType().equals(targetConnector.getDataType())) {
                        //temp Text for length
                        Text temp = new Text(sourceConnector.getDataType());
                        Text temp2 = new Text(sourceConnector.getDataType() + " / ");
                        //Output dataType
                        if (event.getX() > dataType.getX() && event.getX() < (temp.getLayoutBounds().getWidth())) {
                            //can't change the output of a Join or Split
                            if (sourceConnector.getElement().getElementType() == nodeType.fd_Split || sourceConnector.getElement().getElementType() == nodeType.fd_Join) {

                            } else {
                                FlowProperties.showDataDialog(sourceConnector);
                            }
                        }
                        //Input dataType
                        else if (event.getX() < (dataType.getX() + dataType.getLayoutBounds().getWidth()) && event.getX() > (temp2.getLayoutBounds().getWidth())) {
                            FlowProperties.showDataDialog(targetConnector);
                        }

                    }
                    //Change both input and output
                    else {
                        //can't change the output of a Join or Split
                        if (sourceConnector.getElement().getElementType() == nodeType.fd_Split || sourceConnector.getElement().getElementType() == nodeType.fd_Join) {
                            FlowProperties.showDataDialog(targetConnector);
                        } else {
                            FlowProperties.showDataDialog(sourceConnector);
                            targetConnector.setDataType(sourceConnector.getDataType());
                        }
                    }
                }

            }

        });

        updateData();
        updatePos();

    }

    public void updatePos() {

        dataType.setTranslateX(((line.getStartX() + (line.getEndX() - line.getStartX()) / 2)) - dataType.getLayoutBounds().getWidth() / 2);
        dataType.setTranslateY(((line.getStartY() + (line.getEndY() - line.getStartY()) / 2) - 10));

        /* we need to determine the Hypotenuse */
        double a = line.getStartY() - line.getEndY();
        double b = line.getEndX() - line.getStartX();

        /* the lenght of it, using the "Satz des Phytagoras" */
        double c = Math.sqrt((a * a) + (b * b));

        /* now we can determine the angle with sinus*/
        angle = Math.asin(a / c);

        /* we need the angle in degrees */
        angle = Math.toDegrees(angle);

        /* we rotate it depending on the direction */
        if (line.getStartX() <= line.getEndX()) {
            angle = -1 * angle;
        }

        dataType.setRotate(angle);

    }

    public void updateData() {

        //Set the Input or Output DataType
        if (sourceConnector.getDataType().length() > 0 && targetConnector.getDataType().length() > 0) {
            //Implicit conversion
            if (!sourceConnector.getDataType().equals(targetConnector.getDataType())) {
                dataType.setText("" + sourceConnector.getDataType() + " / " + targetConnector.getDataType());
                dataType.setFill(Color.RED);
                Tooltip.install(dataType, tooltip);
            }
            //Same DataType on both sides
            else {
                dataType.setText("" + targetConnector.getDataType());
                dataType.setFill(Color.BLACK);
                Tooltip.uninstall(dataType, tooltip);
            }
        }
        //Set the Input equal to the Output
        else if (sourceConnector.getDataType().length() > 0 && targetConnector.getDataType().length() == 0) {
            dataType.setText("" + sourceConnector.getDataType());
            targetConnector.setDataType(sourceConnector.getDataType());
            dataType.setFill(Color.BLACK);
            Tooltip.uninstall(dataType, tooltip);
        }
        //Set the Output equal to the Input
        else if (targetConnector.getDataType().length() > 0 && sourceConnector.getDataType().length() == 0) {
            dataType.setText("" + targetConnector.getDataType());
            sourceConnector.setDataType(targetConnector.getDataType());
            dataType.setFill(Color.BLACK);
            Tooltip.uninstall(dataType, tooltip);
        }

    }

    public Text getDataType() {
        return dataType;
    }

    public void setDataType(String newType) {
        dataType.setText(newType);
    }


}
