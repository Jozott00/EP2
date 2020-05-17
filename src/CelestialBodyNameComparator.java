public class CelestialBodyNameComparator implements CelestialBodyComparator {

    @Override
    // compares two bodies by their names.
    public int compare(CelestialBody b1, CelestialBody b2) {
        //TODO: implement method.
        if(b2 == null) return b2 == b1 ? 0 : -1;
        return b1 == null ? 1 : b1.getName().compareTo(b2.getName());
    }
}
