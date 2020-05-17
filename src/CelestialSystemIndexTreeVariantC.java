//TODO: change class definition below according to specification in 'Aufgabenblatt6'.

import java.sql.Struct;

public class CelestialSystemIndexTreeVariantC implements CelestialSystemIndex, CelestialBodyIterable {

    private VariantCNode root;
    private CelestialBodyComparator comparator;

    private int size = 0;

    // Initialises this index with a 'comparator' for sorting
    // the keys of this index.
    public CelestialSystemIndexTreeVariantC(CelestialBodyComparator comparator) {
        this.comparator = comparator;
    }

    // Adds a system of bodies to the index.
    // Adding a system adds multiple (key, value) pairs to the
    // index, one for each body of the system, with the same
    // value, i.e., reference to the celestial system.
    // An attempt to add a system with a body that already exists
    // in the index leaves the index unchanged and the returned
    // value would be 'false'.
    // The method returns 'true' if the index was changed as a
    // result of the call and 'false' otherwise.
    public boolean add(CelestialSystem system) {

        if (system == null || system.size() == 0) {
            return false;
        }

        for(CelestialBody b : system) {
           if (this.contains(b)) return false;
        }

        boolean changed = false;

        if (root == null) {
            root = new VariantCNode(system.get(0), system, null, null, comparator);
            size++;
            changed = true;
        } else {
            if(root.add(new VariantCNode(system.get(0), system, null, null, comparator))) {
                changed = true;
                size++;
            }
        }

        for (int i = 1; i < system.size(); i++) {
            if(root.add(new VariantCNode(system.get(i), system, null, null, comparator))) {
                changed = true;
                size++;
            }
        }

        return changed;

    }

    // Returns the celestial system with which a body is
    // associated. If body is not contained as a key, 'null'
    // is returned.
    public CelestialSystem get(CelestialBody body) {
        if (root == null) {
            return null;
        }

        return root.get(body);

    }

    // Returns 'true' if the specified 'body' is listed
    // in the index.
    public boolean contains(CelestialBody body) {
        return get(body) != null;
    }

    // Returns a readable representation with (key, value) pairs sorted by the key.
    public String toString() {
        if (root == null) {
            return "{}";
        }

        return "{" + root.toString() + "}";
    }

    // Returns a collection view of all entries of this index.
    public CelestialBodyCollection bodies() {
        //TODO: implement method.
        return new CelestialBodySet(this);
    }

    // Returns all entries of this as a new collection.
    public CelestialSystem bodiesAsCelestialSystem() {
        //TODO: implement method.

        CelestialSystem cs = new CelestialSystem("IndexTreeVariantC Copy");

        for(CelestialBody b : this) {
            cs.add(b);
        }

        return cs;
    }

    // Returns the comparator used in this index.
    public CelestialBodyComparator getComparator() {
        return comparator;
    }

    @Override
    public CelestialBodyIterator iterator() {
        VariantCIterator iter = new VariantCIterator();
        if(root != null) {root.iter(iter, false);}
        return iter;
    }

    public int getSize() {
        return size;
    }
}

class VariantCNode {
    private VariantCNode left;
    private VariantCNode right;
    private CelestialBody key;
    private CelestialSystem cs;
    private CelestialBodyComparator comparator;

    public VariantCNode(CelestialBody key, CelestialSystem cs, VariantCNode left, VariantCNode right,
                        CelestialBodyComparator comparator) {
        this.key = key;
        this.cs = cs;
        this.left = left;
        this.right = right;
        this.comparator = comparator;

    }

    public boolean add(VariantCNode node) {
        if (node.key.equals(this.key)) {
            return false;
        }

        if (this.comparator.compare(this.key, node.key) > 0) {
            if (left == null) {
                left = node;
                return true;
            } else {
                return left.add(node);
            }
        } else {
            if (right == null) {
                right = node;
                return true;
            } else {
                return right.add(node);
            }
        }

    }

    public CelestialSystem get(CelestialBody body) {
        if (key.equals(body)) {
            return cs;
        }

        if (this.comparator.compare(this.key, body) > 0) {
            if (left == null) {
                return null;
            }
            return left.get(body);
        } else {
            if (right == null) {
                return null;
            }
            return right.get(body);
        }

    }

    public String toString() {
        String result;
        result = left == null ? "" : left.toString();
        result += result.isEmpty() ? "" : ",\n";
        result += this.key + " belongs to " + this.cs;
        result += right == null ? "" : ",\n" + right.toString();
        return result;

    }

    public CelestialBody iter(VariantCIterator iter, boolean next) {
        VariantCNode n = next ? right : this;
        while (n != null) {
            new VariantCIterator(n, iter);
            n = n.left;
        }
        return key;
    }


}

class VariantCIterator implements CelestialBodyIterator {

    private VariantCNode node;
    private VariantCIterator parent;

    public VariantCIterator() {}
    public VariantCIterator(VariantCNode n, VariantCIterator p){
        node = p.node;
        p.node = n;
        parent = p.parent;
        p.parent = this;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public CelestialBody next() {
        if (node == null) return null;
        VariantCNode todo = node;
        node = parent.node;
        parent = parent.parent;
        return todo.iter(this, true);
    }

}

class CelestialBodySet implements CelestialBodyCollection {
    CelestialSystemIndexTreeVariantC tree;

    public CelestialBodySet(CelestialSystemIndexTreeVariantC t) {
        tree = t;
    }

    @Override
    public boolean add(CelestialBody body) {
        return false;
    }

    @Override
    public int size() {
        return tree.getSize();
    }

    @Override
    public CelestialBodyIterator iterator() {
        return tree.iterator();
    }

    @Override
    public String toString() {
        return "CelestialBodySet{" +
                "tree=" + tree +
                '}';
    }
}


/*
    ZUSATZFRAGEN:

    1. Ja, da in beiden "Sammlungen" nur die Referenzen zu den Objekten gespeichert werden.
    Daher, die Himmelskörper bleiben immer referenziert.

    2. Sie passen sich an, so wird bei dem Aufruf einer foreach-Schleife immer der aktuelle Zustand des Objekts verwendet.


 */


