import api.DirectedWeightedGraph;

import api.EdgeData;
import api.NodeData;
import com.google.gson.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class SaveToFile {
    public SaveToFile(String fileName ,DirectedWeightedGraph g){
        save(fileName, g);
    }

    private boolean save(String fileName ,DirectedWeightedGraph g) {
        JsonObject myJsonObject = new JsonObject();
        JsonArray nodesArray = new JsonArray();
        JsonArray edgesArray = new JsonArray();
        Iterator<NodeData> nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currentNode = nodeIter.next();
            JsonObject nodeTemp = new JsonObject();
            if (currentNode.getLocation() == null) {
                nodeTemp.addProperty("pos", ",,");
            } else {
                String nodeTempLocation = currentNode.getLocation().x() + "," + currentNode.getLocation().y() +
                        "," + currentNode.getLocation().z();
                nodeTemp.addProperty("pos", nodeTempLocation);
            }
            nodeTemp.addProperty("id", currentNode.getKey());
            nodesArray.add(nodeTemp);
        }
        nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currentNode = nodeIter.next();
            Iterator<EdgeData> nodeEdgeIter = g.edgeIter(currentNode.getKey());
            while (nodeEdgeIter.hasNext()) {
                EdgeData currentEdge = nodeEdgeIter.next();
                JsonObject edgeTemp = new JsonObject();
                edgeTemp.addProperty("src", currentEdge.getSrc());
                edgeTemp.addProperty("w", currentEdge.getWeight());
                edgeTemp.addProperty("dest", currentEdge.getDest());
                edgesArray.add(edgeTemp);
            }
        }

        myJsonObject.add("Nodes", nodesArray);
        myJsonObject.add("Edges", edgesArray);

        try {
            File f = new File(fileName);
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(gson.toJson(myJsonObject));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
