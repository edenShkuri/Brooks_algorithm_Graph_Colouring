public class NodeData{
    private static int index = 0; // static variable, to be sure that is unique key
    private int key;
    private int tag;
    int color;
    private Point p;


    public NodeData(){
        this.key = index++;
        this.color = 0;
        p=new Point();
    }
    public NodeData(int key){
        this.key = key;
        this.color = 0;
        p=new Point();
    }
    public void setColor(int i) {
        color=i;
    }

    public int getColor() {
        return color;
    }

    public int getKey(){
        return key;
    }

    public void setP(int x, int y) {
        p.setX(x);
        p.setY(y);
    }

    public Point getP() {
        return p;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return ""+key;

    }


}
