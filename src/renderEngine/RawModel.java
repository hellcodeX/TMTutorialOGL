package renderEngine;

public class RawModel {

    private int vaoID;
    private int vertxCount;

    public RawModel(int vaoID, int vertxCount) {
        this.vaoID = vaoID;
        this.vertxCount = vertxCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertxCount() {
        return vertxCount;
    }
}
