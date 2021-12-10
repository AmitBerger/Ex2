package Implementation;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;


import java.io.IOException;
import java.text.ParseException;

/**
 * This class is the main class for Implementation.Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */

    public static DirectedWeightedGraph getGraph(String json_file) throws IOException, ParseException {
        // ****** Add your code here ******
        MyDWGAlgorithm ans = new MyDWGAlgorithm();
        ans.load(json_file);

        // ********************************
        return ans.getGraph();
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {

        DirectedWeightedGraphAlgorithms ans = new MyDWGAlgorithm();
        // ****** Add your code here ******
        ans.load(json_file);
        // ********************************
        return ans;
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
//                GUI.runGUI((Implementation.MyDWG)alg.getGraph());
        // ********************************
    }
}