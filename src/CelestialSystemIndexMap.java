// TODO: define class according to 'Aufgabenblatt5'.

import java.util.Arrays;

public class CelestialSystemIndexMap implements CelestialSystemIndex {

    private final int arraySize = 31;

    private CelestialBody[] keyArray = new CelestialBody[arraySize];
    private CelestialSystem[] sysArray = new CelestialSystem[arraySize];

    private int count = 0;

    CelestialSystemIndexMap() {
    }

    @Override
    public boolean add(CelestialSystem system) {

        if(system == null) return false;

        boolean changed = false;

        CelestialBody[] inputArray = system.toArray();

        for(CelestialBody b : inputArray) {
            if(this.contains(b)) return false;
        }

        for (CelestialBody cb : inputArray) {
            int index = cb.hashCode() % keyArray.length;

            while (true) {
                if (sysArray[index] == null) {
                    sysArray[index] = system;
                    keyArray[index] = cb;
                    changed = true;

                    count++;

                    if(count >= 0.75 * sysArray.length)
                        doubleMap();

                    break;
                }

                if (keyArray[index].equals(cb)) {
                    //changed = false;
                    break;
                }

                index = (index + 1) % keyArray.length;
            }
        }
        return changed;
    }

    @Override
    public CelestialSystem get(CelestialBody body) {
        if(body == null) return null;

        int index = body.hashCode() % keyArray.length;
        int startIndex = index;

        if (sysArray[index] != null && keyArray[index] == body) return sysArray[index];

        while (sysArray[index] == null) {
            if (keyArray[index].equals(body)) return sysArray[index];

            index = (index + 1) % keyArray.length;
            if(index == startIndex) return null;

        }
        return null;
    }

    @Override
    public boolean contains(CelestialBody body) {
        if(body == null) return false;

        int index = body.hashCode() % keyArray.length;
        int startIndex = index;

        if (sysArray[index] != null && keyArray[index] == body) return true;

        while (sysArray[index] != null) {
            if (keyArray[index].equals(body)) return true;

            index = (index + 1) % keyArray.length;
            if(index == startIndex) return false;
        }

        return false;
    }

    @Override
    public String toString() {
        String result = "|";
        for (int i = 0; i < keyArray.length; i++) {
            if(keyArray[i] == null) continue;
            result += "  " + i + "  |";
        }
        result += "\n|";
        for (CelestialBody i : keyArray) {
            if(i == null) continue;
            result += " " + i.getName() + "  |";
        }
        result += "\n|";
        for (CelestialSystem i : sysArray) {
            if(i == null) continue;
            result += " " + i.getName() + " |";
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;

        CelestialSystemIndexMap map = (CelestialSystemIndexMap) obj;

        if (map.getFilledCount() != this.getFilledCount()) return false;

        for(CelestialBody i : keyArray) {

            if(i != null && map.get(i) == null) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = count;
        for(int i = 0; i < keyArray.length; i++) {
            if(keyArray[i] != null) {
                h += keyArray[i].hashCode();
                if(sysArray[i] != null)
                    h += sysArray[i].hashCode();
            }
        }
        return h;
    }



    public int getFilledCount() {
        int sum = 0;
        for(CelestialBody i : keyArray) {
            if(i != null) sum++;
        }
        return sum;
    }

    public void doubleMap()  {
        CelestialSystem[] oldSysArray = sysArray;

        keyArray = new CelestialBody[oldSysArray.length * 2];
        sysArray = new CelestialSystem[oldSysArray.length * 2];

        for(CelestialSystem sys : oldSysArray) {
            this.add(sys);
        }
    }
}