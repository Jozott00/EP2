public class MyNode {
    private CelestialBody body;
    private CelestialSystem system;
    private MyNode next;

    public MyNode(CelestialBody body, MyNode next) {
        this.body = body;
        this.next = next;
    }

    public MyNode(CelestialSystem system, MyNode next) {
        this.system = system;
        this.next = next;
    }

    public MyNode next() {
        return this.next;
    }

    public CelestialBody getBody() {
        return this.body;
    }

    public CelestialSystem getSystem() {
        return this.system;
    }

    public void setNext(MyNode next) {
        this.next = next;
    }

}
