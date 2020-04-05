public class MyNode {
    private CelestialBody body;
    private CelestialSystem system;
    private MyNode next, prev;

    public MyNode(CelestialBody body, MyNode next, MyNode prev) {
        this.body = body;
        this.next = next;
        this.prev = prev;
    }

    public MyNode(CelestialSystem system, MyNode next, MyNode prev) {
        this.system = system;
        this.next = next;
        this.prev = prev;
    }

    public MyNode next() {
        return this.next;
    }

    public MyNode prev() { return this.prev; }

    public CelestialBody getBody() {
        return this.body;
    }

    public CelestialSystem getSystem() {
        return this.system;
    }

    public void setNext(MyNode next) {
        this.next = next;
    }

    public void setPrev(MyNode prev) { this.prev = prev; }


}
