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
            Thread.sleep(500);

        }
        return MaxColor;
    }

    public static Undirected_Graph CreateBigGraph(){
        Undirected_Graph g=new Undirected_Graph();
        for (int i=1; i<=20; i++){
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
        g.addEdge(16,20);
        g.addEdge(16,17);
        g.addEdge(16,11);
        g.addEdge(17,12);
        g.addEdge(17, 18);
        g.addEdge(18,13);
        g.addEdge(18,19);
        g.addEdge(19,20);
        g.addEdge(19,14);
        g.addEdge(20,15);

        g.getNode(1).setP(395, 210);
        g.getNode(2).setP(360, 290);
        g.getNode(3).setP(505, 210);
        g.getNode(4).setP(450, 360);
        g.getNode(5).setP(540, 290);
        g.getNode(6).setP(580, 170);
        g.getNode(7).setP(620, 320);
        g.getNode(8).setP(450, 430);
        g.getNode(9).setP(290, 330);
        g.getNode(10).setP(350, 170);
        g.getNode(11).setP(450, 100);
        g.getNode(12).setP(680, 220);
        g.getNode(13).setP(600, 440);
        g.getNode(14).setP(320, 440);
        g.getNode(15).setP(240, 220);
        g.getNode(16).setP(450, 40);
        g.getNode(17).setP(775, 200);
        g.getNode(18).setP(660, 515);
        g.getNode(19).setP(260, 515);
        g.getNode(20).setP(145, 200);

        return g;
    }

    public static void main(String[] args) throws InterruptedException {
        Undirected_Graph g=CreateBigGraph();

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
