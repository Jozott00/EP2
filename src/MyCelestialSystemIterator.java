public class MyCelestialSystemIterator implements CelestialBodyIterator {

    MyNode current;

    public MyCelestialSystemIterator(CelestialSystem system) {
        current = system.getHead();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public CelestialBody next() {
        CelestialBody data = current.getBody();
        current = current.next();
        return data;
    }

}
