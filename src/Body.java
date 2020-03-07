
// Body represents a body or object in space with 3D-position, velocity and mass.
// This class will be used by the executable class 'Space'. A body moves through space according to
// its inertia and - if specified - an additional force exerted on it (for example the
// gravitational force of another mass).
public class Body {
    //TODO: class definition.
    /*
    This class should describe the object that will be created later in "space.java".
    For this purpose some variables and methods are needed.
    --- Variables:
        - Current 3-dimensional position of the object (posX, posY, posZ)
        - Current velocity of the object as velocity vector (velX, velY, velZ)
        - Current mass of object (mass)

    --- Methods:
        - Body(): Constructor which is probably not needed
        - setPosition(x, y, z): A setter to overwrite or create the current position.
        - setVelocity(x, y, z): A setter to overwrite or create the current velocity vector.
        - setMass(m): A setter to overwrite or create the current mass.
        - getMass(m): A getter to return the current mass of the object.

        - move(): Executes a movement of the object (simulates one second).
                  Therefore the current position is replaced by the old position plus the current speed.

        - move(fx, fy, fz): Changes the current velocity of the object by a force passed through fx, fy and fz.
                            Current velocity vector is replaced by velocity vector plus force divided by mass.
                            Then move() is executed to perform movement step.

     Question:
     move(seconds, fx, fy, fz) {
        double[] pos = move(fx, fy, fz);
        if (seconds == 1) return pos;
        return move(seconds - 1, fx, fy, fz);
     }
     When the method is called, move(fx, fy, fz) is executed and the returned values are stored as pos.
     Then it is checked if the seconds have expired ( == 1 because move() happens before seconds decrease),
     if so the most recent position is returned. If not the method itself is called again, with one second less.
     If the force action was one-time, you have to set the force parameter to 0 in the recursion call.
     */

    //position of obj
    private double posX;
    private double posY;
    private double posZ;

    //velocity of obj
    private double velX;
    private double velY;
    private double velZ;

    //mass of obj
    private double mass;

    public Body() {
    }

    public void setPosition(double x, double y,  double z) {
        posX = x;
        posY = y;
        posZ = z;
    }

    public void setVelocity(double x, double y, double z) {
        velX = x;
        velY = y;
        velZ = z;
    }

    public void setMass(double m) {
        mass = m;
    }

    public double getMass() {
        return mass;
    }

    public double [] move() {
        posX += velX;
        posY += velY;
        posZ += velZ;

        return new double[] {posX, posY, posZ};
    }

    // new velocity:
    // actual velocity + force / mass
    public double[] move(double fx, double fy, double fz) {

        velX += (fx / mass);
        velY += (fy / mass);
        velZ += (fz / mass);

        System.out.println("ZForce: " + fz );
        System.out.println("Velocity: " + velZ);

        return move();
    }



}
