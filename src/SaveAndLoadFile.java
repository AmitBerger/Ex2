import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class SaveAndLoadFile {

    public static class LoadFromFile{
        MyDWG newGraph;
        public LoadFromFile(String G){
            load(G);
        }

        private boolean load(String fileName) {
            JsonObject jasonObject;
            String f;
            DirectedWeightedGraph g1 = new MyDWG();

            try {
                f = new String(Files.readAllBytes(Paths.get(fileName)));

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            jasonObject = JsonParser.parseString(f).getAsJsonObject();

            JsonArray NodesArray = jasonObject.getAsJsonArray("Nodes");
            for (JsonElement i : NodesArray) {
                String[] posArray = i.getAsJsonObject().get("pos").getAsString().split(",");
                NodeData temp = new Node(i.getAsJsonObject().get("id").getAsInt());
                if (posArray.length > 0)
                    temp.setLocation(new location(Double.parseDouble(posArray[0]), Double.parseDouble(posArray[1])
                            , Double.parseDouble(posArray[2])));
                g1.addNode(temp);
            }

            JsonArray EdgesArray = jasonObject.getAsJsonArray("Edges");
            for (JsonElement i : EdgesArray) {
                g1.connect(i.getAsJsonObject().get("src").getAsInt(), i.getAsJsonObject().get("dest").getAsInt(), i.getAsJsonObject().get("w").getAsDouble());
            }

            init(g1);
            return true;
        }

    }

    public static class SaveToFile{

        public SaveToFile(String fileName ,DirectedWeightedGraph g){
            save(fileName, g);
        }

        private boolean save(String fileName ,DirectedWeightedGraph g) {
            JsonObject jsonObject = new JsonObject();
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

            jsonObject.add("Nodes", nodesArray);
            jsonObject.add("Edges", edgesArray);

            try {
                File f = new File(fileName);
                Gson gson = new Gson();
                FileWriter fileWriter = new FileWriter(f);
                fileWriter.write(gson.toJson(jsonObject));
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

}
