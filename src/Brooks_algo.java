import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class Brooks_algo {

    public static int Colouring_By_Brooks(Undirected_Graph g, JFrame f) throws InterruptedException {
        List<NodeData> BFS=g.BFS_order();
        Collections.reverse(BFS);

        BFS.get(0).setColor(1);
        int MaxColor=1;

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
            }
            f.repaint();
            Thread.sleep(1000);
        }
        return MaxColor;
    }

    public static void main(String[] args) throws InterruptedException {
        Undirected_Graph g=new Undirected_Graph();
        for (int i=1; i<16; i++){
            g.addNode(new NodeData(i));
        }

        g.addEdge(1,2);
        g.addEdge(1,3);
        g.addEdge(2,4);
        g.addEdge(2,9);
        g.addEdge(3,5);
        g.addEdge(3,6);
        g.addEdge(4,5);
        g.addEdge(4,8);
        g.addEdge(5,7);
        g.addEdge(6,11);
        g.addEdge(6,12);
        g.addEdge(7,12);
        g.addEdge(7,13);
        g.addEdge(8,13);
        g.addEdge(8,14);
        g.addEdge(9,14);
        g.addEdge(9,15);
        g.addEdge(10,15);
        g.addEdge(10,11);

        JFrame f = new JFrame();
        f.setSize(600, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ColouringGui(g));
        f.pack();
        f.setVisible(true);
        Thread.sleep(1000);
        int MaxColor=Brooks_algo.Colouring_By_Brooks(g, f);
        g.PrintByColor(MaxColor);

    }
}
