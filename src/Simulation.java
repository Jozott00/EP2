import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {


        solarSystemSimulation(args);

//        indexTreeVariantCTest();

//        bodySetTreeVariantCTest();

//        bodySetInitTest();
    }

    private static void test() throws FileFormatException {
        String test = "2g3";

        try {
            System.out.println("1");
            try {
                Double.parseDouble(test);
            } catch (NumberFormatException e){
                throw new FileFormatException(2, "her");
            }
            System.out.println("2");
        } catch (ArithmeticException e) {
            System.out.println("fuck is aufgefangen");
        }
    }

    private static void bodySetInitTest() {
        double AU = 150e9;

        CelestialBody sun = null, mercury = null, venus = null, earth = null, mars = null;

        // TODO: implement suitable constructor in class 'CelestialBody' and include block:
        // Parameters: name, mass, radius, position, velocity, color

        sun = new CelestialBody("Sol", 1.989e30, 696340e3, new Vector3(0, 0, 0),
                new Vector3(0, 0, 0), StdDraw.YELLOW);

        mercury = new CelestialBody("Mercury", 0.330114e23, 2439.4e3,
                new Vector3(0, 0, 0), new Vector3(0, 0, 0), StdDraw.GRAY);

        venus = new CelestialBody("Venus", 4.86747e24, 6051.8e3, new Vector3(0, 0
                , 0), new Vector3(0, 0, 0), StdDraw.PINK);

        earth = new CelestialBody("Earth", 5.97237e24, 6371.0084e3, new Vector3(0, 0, 0),
                new Vector3(0, 0, 0), StdDraw.BLUE);

        mars = new CelestialBody("Mars", 0.641712e24, 3389.5e3, new Vector3(0, 0, 0),
                new Vector3(0, 0, 0), StdDraw.RED);


        CelestialSystem ss = new CelestialSystem("Solarsystem");
        ss.add(mercury);
        ss.add(venus);
        ss.add(earth);
        ss.add(mars);

        try {
            ReadDataUtil.readConfiguration("Hell", ss, 21);
            ss.add(sun);
        } catch (FileNotFoundException | FileFormatException e) {
            e.printStackTrace();
        }


    }

    private static void bodySetTreeVariantCTest() {

        CelestialBody a = new CelestialBody("a", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody b = new CelestialBody("b", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody c = new CelestialBody("c", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody d = new CelestialBody("d", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody e = new CelestialBody("e", 0, 0, new Vector3(), new Vector3(), Color.white);

        CelestialBody b5 = new CelestialBody("body2", 0, 0, new Vector3(), new Vector3(), Color.white);

        CelestialSystem s1 = new CelestialSystem("system1");
        CelestialSystem s2 = new CelestialSystem("system2");

        CelestialBodyNameComparator comp = new CelestialBodyNameComparator();

        CelestialSystemIndexTreeVariantC tree = new CelestialSystemIndexTreeVariantC(comp);

        CelestialBodyCollection bodySet = tree.bodies();

        s1.add(a);
        s1.add(d);
        s1.add(c);

        tree.add(s1);


        System.out.println(bodySet);

        CelestialSystem copy = tree.bodiesAsCelestialSystem();


        s2.add(b);
        s2.add(e);
//        s2.add(b5);

        tree.add(s2);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(bodySet);

        for (CelestialBody bo : bodySet) {
            System.out.println(bo.getName());
        }
        System.out.println("\n\n" + copy);
        System.out.println("\n\n" + tree.bodiesAsCelestialSystem());


    }

    private static void indexTreeVariantCTest() {

        CelestialBody a = new CelestialBody("a", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody b = new CelestialBody("b", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody c = new CelestialBody("c", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody d = new CelestialBody("d", 0, 0, new Vector3(), new Vector3(), Color.white);
        CelestialBody e = new CelestialBody("e", 0, 0, new Vector3(), new Vector3(), Color.white);

        CelestialBody b5 = new CelestialBody("body2", 0, 0, new Vector3(), new Vector3(), Color.white);

        CelestialSystem s1 = new CelestialSystem("system1");
        CelestialSystem s2 = new CelestialSystem("system2");

        CelestialBodyNameComparator comp = new CelestialBodyNameComparator();

        CelestialSystemIndexTreeVariantC tree = new CelestialSystemIndexTreeVariantC(comp);

        s1.add(a);
        s1.add(d);
        s1.add(c);

        tree.add(s1);

        System.out.println("\n Tree: " + tree);


        s2.add(b);
        s2.add(e);
//        s2.add(b5);

        tree.add(s2);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println("\n Tree: " + tree);

        for (CelestialBody bo : tree) {
            System.out.println(bo.getName());
        }

    }

    private static void solarSystemSimulation(String[] args) {
        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.

        if(args.length != 2) {
            System.out.println("Error: wrong number of arguments (2 arguments needed).");
            System.exit(0);
        }

        try {
            int day = Integer.parseInt(args[1]);

            if(!(0 < day && day < 367)) {
                System.out.println("Error: Invalid day argument(" + day +").");
                System.exit(0);
            }

            CelestialSystem bodies = ReadDataUtil.initialize(day, args[0]);

            Vector3[] forceOnBody = new Vector3[bodies.size()];

            StdDraw.setCanvasSize(500, 500);
            StdDraw.setXscale(-2 * AU, 2 * AU);
            StdDraw.setYscale(-2 * AU, 2 * AU);
            double pixelWidth = 4 * AU / 500;
            StdDraw.enableDoubleBuffering();
            StdDraw.clear(StdDraw.BLACK);

            double seconds = 0;

//            // simulation loop
//            while (true) {
//
//                seconds++; // each iteration computes the movement of the celestial bodies within one second.
//
//                // for each body (with index i): compute the total force exerted on it.
//                for (int i = 0; i < bodies.size(); i++) {
//                    //                forceOnBody[i] = new Vector3(0,0,0); // begin with zero
//                    Vector3 sumForce = new Vector3();
//                    for (int j = 0; j < bodies.size(); j++) {
//                        if (i == j) continue;
//                        Vector3 forceToAdd = bodies.get(i).gravitationalForce(bodies.get(j));
//                        sumForce = sumForce.plus(forceToAdd);
//                    }
//                    bodies.get(i).move(sumForce);
//
//                }
//                // now forceOnBody[i] holds the force vector exerted on body with index i.
//
//                // for each body (with index i): move it according to the total force exerted on it.
//                //            for (int i = 0; i < bodies.size(); i++) {
//                //               bodies.get(i).move(forceOnBody[i]);
//                //            }
//
//                // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
//                if (seconds % (3 * 3600) == 0) {
//                    // clear old positions (exclude the following line if you want to draw orbits).
//                    StdDraw.clear(StdDraw.BLACK);
//
//                    // draw new positions
//                    for (int i = 0; i < bodies.size(); i++) {
//                        bodies.get(i).draw();
//                    }
//
//                    // show new positions
//                    StdDraw.show();
//                }
//            }

            System.out.println("Running Simulation...");

        } catch (FileNotFoundException e) {
            System.out.println("Error: File \"" + e.getPath() +"\" not found.");
            System.exit(0);
        } catch (FileFormatException e) {
            System.out.println("Error: File \""+ e.getFileName() +" does not have required format in line " + e.getLine() +" (aborting).");
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("Error: Day argument not of type int");
            System.exit(0);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
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

        return new CelestialBody[]{sun, mercury, venus, earth, mars};
    }

}

//TODO: answer additional questions of 'Aufgabenblatt2'.

/*
Data-Hiding: Variablen verstecken, in dem man Zugriff von außen blockiert.

Datenkapselung: zusammenfassen von Methoden und Variablen zu einer Einheit.
 */

