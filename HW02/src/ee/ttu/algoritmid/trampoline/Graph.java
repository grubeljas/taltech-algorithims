package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<Point,Vertex> mapCoordinateVertex = new HashMap<>();
    HashMap<Point, List<Vertex>> connections = new HashMap<>();
    private Point startingPoint = new Point(0, 0);
    private Point maxPoint;

    private void updateEdges(Vertex vertex, List<Vertex> verticesPerRow, List<Vertex> verticesPerColumn) {
        if (connections.containsKey(vertex.coordinate)) {
            List<Vertex> from = connections.get(vertex.coordinate);
            for (Vertex f : from) {
                f.addDirectedEdge(vertex);
                verticesPerRow.remove(vertex);
                verticesPerColumn.remove(vertex);
            }
        }
    }

    private void addConnection(Vertex from, Point destination) {
        if (!connections.containsKey(destination)) {
            connections.put(destination, new ArrayList<>());
        }
        connections.get(destination).add(from);
    }

    public void create(Trampoline[][] map) {
        if (map.length == 0) return;
        int rows = map.length;
        int columns = map[0].length;

        ArrayList<Vertex> verticesPerRow = new ArrayList<>();
        ArrayList<ArrayList<Vertex>> verticesPerColumn = new ArrayList<>();
        for (int i = 0; i < columns; ++i) {
            verticesPerColumn.add(new ArrayList<>());
        }
        maxPoint = new Point (rows-1, columns-1);

        for (var row = 0; row < rows; ++row) {
            verticesPerRow.clear();
            for (var col = 0; col < columns; ++col) {
                Trampoline trampoline = map[row][col];
                Vertex newVertex = new Vertex(row, col, trampoline.getType(), trampoline.getJumpForce());
                mapCoordinateVertex.put(new Point(row,col), newVertex);
                int jumpForce = trampoline.getJumpForce();

                if (trampoline.getType() == Trampoline.Type.NORMAL || trampoline.getType() == Trampoline.Type.WITH_FINE) {
                    verticesPerRow.add(newVertex);
                    verticesPerColumn.get(col).add(newVertex);
                    int[] jumps = new int[]{jumpForce-1, jumpForce, jumpForce+1};
                    for (var jump : jumps){
                        if (jumpForce > 0) {
                            Point newPointEast = new Point(row, col + jump);
                            Point newPointSouth = new Point(row + jump, col);
                            addConnection(newVertex, newPointEast);
                            addConnection(newVertex, newPointSouth);
                        }
                    }
                }
                if (trampoline.getType() == Trampoline.Type.WALL) {
                    if (verticesPerRow.size() > 0) {
                        Point previousPointInRow = new Point(row, col-1);
                        for (Vertex vertex : verticesPerRow.toArray(new Vertex[0])){
                            Point searchKey = new Point(row, vertex.coordinate.y + vertex.force);
                            connections.get(searchKey).remove(vertex);
                            addConnection(vertex, previousPointInRow);
                            verticesPerRow.remove(vertex);
                        }
                        updateEdges(mapCoordinateVertex.get(previousPointInRow),verticesPerRow, verticesPerColumn.get(col));
                    }

                    if (verticesPerColumn.get(col).size() > 0) {
                        Point previousPointInCol = new Point(row - 1, col);
                        for (Vertex c : verticesPerColumn.get(col)) {
                            Point searchKey = new Point(c.coordinate.x + c.force, col);
                            connections.get(searchKey).remove(c);
                            addConnection(c, previousPointInCol);
                            verticesPerRow.remove(c);
                        }
                        updateEdges(mapCoordinateVertex.get(previousPointInCol),verticesPerRow, verticesPerColumn.get(col));
                    }
                }
                updateEdges(newVertex, verticesPerRow, verticesPerColumn.get(col));
            }
        }
    }

    public Vertex getStartVertex() {
        return mapCoordinateVertex.get(startingPoint);
    }

    public Vertex getEndVertex() {
        return mapCoordinateVertex.get(maxPoint);
    }

}
