import java.awt.*;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class CelestialBody {
    // gravitational constant
    private static final double G = 6.6743e-11;
    // one astronomical unit (AU) is the average distance of earth to the sun.
    private static final double AU = 150e9;


    //TODO: change modifiers.
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Color color; // for drawing the body.

    //TODO: define constructor.
    public CelestialBody(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
    }

    public CelestialBody(CelestialBody body, Vector3 position, Vector3 velocity) {
        this.name = body.name;
        this.mass = body.mass;
        this.radius = body.radius;
        this.color = body.color;
        this.position = position;
        this.currentMovement = velocity;
    }

    public String getName() {
        return this.name;
    }

    // Returns the distance between this celestial body and the specified 'body'.
    public double distanceTo(CelestialBody body) {

        //TODO: implement method.
        return 0;
    }

    // Returns a vector representing the gravitational force exerted by 'body' on this celestial body.
    public Vector3 gravitationalForce(CelestialBody body) {
        //TODO: implement method.
        Vector3 direction = body.position.minus(this.position);
        double r = direction.length();
        direction.normalize();
        double force = G * this.mass * body.mass / (r * r);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force.)
    public void move(Vector3 force) {

        //TODO: implement method.
        Vector3 newPosition = this.currentMovement.plus(this.position.plus(force.times(1 / this.mass)));
        Vector3 newMovement = newPosition.minus(this.position); // new minus old position.

        this.position = newPosition;
        this.currentMovement = newMovement;
    }

    // Returns a string with the information about this celestial body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    @Override
    public String toString() {

        //TODO: implement method.
        return String.format("[%s, %s kg, radius: %s m, position: %s m, movement: %s m/s.]", this.name, this.mass, this.radius, this.position.toString(), this.currentMovement.toString());
    }

    // Prints the information about this celestial body including
    // name, mass, radius, position and current movement, to the console (without newline).
    // Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s.
    public void print() {

        //TODO: implement method.
        System.out.println(toString());
    }

    // Draws the celestial body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    public void draw() {

        //TODO: implement method.
        position.drawAsDot(this.radius, this.color);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()) return false;

        CelestialBody cb = (CelestialBody) obj;

        return cb.getName().equals(this.name);
    }

    @Override
    public int hashCode() {

        int hc = name.hashCode();
        int sum = 0;
        for(int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            c *= 31^(name.length()-i+1) * hc/(i+1);
            sum += c;
        }

        return sum;
    }


}

