public class CelestialSystem {

    //TODO: Define variables.
    private String name;
    private MyNode head;
    private int sizeCount = 0;

    // Initialises this system as an empty system with a name.
    public CelestialSystem(String name) {
        //TODO: implement constructor.
        this.name = name;
    }

    // Adds 'body' to the list of bodies of the system if the list does not already contain a
    // body with the same name as 'body', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(CelestialBody body) {
        //TODO: implement method.
        if(head == null) {
            head = new MyNode(body, null);
            sizeCount++;
            return true;
        }
        MyNode last = head;
        while (true) {
            if(last.getBody().getName() == body.getName()) return false; //compare every element with body
            if(last.next() == null) break;
            last = last.next();
        }
        last.setNext(new MyNode(body, null));
        sizeCount++;
        return true;
    }

    // Returns the 'body' with the index 'i'. The body that was first added to the list has the
    // index 0, the body that was most recently added to the list has the largest index (size()-1).
    public CelestialBody get(int i) {
        //TODO: implement method.
        if(head == null) return null;
        MyNode iNode = head; //index node
        for(int _i = 0; _i < i; _i++ ) {
            iNode = iNode.next();
        }
        return iNode.getBody();
    }

    // Returns the body with the specified name or 'null' if no such body exits in the list.
    public CelestialBody get(String name) {
        //TODO: implement method.
        if(head == null) return null;
        if(head.getBody().getName() == name) return head.getBody();
        MyNode sNode = head; //search node
        while (sNode.next() != null) {
            sNode = sNode.next();
            if(sNode.getBody().getName() == name) return sNode.getBody() ; //compare every element's bodyname with name
        }
        return null;
    }

    // returns the number of entries of the list.
    public int size() {
        //TODO: implement method.
        return this.sizeCount;
    }

    public String getName() {
        return this.name;
    }

    //check if this system contains body or itself with given name
    public boolean contains(String name) {
        if(this.name == name) return true;
        MyNode last = head;
        while (true) {
            if(last.getBody().getName() == name) return true;
            if(last.next() == null) break;
            last = last.next();
        }
        return false;
    }

    //TODO: Define additional class(es) implementing the linked list (either here or outside class).
}
