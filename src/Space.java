import java.util.Arrays;

// Space is the actual program (executable class) using objects of class 'Body'.
public class Space {

    // Some constants helpful for the simulation (particularly the 'falling ball' example).
    public static final double G = 6.6743e-11; // gravitational constant
    public static final double MASS_OF_EARTH = 5.972e24; // kg
    public static final double MASS_OF_BALL = 1; // kg
    public static final double RADIUS_OF_EARTH = 6.371e6; // m (meters)

    // On the surface of earth the gravitational force on the ball weighing 1kg is
    // approximately as follows:
    public static final double GRAVITATIONAL_FORCE_ON_BALL =
            G*MASS_OF_EARTH*MASS_OF_BALL/(RADIUS_OF_EARTH*RADIUS_OF_EARTH); // kg*m/sec² ... F = mass * acc
    // This means each second its speed increases about 9.82 meters per second.

    //TODO: further variables, if needed.
    public static final double ROCKET_FORCE = 5e6;
    public static final double ROCKET_START_MASS = 2.9e5;
    public static final double ROCKET_FUEL_TANK = 2.55e5;
    public static final double ROCKET_FUELBURN_PER_SECOND = 1333;

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: implement method.

        //One test case is given:
        //Results for the falling ball on the surface of earth are as follows:
        //Height 10m: 2 (sec = number of move(fx,fy,fz) calls)
        //Height 100m: 5 (sec = number of move(fx,fy,fz) calls)

        Body ball1 = new Body();
        ball1.setPosition(0,0,100); // 100m height.
        ball1.setVelocity(0,0,0);
        ball1.setMass(1); // 1 kg
//        System.out.println(fallToGround(ball1)); // 5


        ball1.setPosition(0,0,10); // 10m height.
        ball1.setVelocity(0,0,0);
//        System.out.println(fallToGround(ball1)); // 2


        Body ball2 = new Body();
        ball2.setPosition(0,0,100); // 100m height.
        ball2.setVelocity(0,0,0);
        ball2.setMass(15); // 15 kg
//        System.out.println(fallToGround(ball2)); // 5


        //Further examples are to be tested (body in empty space, rocket, feather).

        Body obj = new Body();
        obj.setPosition(0,0,0);
        obj.setVelocity(1,0.2, 0.5);
//        System.out.println("Position after ... seconds: " + Arrays.toString(positionInEmptySpace(obj, 5)));


        Body rocket = new Body();
        rocket.setPosition(0,0,0);
        rocket.setVelocity(0,0, 0);
        rocket.setMass(ROCKET_START_MASS);
        System.out.println("Rocket landed after " + rocketFlightInSeconds(rocket) + " seconds");

        rocket.setVelocity(0,0,0);
        rocket.setPosition(0,0,0);
        rocket.setMass(ROCKET_START_MASS);
//        System.out.println("Position after ... seconds: " + Arrays.toString(positionOfRocket(rocket, 5)));


        Body feather = new Body();
        feather.setPosition(0,0,5);
        feather.setVelocity(0,0,0);
        feather.setMass(8.2e-6);
//        System.out.println("Position after ... seconds or touching ground: " + Arrays.toString(randomWindinputOnFeather(feather, 6)));


    }

    // Returns the number of move(fx,fy,fz) calls needed for 'b' hitting the ground, i.e.,
    // the method reduces the z-coordinate of 'b' until it becomes 0 or negative.
    public static int fallToGround(Body b) {
        //TODO: implement recursive method.
        double[] pos = b.move(0.0, 0.0, -gravCalc(b.getMass()));

        if (pos[2] <= 0) {
            return 1;
        }
        int calls = fallToGround(b);
        return ++calls;
    }

    //TODO: Define further methods as needed.

    //returns position after ... seconds in empty space
    public static double[] positionInEmptySpace(Body obj, int sec) {
        double[] objPos = obj.move();

        //if sec == 1 because move() runs before sec decrement
        if(sec == 1)
            return objPos;

        return positionInEmptySpace(obj, sec - 1);
    }

    //returns position of rocket after ... seconds in empty space
    public static double[] positionOfRocket(Body rocket, int sec) {
        double[] rocketPos = rocket.move(0, 0, ROCKET_FORCE);

        //check if fuel > 0, if so decrement the mass by ROCKET_FUELBURN_PER_SECOND
        double rocketMass = rocket.getMass();
        double fuel = rocketMass - (ROCKET_START_MASS - ROCKET_FUEL_TANK);
        if(fuel > 0) rocket.setMass(rocketMass - ROCKET_FUELBURN_PER_SECOND);

        if(sec == 1) return rocketPos;

        return positionOfRocket(rocket, sec - 1);
    }

    //returns flight time in seconds after returning to ground (same as fallToGround() just with some extras)
    public static int rocketFlightInSeconds(Body rocket) {
        double rocketMass = rocket.getMass(); //complete rocket mass
        double grav = gravCalc(rocketMass); //gravitational force on rocket
        double fuel = rocketMass - (ROCKET_START_MASS - ROCKET_FUEL_TANK); //available fuel in rocket
        double thrust = fuel > 0 ? ROCKET_FORCE : 0; //thrust of rocket (constant, only if fuel > 0)

        System.out.println("Fuel: " + fuel);
        System.out.println("Thrust: " + thrust);
        System.out.println("GravForce: " + grav);

        //calc next position of rocket (thrust force of rocket minus gravitational force on rocket) ... only z-axis
        double[] pos = rocket.move(0.0, 0.0, thrust - gravCalc(rocketMass));

        System.out.println("Position: " + Arrays.toString(pos));

        // on reaching ground
        if(pos[2] <= 0)
            return 1;

        // fuel-burn per second: 133 liter
        if(fuel > 0)
            rocket.setMass(rocketMass-ROCKET_FUELBURN_PER_SECOND);

        System.out.println("-------------");

        int calls = rocketFlightInSeconds(rocket);
        return ++calls;
    }

    //returns position of feather after ... seconds. Position of feather is random, caused of the random wind force
    public static double[] randomWindinputOnFeather(Body feather, int sec) {
        double grav = gravCalc(feather.getMass());

        //generate values -1 to 1. e.g: 0.4 * 1^3
        double fx = Math.random() * Math.pow(-1, (int) (Math.random() * 10));
        double fy = Math.random() * Math.pow(-1, (int) (Math.random() * 10));
        double fz = Math.random() * Math.pow(-1, (int) (Math.random() * 10));

        double[] featherPos = feather.move(fx, fy, fz - grav);

        if(featherPos[2] <= 0 || sec == 1) return featherPos;

        return randomWindinputOnFeather(feather, sec-1);
    }

    //own gravitational force calculation with variable mass
    public static double gravCalc(double objMass) {
        return G*MASS_OF_EARTH*objMass/(RADIUS_OF_EARTH*RADIUS_OF_EARTH);
    }

}



