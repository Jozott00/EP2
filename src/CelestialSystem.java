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
            head = new MyNode(body, null, null);
            sizeCount++;
            return true;
        }
        MyNode last = head;
        while (true) {
            if(last.getBody().getName() == body.getName()) return false; //compare every element with body
            if(last.next() == null) break;
            last = last.next();
        }
        last.setNext(new MyNode(body, null, last));
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

    // Inserts the specified 'body' at the specified position
    // in this list if 'i' is a valid index and there is no body
    // in the list with the same name as that of 'body'.
    // Shifts the element currently at that position (if any) and
    // any subsequent elements to the right (adds one to their
    // indices). The first element of the list has the index 0.
    // Returns 'true' if the list was changed as a result of
    // the call, 'false' otherwise.
    public boolean add(int i, CelestialBody body) {
        //TODO: implement method.
        System.out.println(size());
        if(!(this.size() > i)) return false;
        if(this.contains(body.getName())) return false;

        MyNode iNode = head;
        for(int _i = 0; _i < i; _i++){
            iNode = iNode.next();
        }

        MyNode newNode = new MyNode(body, iNode, iNode.prev());
        if(iNode.prev() == null) head = newNode;
        else iNode.prev().setNext(newNode);

        iNode.setPrev(newNode);

        System.out.println(iNode.getBody().getName());

        sizeCount++;
        return true;
    }

    // Returns a readable representation with the name of the
    // system and all bodies in respective order of the list.
    @Override
    public String toString() {
        //TODO: implement method.
        String text = "System " + this.getName() + ": ";
        if(head == null) return text;
        MyNode curr = head;
        while (true) {
            if(curr.next() == null) return text += curr.getBody().getName();
            text += curr.getBody().getName() + ", ";
            curr = curr.next();
        }
    }



    // Returns a new list that contains the same elements as this
    // list in reverse order. The list 'this' is not changed and
    // bodies are not duplicated (shallow copy).
    public CelestialSystem reverse() {
        //TODO: implement method.
        CelestialSystem reverseList = new CelestialSystem("reverseSolarsystem");
        if(head == null) return reverseList;

        MyNode curr = head;
        for(int i = 0; i < size(); i++) {
            reverseList.push(curr.getBody());
            curr = curr.next();
        }

        return reverseList;
    }


    //pushes new bodie element to front of list and shifts every following element to right
    public void push(CelestialBody b) {
        if(this.head == null) {
            head = new MyNode(b, null, null);
            return;
        }

        MyNode newNode = new MyNode(b, head, null);
        head.setPrev(newNode);
        head = newNode;
    }

    //get bodies name of system as array of string
    public String[] getAllNames() {
        if(head == null) return null;
        String[] names = new String[size()];

        MyNode curr = head;
        for(int i = 0; i < size(); i++) {
            names[i] = curr.getBody().getName();
            curr = curr.next();
        }
        return names;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()) return false;

        CelestialSystem system = (CelestialSystem) obj;

        if(this.size() != system.size()) return false;

        for(int i = 0; i < this.size(); i++) {
            if(system.get(this.get(i).getName()) == null) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int sum = 0;

        for(int i = 0; i < this.size(); i++){
            sum += get(i).getName().hashCode();
        }

        return sum;
    }

}
