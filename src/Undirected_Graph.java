import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Undirected_Graph {
    HashMap<Integer, NodeData> vertices;
    HashMap<Integer, List<edgeData>> edges;
    private int MaxColor;

    public Undirected_Graph() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
        MaxColor=0;
    }

    public NodeData getNode(int key) {
        return vertices.get(key);
    }

    public void addNode(NodeData n) {
        vertices.put(n.getKey(), n);
        edges.put(n.getKey(), new LinkedList<>());
    }

    public void addEdge(int n1, int n2) {
        if(getEdge(n1,n2) == null){
            edgeData e1 = new edgeData(getNode(n1), getNode(n2));
            edgeData e2 = new edgeData(getNode(n2), getNode(n1));

            edges.get(n1).add(e1);
            edges.get(n2).add(e2);
        }
    }

    public void removeEdge(int n1, int n2) {
        edgeData e = getEdge(n1,n2);
        if(e != null){
            edges.get(n1).remove(e);
            e = getEdge(n2,n1);
            edges.get(n2).remove(e);
        }
    }

    public void removeNode(int key) {
        vertices.remove(key);
        for (edgeData e : edges.get(key)) {
            edges.get(e.getDest()).remove(e.getSrc());
        }
        edges.remove(key);
    }

    public Collection<NodeData> get_all_V() {
        return vertices.values();
    }

    public Collection<edgeData> get_all_E(int key) {
        return edges.get(key);
    }

    public edgeData getEdge(int key1, int key2) {
        if(!vertices.containsKey(key1) || !vertices.containsKey(key2)) {
            return null;
        }else{
            for (edgeData e : edges.get(key1)) {
                if (e.getDest() == key2)
                    return e;
            }
        }
        return null;
    }
    public int getSumOfEdges(){
        int n = 0;
        for(List<edgeData> l : edges.values()){
            n+=l.size();
        }
        return n/2;
    }
    public Collection<NodeData> getNi(NodeData n) {
        Collection<NodeData> res = new HashSet<>();
        for (edgeData e : this.get_all_E(n.getKey())) {
            res.add(e.getDestNode());
        }
        return res;
    }

    private void resetTagAndColor() {
        for(NodeData n : vertices.values()){
            n.setColor(0);
            n.setTag(0);
        }
    }

    public void setMaxColor(int i){
        MaxColor=i;
    }

    public int getMaxColor(){
        return MaxColor;
    }


    public List<NodeData> BFS_order(){
        List<NodeData> bfs_order=new LinkedList<>();
        resetTagAndColor();
        NodeData startNode=new NodeData();

        for (NodeData n:get_all_V()){
            if(edges.get(n.getKey()).size()<getMaxDegree()){
                startNode=n;
                break;
            }
        }

        Stack<NodeData> unVisitedNodes = new Stack<>();
        unVisitedNodes.push(startNode);
        bfs_order.add(startNode);
        startNode.setTag(1);

        while (!unVisitedNodes.isEmpty()) { //O(v) 
            NodeData tmpNode = unVisitedNodes.pop();
            for (edgeData tmp : get_all_E(tmpNode.getKey())) {
                NodeData currNode = getNode(tmp.getDest());
                if (currNode.getTag() != 1) {
                    unVisitedNodes.push(currNode);
                    bfs_order.add(currNode);
                    currNode.setTag(1);
                }
            }
        }
        return bfs_order;
    }

    public int getMaxDegree() {
        int max=0;
        for (NodeData n:get_all_V()){
            int size=edges.get(n.getKey()).size();
            if(size>max)
                max=size;
        }
        return max;
    }



    public void PrintByColor(int maxColor) {
        HashMap<Integer, List> NodesByColor=new HashMap<>();
        for (int i=1; i<=maxColor; i++){
            NodesByColor.put(i, new LinkedList());
        }
        for (NodeData n: get_all_V()){
            NodesByColor.get(n.getColor()).add(n);
        }

        for (int color: NodesByColor.keySet()){
            System.out.println("Color "+color+"\n"+NodesByColor.get(color).toString());
        }
    }


    public boolean load(String file) {
        try {
            //JSONObject that represent the graph from JSON file
            JSONObject graph = new JSONObject(new String(Files.readAllBytes(Paths.get(file))));

            //Two JSONArray that represents the Edges and Nodes
            JSONArray edges = graph.getJSONArray("Edges");
            JSONArray nodes = graph.getJSONArray("Nodes");

            //Declare of the new graph
//            g = new Graph();
            //For each Node, get the data ,make new node and add him to the graph
            for (int i = 0; i < nodes.length(); i++) {
                JSONObject nJSON = nodes.getJSONObject(i);
                //Build node that contain the id an pos
                NodeData n = new NodeData(nJSON.getInt("id"));
                try {
                    JSONObject pointJSON = nJSON.getJSONObject("point");
                    int x = pointJSON.getInt("X");
                    int y = pointJSON.getInt("Y");
                    n.setP(x,y);
                }catch(Exception e){

                }
                //Add this node to the graph
                addNode(n);
            }
            //For each edge, get the data and connect two vertex by the data
            for (int i = 0; i < edges.length(); i++) {
                JSONObject edge = edges.getJSONObject(i);
                int src = edge.getInt("src");
                int dest = edge.getInt("dest");
                addEdge(src, dest);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(String file) {
        //Create new Json object - graph
        JSONObject graph = new JSONObject();
        //Declare two Json arrays
        JSONArray edges = new JSONArray();
        JSONArray nodes = new JSONArray();
        try {
            //For each node
            for (NodeData n : get_all_V()) {
                //Scan all his edges
                for (edgeData e : get_all_E(n.getKey())) {
                    //Declare Json object - edge
                    JSONObject edge = new JSONObject();
                    //Insert the data to this object
                    edge.put("src", e.getSrc());
                    edge.put("dest", e.getDest());
                    //Insert this object to edges array
                    edges.put(edge);
                }
                //Declare Json object - node
                JSONObject node = new JSONObject();
                //Insert the data to this object
                node.put("id", n.getKey());
                JSONObject point = new JSONObject();
                point.put("X",n.getP().getX());
                point.put("Y",n.getP().getY());
                node.put("point",point);
                //Insert this object to nodes array
                nodes.put(node);
            }
            //Insert this both arrays to the graph object
            graph.put("Edges", edges);
            graph.put("Nodes", nodes);

            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(graph.toString());
            pw.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Graph:\n");
        for(int key : this.edges.keySet()){
            s.append("key: ").append(key).append(" | ");

            for(edgeData e : edges.get(key)){
                s.append("").append(e.getDest()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
