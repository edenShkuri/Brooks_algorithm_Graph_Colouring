import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ColouringGui extends JPanel {

    private static final int SIZE = 650;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
    private final Undirected_Graph graph;
    private Color[] Colors;
    boolean flag;
    int maxDeg;

    public ColouringGui(Undirected_Graph gr) {
        super(true);
        this.setPreferredSize(new Dimension(SIZE+350, SIZE));
        n=gr.get_all_V().size();
        graph=gr;
        maxDeg=graph.getMaxDegree();
        Colors=new Color[maxDeg+1];
        Colors[0]=Color.white;
        Random generator = new Random(8);
        for(int i=1; i<=maxDeg; i++){
            int r=(int)(generator.nextDouble() * (255));
            int g=(int)(generator.nextDouble() * (255));
            int b=(int)(generator.nextDouble() * (255));
            Colors[i]=new Color(r,g,b);
        }
        NodeData firstNode=graph.get_all_V().stream().findFirst().get();
        flag = firstNode.getP().getX() != 0 && firstNode.getP().getY() != 0;
    }


    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);
        r = 4 * m / 5;
        g2d.setStroke(new BasicStroke(2));
        if(flag){
            drawEdges(g2d);
            drawNodesWithLocations(g2d);

        }else {
            drawNodes(g2d, m, r);
            drawEdges(g2d);
        }
        Font f=new Font("SansSerif", Font.BOLD,22);
        g2d.setFont(f);
        g2d.drawString("Δ is "+maxDeg, 755, 100);
        g2d.drawString("number of colors-> "+graph.getMaxColor(), 755, 140);

        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(735, 70, 260, 80);

        g2d.drawString("The colors:", 840, 200);

        int maxColor=graph.getMaxColor();
        for (int i=1; i<=maxColor; i++){
            g2d.setColor(Colors[i]);
            g2d.fillRect(855, 220+(i-1)*30, 100, 20);
        }

    }

    private void drawEdges(Graphics2D g2d) {
        for(NodeData node: graph.get_all_V()){
            for(NodeData ni: graph.getNi(node)){
                int x1=node.getP().getX(),
                        y1=node.getP().getY(),
                        x2=ni.getP().getX(),
                        y2=ni.getP().getY();
                g2d.drawLine(x1, y1, x2, y2);
            }
        }

    }
    private void drawNodesWithLocations(Graphics2D g2d) {
        for(NodeData node: graph.get_all_V()){
            g2d.setColor(Colors[node.getColor()]);
            int x = node.getP().getX();
            int y =  node.getP().getY();
            g2d.fillOval(x-20, y-20, 40, 40);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x-20 , y-20, 40, 40);
            Font f=new Font("SansSerif", Font.BOLD,15);
            g2d.setFont(f);
            int key=node.getKey();
            g2d.drawString(""+key,x-6 ,y+5);
        }
    }

    private void drawNodes(Graphics2D g2d, int m, int r) {
        int r2=20;
        int i=0;
        for(NodeData node: graph.get_all_V()){
            g2d.setColor(Colors[node.getColor()]);
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
            g2d.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
            g2d.setColor(Color.BLACK);
            int px, py;
            int d= (int)Math.sqrt((x-m)*(x-m)+(y-m)*(y-m));
            int k= d-r2;
            if(d==0){d=1;}
            px=(m*r2+x*k)/(d);
            py=(m*r2+y*k)/(d);
            node.setP(px, py);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - r2, y - r2, 2 * r2, 2 * r2);
            Font f=new Font("SansSerif", Font.BOLD,13);
            g2d.setFont(f);
            int key=node.getKey();
            g2d.drawString(""+key,(int)(x+0.038*(2 * r2)) , (int)(y+0.038*(2 * r2)));
            i++;
        }
    }
}