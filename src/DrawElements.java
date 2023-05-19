import java.util.ArrayList;

/**
 * A helper class used for drawing an arraylist of the elements
 * @param <T> the type of the elements to be drawn
 */
public class DrawElements<T extends Element> {

    /**
     * Draw all elements in an ArrayList
     * @param collection ArrayList of elements to be drawn
     */
    protected void drawAll(ArrayList<T> collection) {
        for (T element : collection) {
            element.draw();
        }
    }
}
