import org.w3c.dom.Node;

import java.util.*;

public class Undirected_Graph {
    HashMap<Integer, NodeData> vertices;
    HashMap<Integer, List<edgeData>> edges;

    public Undirected_Graph() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
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


    public List<NodeData> BFS_order(){
        List<NodeData> bfs_order=new LinkedList<>();
        resetTagAndColor();
        NodeData startNode=new NodeData();

        for (NodeData n:get_all_V()){
            if(edges.get(n.getKey()).size()<getMaxDegree()){
                startNode=n;
                System.out.println("start node-> "+n.getKey());
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

    private int getMaxDegree() {
        int max=0;
        for (NodeData n:get_all_V()){
            int size=edges.get(n.getKey()).size();
            if(size>max)
                max=size;
        }
        return max;
    }

    @Override
    public String toString() {
        String s = "Graph:\n";
                for(int key : this.edges.keySet()){
                    s+="key: "+key+" | ";

                    for(edgeData e : edges.get(key)){
                        s+=""+e.getDest()+" ";
                    }
                    s+="\n";
                }
                return s;
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
}
