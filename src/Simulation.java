import java.awt.*;

public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.
        CelestialSystem bodies = ReadDataUtil.initialize(60);
        Vector3[] forceOnBody = new Vector3[bodies.size()];

        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        double pixelWidth = 4*AU/500;
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        // simulation loop
        while(true) {

            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // for each body (with index i): compute the total force exerted on it.
            for (int i = 0; i < bodies.size(); i++) {
//                forceOnBody[i] = new Vector3(0,0,0); // begin with zero
                Vector3 sumForce = new Vector3();
                for (int j = 0; j < bodies.size(); j++) {
                    if (i == j) continue;
                    Vector3 forceToAdd = bodies.get(i).gravitationalForce(bodies.get(j));
                    sumForce = sumForce.plus(forceToAdd);
                }
                bodies.get(i).move(sumForce);

            }
            // now forceOnBody[i] holds the force vector exerted on body with index i.

            // for each body (with index i): move it according to the total force exerted on it.
//            for (int i = 0; i < bodies.size(); i++) {
//               bodies.get(i).move(forceOnBody[i]);
//            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < bodies.size(); i++) {
                   bodies.get(i).draw();
                }

                // show new positions
                StdDraw.show();
            }

        }

    }

    public static CelestialBody[] getOtherSystem() {
        Vector3 mercuryPosistion = new Vector3(-100, 30, 5);
        Vector3 mercuryVelocity = new Vector3(-1, 1, -1);

        Vector3 venusPosistion = new Vector3(40, -150, -40);
        Vector3 venusVelocity = new Vector3(3, 2, -4);

        Vector3 earthPosistion = new Vector3(60, -120, -10);
        Vector3 earthVelocity = new Vector3(5, -3, 2);

        Vector3 marsPosistion = new Vector3(-29, -12, -51);
        Vector3 marsVelocity = new Vector3(4, -10, -4);

        CelestialBody sun = new CelestialBody("Sun", 50, 40, new Vector3(), new Vector3(), StdDraw.YELLOW);
        CelestialBody mercury = new CelestialBody("Mercury", 12, 10, mercuryPosistion, mercuryVelocity,
                StdDraw.GRAY);
        CelestialBody venus = new CelestialBody("Venus", 5, 5, venusPosistion, venusVelocity,
                StdDraw.PINK);
        CelestialBody earth = new CelestialBody("Earth", 29, 12, earthPosistion, earthVelocity,
                StdDraw.BLUE);
        CelestialBody mars = new CelestialBody("Mars", 5, 3, marsPosistion, marsVelocity, StdDraw.RED);

        return new CelestialBody[] { sun, mercury, venus, earth, mars };
    }

}

//TODO: answer additional questions of 'Aufgabenblatt2'.

/*
Data-Hiding: Variablen verstecken, in dem man Zugriff von außen blockiert.

Datenkapselung: zusammenfassen von Methoden und Variablen zu einer Einheit.
 */

