public class edgeData{
    private NodeData src;
    private NodeData dest;

    public edgeData(NodeData src, NodeData dest) {
        this.src = src;
        this.dest = dest;
    }

    public int getSrc() {return src.getKey();}

    public int getDest() {return dest.getKey();}

    public NodeData getSrcNode(){return src;}

    public NodeData getDestNode() {return dest;}

    public String toString(){
        return "(src: "+src+", dest: "+dest+")";
    }
}
