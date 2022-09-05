
public class MapObject {
    private long id;
    private int x;
    private int y;
    private int type;
    private int category;

    public MapObject(long id, int x, int y, int type, int category) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public int getCategory() {
        return category;
    }
}
