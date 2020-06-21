import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution
{
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        parentMap = new HashMap<>();
        weightMap = new HashMap<>();

        for (int i = 0; i < equations.size(); ++i) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);

            if (!parentMap.containsKey(a)) {
                parentMap.put(a, a);
                weightMap.put(a, 1.0);
            }

            if (!parentMap.containsKey(b)) {
                parentMap.put(b, b);
                weightMap.put(b, 1.0);
            }

            union(a, b, values[i]);
        }

        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); ++i) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);
            Node parenta = find(a);
            Node parentb = find(b);
            if (parenta.nodeName == "" || parentb.nodeName == "" || parenta.nodeName != parentb.nodeName) {
                ans[i] = -1;
                continue;
            }

            ans[i] = parenta.weight / parentb.weight;
        }

        return ans;
    }

    private Node find(String node)
    {
        if (!parentMap.containsKey(node)) {
            Node ans = new Node();
            ans.nodeName = "";
            ans.weight = -1;
            return ans;
        }

        double weight = 1.0;
        while (node != parentMap.get(node)) {
            weight *= weightMap.get(node);
            node = parentMap.get(node);
        }

        Node ans = new Node();
        ans.nodeName = node;
        ans.weight = weight;
        return ans;
    }

    private void union(String a, String b, double val)
    {
        Node parenta = find(a);
        Node parentb = find(b);
        if (parenta.nodeName == "" || parentb.nodeName == "") {
            return;
        }

        if (parenta.nodeName == parentb.nodeName) {
            return;
        }

        parentMap.put(parenta.nodeName, parentb.nodeName);
        weightMap.put(parenta.nodeName, 1.0 / parenta.weight * val * parentb.weight);

    }

    class Node
    {
        String nodeName;
        double weight;
    }

    private Map<String, String> parentMap;
    private Map<String, Double> weightMap;
}