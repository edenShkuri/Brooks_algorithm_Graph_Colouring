public class NodeData{
    private static int index = 0; // static variable, to be sure that is unique key
    private int key;
    private int tag;
    int color;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public NodeData(){
        this.key = index++;
        this.color = 0;
    }
    public NodeData(int key){
        this.key = key;
        this.color = 0;
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

    @Override
    public String toString() {
        return ""+key;

    }


}
