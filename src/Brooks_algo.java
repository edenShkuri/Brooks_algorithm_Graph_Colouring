import javax.swing.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Brooks_algo {

    public static int Colouring_By_Brooks(Undirected_Graph g, JFrame f) throws InterruptedException {
        List<NodeData> BFS=g.BFS_order();
        Collections.reverse(BFS);

        BFS.get(0).setColor(1);
        int MaxColor=1;
        g.setMaxColor(1);

        for (int i=1; i<BFS.size(); i++){
            NodeData node= BFS.get(i);
            boolean ColorIsTaken=false;
            for(int color=1; color<=MaxColor; color++){
                ColorIsTaken=false;
                for (NodeData nei: g.getNi(node)){
                    if(nei.getColor()==color){
                        ColorIsTaken=true;
                        break;
                    }
                }
                if(!ColorIsTaken){
                    node.setColor(color);
                    break;
                }
            }
            if(ColorIsTaken){
                MaxColor++;
                node.setColor(MaxColor);
                g.setMaxColor(MaxColor);
            }
            f.repaint();
            Thread.sleep(500);

        }
        return MaxColor;
    }

    /**
     * Create random graph such that not all nodes had same degree
     * NOTE: if E > V*(V-1), throw exception.
     *
     * @param V - Sum of nodes.
     * @param E - Sum of edges.
     * @return Undirected Graph.
     */
    public static Undirected_Graph graphCreator(int V, int E){
        if(E > V*(V-1)){ throw new IndexOutOfBoundsException("This graph, with "+V+" nodes cannot have "+E+" edges");}
        Undirected_Graph g = new Undirected_Graph();
        //Add nodes to the graph
        for(int i = 0;i<V;++i){
            g.addNode(new NodeData());
        }
        List<NodeData> nodes = new LinkedList<>(g.get_all_V());
        //Connect all node to the next node to ensure connectivity
        for(int i = 0;i<nodes.size()-1;++i){
            g.addEdge(nodes.get(i).getKey(),nodes.get((i+1)).getKey());
        }

        Random r = new Random();
        //Connect more random edges to the graph
        while(g.getSumOfEdges() < E){
            int src = r.nextInt(nodes.size());
            int dest = r.nextInt(nodes.size());
            while(src == dest){
                dest = r.nextInt(nodes.size());
            }
            if(g.getEdge(src,dest) == null) {
                g.addEdge(src,dest);
            }
        }
        int maxDegree = g.getMaxDegree();
        for(List<edgeData> l : g.edges.values()){
            if(l.size() < maxDegree){
                return g;
            }
        }
        //If the for loop above don't return g, so all nodes add same degree.
        //Get first edge from first node and remove it.
        edgeData e = g.edges.values().stream().findFirst().get().stream().findFirst().get();
        g.removeEdge(e.getSrc(),e.getDest());

        return g;
    }
    public static void main(String[] args) throws InterruptedException {
//        Undirected_Graph g=CreateBigGraph();
        Undirected_Graph g= graphCreator(20,45);
//        Undirected_Graph g=new Undirected_Graph();
//        g.load("Graphs/Graphs_with_Points/Rocket.json");
        JFrame f = new JFrame();
        f.setSize(1500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ColouringGui(g));
        f.pack();
        f.setVisible(true);
        Thread.sleep(2000);
        int MaxColor=Brooks_algo.Colouring_By_Brooks(g, f);
        g.PrintByColor(MaxColor);

    }
}
