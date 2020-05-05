import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class MyTestClass {


    public static void main(String[] args) {

        test1();

    }

    private static void test2() {

        CelestialBody b0 = new CelestialBody("b0" , 0, 0, new Vector3(), new Vector3(), Color.white );
        CelestialBody b1 = new CelestialBody("b1" , 0, 0, new Vector3(), new Vector3(), Color.white );
        CelestialBody b2 = new CelestialBody("body2" , 0, 0, new Vector3(), new Vector3(), Color.white );
        CelestialBody b3 = new CelestialBody("b3" , 0, 0, new Vector3(), new Vector3(), Color.white );
        CelestialBody b4 = new CelestialBody("b4" , 0, 0, new Vector3(), new Vector3(), Color.white );

        CelestialBody b5 =  new CelestialBody("body2" , 0, 0, new Vector3(), new Vector3(), Color.white );

        CelestialSystem s1 = new CelestialSystem("system1");
        CelestialSystem s2 = new CelestialSystem("system2");

        CelestialSystemIndexMap map = new CelestialSystemIndexMap();

        s1.add(b0);
        s1.add(b1);
        s1.add(b2);
//        s2.add(b5);
        s2.add(b3);
        s2.add(b4);

        map.add(s1);
        map.add(s2);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(map);
        System.out.println(b0.hashCode() + ", " + b1.hashCode());

        System.out.println(Arrays.toString(s1.toArray()));

    }

    private static void test1() {
        Random r = new Random();

        CelestialSystemIndexMap map = new CelestialSystemIndexMap();
        CelestialSystemIndexMap map2 = new CelestialSystemIndexMap();
        CelestialBody[] bodies = new CelestialBody[50];
        CelestialSystem[] systems = new CelestialSystem[5];

        CelestialBody testB = new CelestialBody("b0" , r.nextInt(), r.nextDouble(), new Vector3(), new Vector3(), Color.white );


        for(int i = 0; i < systems.length; i++) {
            systems[i] = new CelestialSystem("sys" + i);
        }


        for(int i = 0; i < bodies.length/2; i++) {
            bodies[i] = new CelestialBody("b" + i, r.nextInt(), r.nextDouble(), new Vector3(), new Vector3(), Color.white );
            systems[(i % 5)].add(bodies[i]);
        }

        for(CelestialSystem i : systems) {
            map.add(i);
            map2.add(i);
        }

        System.out.println(Arrays.toString(systems));
        System.out.println(map);
        System.out.println(map.get(bodies[4]));
        System.out.println(map.equals(map2));
        System.out.println(map);
        System.out.println(map2);
        System.out.println(map.contains(bodies[20]));
    }
}
