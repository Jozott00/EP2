public class CelestialSystemIndex {

    //TODO: Define variables and constructor.
    Node root;

    // Adds a system of bodies to the tree. Since the keys of the tree are the names of bodies,
    // adding a system adds multiple (key, value) pairs to the tree, one for each body of the
    // system, with the same value, i.e., reference to the celestial system.
    // An attempt to add a system with a body that already exists in the tree
    // leaves the tree unchanged and the returned value would be 'false'.
    // For example, it is not allowed to have a system ("Earth", "Moon") and a system ("Jupiter",
    // "Moon") indexed by the tree, since "Moon" is not unique.
    // The method returns 'true' if the tree was changed as a result of the call and
    // 'false' otherwise.
    public boolean add(CelestialSystem system) {
        //TODO: implement method.
        String[] bodiesNames = system.getAllNames();

        if(root != null)
            for(String name : bodiesNames) {
                if(root.find(name) != null) return false;
            }

        for(String name : bodiesNames) {
            if(root == null) root = new Node(name, system);
            else if(!root.add(name, system)){
                System.out.println("--- ERROR: CelestialSystem search tree addition went wrong. Tree got manipulated and could store wrong data.");
                return false;
            }
        }
        return true;
    }

    // Returns the celestial system in which a body with the specified name exists.
    // For example, if the specified name is "Europa", the system of Jupiter (Jupiter, Io,
    // Europa, Ganymed, Kallisto) will be returned.
    // If no such system is found, 'null' is returned.
    public CelestialSystem get(String name) {
        //TODO: implement method.
        if(root == null) return null;
        Node node = root.find(name);
        return node == null ? null : node.value();
    }

    // Returns the overall number of bodies indexed by the tree.
    public int numberOfBodies() {
        //TODO: implement method.
        if(root == null) return 0;
        return root.hasBodies();
    }

    // Returns the overall number of systems indexed by the tree.
    public int numberOfSystems() {
        //TODO: implement method.
        if(root == null) return 0;
        ComplexCelestialSystem list = new ComplexCelestialSystem("TreeSystemList");
        root.listAllSystens(list);
        return list.size();
    }

    //TODO: Define additional class(es) implementing a binary tree (either here or outside class).

    public class Node {
        String key;
        CelestialSystem value;

        Node left, right;

        public Node(String key, CelestialSystem value) {
            this.key = key;
            this.value = value;
        }

        private int compare(String k) {
            if(k == null) { return key == null ? 0 : -1;}
            if (key == null) {return 1; }
            return k.compareTo(key);
        }

        public boolean add(String k, CelestialSystem v) {
            int cmp = compare(k);
            if(cmp < 0) {
                if(left != null) return left.add(k, v);
                left = new Node(k, v);
            } else if(cmp > 0) {
                if(right != null) return right.add(k, v);
                right = new Node(k,v);
            } else {
                return false;
            }
            return true;
        }

        public Node find(String k) {
            int cmp = compare(k);
            if(cmp == 0) return this;
            Node node = cmp < 0 ? left : right;
            if (node == null) return null;
            return node.find(k);
        }

        public int hasBodies() {
            int count = 1;
            if(left != null) count += left.hasBodies();
            if(right != null) count += right.hasBodies();

            return count;
        }

        public void listAllSystens(ComplexCelestialSystem list) {
            list.add(value);
            if(left != null) left.listAllSystens(list);
            if(right != null) right.listAllSystens(list);
        }

        public CelestialSystem value() {
            return this.value;
        }

    }

    /*

        Zusatzfrage:

        Man müsste die namen der systeme selbst nochmal als knoten eintragen.
        So wären die namen der systeme als key in jedem knoten vorhanden und richtig sortiert.
        Außerdem würden die rekursive find methode den namen erkennen, ohne dass man diese umbauen muss.

     */

}


