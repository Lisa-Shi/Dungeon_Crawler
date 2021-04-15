package sample;

import java.util.ArrayList;

public class PolygonWireframe {
    private ArrayList<Vector2D> vertices;
    private ArrayList<Vector2D> edges;
    private Vector2D center;

    // Constructors
    public PolygonWireframe() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        center = new Vector2D(0, 0);
    }

    // https://stackoverflow.com/questions/1119627/how-to-test-if-a-point-is-inside-of-a-convex-polygon-in-2d-integer-coordinates
    public boolean containsPoint(Vector2D relativePoint) {
        int pos = 0;
        int neg = 0;

        for (int i = 0; i < vertices.size(); i++) {
            int indexOfNextVertex = i + 1;
            if (indexOfNextVertex >= vertices.size()) {
                indexOfNextVertex = 0;
            }

            double x1 = vertices.get(i).getX();
            double y1 = vertices.get(i).getY();
            double x2 = vertices.get(indexOfNextVertex).getX();
            double y2 = vertices.get(indexOfNextVertex).getY();

            double x = relativePoint.getX();
            double y = relativePoint.getY();

            double cross = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);

            if (cross > 0) {
                pos++;
            }

            if (cross < 0) {
                neg++;
            }

            if (pos > 0 && neg > 0) {
                return false;
            }
        }

        return true;
    }
    public void generate() {
        generateCenter();
        generateEdges();
    }
    private void generateCenter() {
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < getVertices().size(); i++) {
            sumX += getVertices().get(i).getX();
            sumY += getVertices().get(i).getY();
        }
        center.setX(sumX / getVertices().size());
        center.setY(sumY / getVertices().size());
    }
    private void generateEdges() {
        for (int i = 0; i < getVertices().size(); i++) {
            // Find the bordering vertex to draw an edge to
            int borderingVertexIndex = i + 1;
            if (i + 1 == getVertices().size()) {
                borderingVertexIndex = 0;
            }
            Vector2D borderingVertex = getVertices().get(borderingVertexIndex);

            Vector2D edge = borderingVertex.subtract(getVertices().get(i));
            edges.add(edge);
        }
    }

    public ArrayList<Vector2D> getVertices() {
        return vertices;
    }
    public ArrayList<Vector2D> getEdges() {
        return edges;
    }
    public Vector2D getCenter() {
        return center;
    }

    public void addVertex(Vector2D vertex) {
        vertices.add(vertex);
    }
}
