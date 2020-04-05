public class ComplexCelestialSystem {

    //TODO: Define variables.
    private String name;
    private MyNode head;
    private int sizeCount = 0;

    // Initializes this system as an empty system with a name.
    public ComplexCelestialSystem(String name) {
        //TODO: implement constructor.
        this.name = name;
    }

    // Adds a subsystem of bodies to this system if there are no bodies in the subsystem
    // with the same name as a body or subsystem of this system.
    // The method returns 'true' if the collection was changed as a result of the call and
    // 'false' otherwise.
    public boolean add(CelestialSystem subsystem) {
        //TODO: implement method.
        if(head == null) {
            head = new MyNode(subsystem, null, null);
            sizeCount++;
            return true;
        }
        if(head.getSystem().contains((subsystem.getName()))) return false;
        MyNode last = head;
        while (last.next() != null) {
            last = last.next();
            if(last.getSystem().contains((subsystem.getName()))) return false;
        }
        last.setNext(new MyNode(subsystem, null, last));
        sizeCount++;
        return true;
    }

    // Returns the single body or subsystem with 'name' or 'null' if no such body or subsystem 
    // exists in this system. In case of a single body, the body is returned as a subsystem of 
    // one body, with the same name as the body.
    public CelestialSystem get(String name) {
        //TODO: implement method.
        if(head == null) return null;
        MyNode sNode = head;
        while (true){
            // if the current node's subsystem name matches the given name, return the subsystem
            if(sNode.getSystem().getName() == name) return sNode.getSystem();

            // search for name of body in current node's subsystem
            CelestialBody sBody = sNode.getSystem().get(name);

            // if a body in node's subsystem exists with the same name as give, return a new subsystem with this body
            if(sBody != null) {
                CelestialSystem result = new CelestialSystem(name);
                result.add(sBody);
                return result;
            }

            // when next node is null, breakout of the loop
            if(sNode.next() == null) break;
        }
        return null;
    }

    // Returns the number of bodies of the entire system.
    public int size() {
        //TODO: implement method.
        return this.sizeCount;
    }

    public boolean contains(String name) {
        if(head == null) return false;

        MyNode curr = head;
        for(int i = 0; i < size(); i++){
            if(curr.getSystem().getName() == name) return true;
            String[] names = curr.getSystem().getAllNames();
            for(String n : names) {
                if(n == name) return true;
            }
            curr = curr.next();
        }

        return false;
    }

    //TODO: Define additional class(es) implementing a linked list (either here or outside class).
}


