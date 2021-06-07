import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ColouringGui extends JPanel implements ActionListener {

    private static final int SIZE = 650;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
    private Undirected_Graph graph;
    private int numberOfColors;
    private Color Colors[];
    JButton button;
    boolean flag;

    public ColouringGui(Undirected_Graph gr) {
        super(true);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        n=gr.get_all_V().size();
        graph=gr;
        numberOfColors=graph.getMaxDegree();
        Colors=new Color[numberOfColors+1];
        Colors[0]=Color.white;
        Random generator = new Random(4);
        for(int i=1; i<=numberOfColors; i++){
            int r=(int)(generator.nextDouble() * (255));
            int g=(int)(generator.nextDouble() * (255));
            int b=(int)(generator.nextDouble() * (255));
            Colors[i]=new Color(r,g,b);
//            Colors[i]=hslColor((i-1)*(255/numberOfColors), 150, 125);
        }
//        setLayout(new BorderLayout());
        button=new JButton("next");
        Font f=new Font("SansSerif", Font.BOLD,13);
        button.setFont(f);
        button.setLocation(10,10);
        button.addActionListener(this);
//        add(button);
        flag=true;
    }

//    private static float hue2rgb(float p, float q, float h) {
//        if (h < 0) {
//            h += 1;
//        }
//        if (h > 1) {
//            h -= 1;
//        }
//        if (6 * h < 1) {
//            return p + ((q - p) * 6 * h);
//        }
//        if (2 * h < 1) {
//            return q;
//        }
//        if (3 * h < 2) {
//            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
//        }
//        return p;
//    }
//
//    static public Color hslColor(float h, float s, float l) {
//        float q, p, r, g, b;
//
//        if (s == 0) {
//            r = g = b = l; // achromatic
//        } else {
//            q = l < 0.5 ? (l * (1 + s)) : (l + s - l * s);
//            p = 2 * l - q;
//            r = hue2rgb(p, q, h + 1.0f / 3);
//            g = hue2rgb(p, q, h);
//            b = hue2rgb(p, q, h - 1.0f / 3);
//        }
//        int R=Math.round(r * 255), G= Math.round(g * 255), B= Math.round(b * 255);
//        System.out.println("R= "+R+", G= "+G+", B= "+B);
//        Color mycolor = new Color(R,G,B);
//        return mycolor;
//    }

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
        int r2 = Math.abs(m - r) / 2;
//        g2d.drawOval(a - r, b - r, 2 * r, 2 * r);

        g2d.setStroke(new BasicStroke(2));
       drawNodes(g2d, r2, m, r);
       drawEdges(g2d);

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

    private void drawNodes(Graphics2D g2d, int r2, int m, int r) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
           flag=true;
        }
    }
}