// TODO: define class according to 'Aufgabenblatt5'.

import java.util.Arrays;

public class CelestialSystemIndexMap implements CelestialSystemIndex {

    private final int arraySize = 65;

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
            int index = cb.hashCode() % arraySize;

            while (index < arraySize) {
                if (sysArray[index] == null) {
                    sysArray[index] = system;
                    keyArray[index] = cb;
                    changed = true;
                    break;
                }

                if (keyArray[index].equals(cb)) {
                    //changed = false;
                    break;
                }

                index++;
            }
        }
        return changed;
    }

    @Override
    public CelestialSystem get(CelestialBody body) {
        int index = body.hashCode() % arraySize;

        if (sysArray[index] != null && keyArray[index] == body) return sysArray[index];

        while (index < arraySize) {
            if (sysArray[index] == null) return null;
            if (keyArray[index].equals(body)) return sysArray[index];

            index++;
        }
        return null;
    }

    @Override
    public boolean contains(CelestialBody body) {
        if(body == null) return false;

        int index = body.hashCode() % arraySize;
        if (sysArray[index] != null && keyArray[index] == body) return true;

        while (index < arraySize) {
            if (sysArray[index] == null) break;
            if (keyArray[index].equals(body)) return true;
            index++;
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
}