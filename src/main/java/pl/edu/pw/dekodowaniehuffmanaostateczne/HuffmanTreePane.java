package pl.edu.pw.dekodowaniehuffmanaostateczne;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class HuffmanTreePane extends Pane {
    private final double radius = 15;
    private final double verticalSpace = 50;

    public void displayTree(Tree tree) {
        setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));

        getChildren().clear();

        double width = 500 * tree.getLevels();

        if (tree.getRoot() != null)
            displayTree(tree.getRoot(), width / 2, verticalSpace, width / 4, Color.HOTPINK);
    }

    private void displayTree(HuffmanNode root, double x, double y, double horizontalSpace, Color color) {
        double newX, newY;

        if (root.hasLeftChild()) {
            newX = x - horizontalSpace;
            newY = y + verticalSpace;

            getChildren().addAll(new Line(newX, newY, x, y), new Text((newX + x) / 2, (newY + y) / 2, "0"));
            displayTree(root.leftChild(), newX, newY, horizontalSpace / 2, color);
        }


        if (root.hasRightChild()) {
            newX = x + horizontalSpace;
            newY = y + verticalSpace;

            getChildren().addAll(new Line(newX, newY, x, y), new Text((newX + x) / 2, (newY + y) / 2, "1"));
            displayTree(root.rightChild(), newX, newY, horizontalSpace / 2, color);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        circle.setStroke(Color.LIGHTPINK);

        Text text = new Text(x - 4, y + 4, String.valueOf(root.character()));

        getChildren().addAll(circle, text);
    }
}
