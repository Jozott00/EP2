import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ReadDataUtil {
    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static double AU = 150e9;

    // Returns the solar system (inner planets only) in the configuration on the specified day.
    // Precondition: day > 10 && day < 356 (precondition needed due to velocity calculation).
    public static CelestialSystem initialize(int day, String path) throws FileNotFoundException, FileFormatException {
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


//        CelestialBody[] planets = new CelestialBody[]{earth, mercury, venus, mars};
//        CelestialSystem solarsystem = ReadDataUtil.readConfiguration("Solarsystem", planets, day);

        CelestialSystem solarsystem = new CelestialSystem("Solarsystem");
        solarsystem.add(mercury);
        solarsystem.add(venus);
        solarsystem.add(earth);
        solarsystem.add(mars);

        readConfiguration(path, solarsystem, day);

        solarsystem.add(sun);
        return solarsystem;
    }

    // Reads the positions of the specified bodies on the specified day in the year 2020
    // from the file 'Configuration.csv' and returns a linked list of the celestial bodies,
    // where position and current velocity of each body is set accordingly.
    public static CelestialSystem readConfiguration(String name, CelestialBody[] bodies, int day) {

        CelestialSystem result = new CelestialSystem(name);

        In fileReader = new In("Configuration.csv");

        String[] lines = fileReader.readAllLines();

        for (CelestialBody body : bodies) {
            for (int i = 1; i < lines.length - 1; i++) {
                String[] fields = lines[i].split(";");
                Vector3 velocity = new Vector3(0, 0, 0);
                Vector3 position = new Vector3(0, 0, 0);

                //TODO: implement getName() in 'CelestialBody'.
                //TODO: implement missing constructor in class 'CelestialBody'.
                //TODO: include following block.

                if (fields[0].equals(body.getName()) && Integer.parseInt(fields[2]) == day) {
                    int j = 0;
                    while (velocity.length() < 1e9) {
                        j++;
                        String[] fieldsNext = lines[i + j].split(";");
                        String[] fieldsPrev = lines[i - j].split(";");
                        double lon = Double.parseDouble(fields[5]);
                        double lat = Double.parseDouble(fields[4]);
                        double rad = Double.parseDouble(fields[3]) * AU;

                        double lonNext = Double.parseDouble(fieldsNext[5]);
                        double latNext = Double.parseDouble(fieldsNext[4]);
                        double radNext = Double.parseDouble(fieldsNext[3]) * AU;
                        double lonPrev = Double.parseDouble(fieldsPrev[5]);
                        double latPrev = Double.parseDouble(fieldsPrev[4]);
                        double radPrev = Double.parseDouble(fieldsPrev[3]) * AU;

                        position = asCartesian(lon, lat, rad);
                        Vector3 posNext = asCartesian(lonNext, latNext, radNext);
                        Vector3 posPrev = asCartesian(lonPrev, latPrev, radPrev);

                        velocity = posNext.minus(posPrev);
                    }
                    velocity = velocity.times(1d / (2 * j * 3600 * 24));
                    result.add(new CelestialBody(body, position, velocity));
                }

            }
        }

        return result;
    }

    // transforms degree to radiant.
    public static double radiant(double degree) {
        return 2 * Math.PI * degree / 360;
    }

    // transforms radiant to dergee.
    public static double degree(double radiant) {
        return radiant * 180 / Math.PI;
    }

    // transforms polar coordinates to Cartesian coordinates.
    public static Vector3 asCartesian(double lon, double lat, double r) {
        double x, y, z;
        x = r * Math.cos(radiant(lat)) * Math.cos(radiant(lon));
        y = r * Math.cos(radiant(lat)) * Math.sin(radiant(lon));
        z = r * Math.sin(radiant(lat));
        return new Vector3(x, y, z);
    }


    // Reads the positions of the specified 'bodies' on the
    // specified 'day' in the year 2020 from the file with
    // the specified 'path', and sets position and current
    // velocity of each body in the collection accordingly.
    // Each line in the file is required to have the
    // following format:
    // BODY;YEAR;DAY;RAD_AU;SE_LAT;SE_LON
    // where BODY is a string, YEAR and DAY is a positive
    // integer, and the last three numbers are interpretable
    // as 'double' values. The first line (header) is ignored.
    // The character ';' must only be used as field separator.
    // If the file is not found, an exception of the class
    // 'FileNotFoundException' is thrown. If it does not
    // comply with the format described above, the method
    // throws an exception of the class 'FileFormatException'.
    // Both exceptions are subtypes of 'IOException'.
    public static void readConfiguration(String path, CelestialBodyCollection bodies, int day) throws FileFormatException, FileNotFoundException {
        // TODO: implement method.

        ArrayList<String> lineList = new ArrayList<>();

        try {
            BufferedReader in = null;

            File file = new File(path);
            if(!file.exists())
                throw new FileNotFoundException(path);


            try {
                Reader r = new FileReader(file);
                in = new BufferedReader(r);

                String line;
                while ((line = in.readLine()) != null) {
                    lineList.add(line);
                }

                String[] lines = lineList.toArray(new String[lineList.size()]);

                for (int i = 1; i < lines.length - 1; i++) {
                    String[] fields = lines[i].split(";");

                    if(fields.length != 6) throw new FileFormatException(i+1,path);

                    for (int j = 1; j < fields.length; j++) {
                        try {
                            if(j == 1 || j == 2) {
                                int ps = Integer.parseInt(fields[j]);
                                if(ps <= 0) throw new FileFormatException(i+1, path);
                            }
                            if (j > 2) Double.parseDouble(fields[j]);
                        } catch (NumberFormatException e) {
                            throw new FileFormatException(i+1, path);
                        }
                    }
                }

                for (CelestialBody body : bodies) {
                    for (int i = 1; i < lines.length - 1; i++) {
                        String[] fields = lines[i].split(";");

                        Vector3 velocity = new Vector3(0, 0, 0);
                        Vector3 position = new Vector3(0, 0, 0);

                        //TODO: implement getName() in 'CelestialBody'.
                        //TODO: implement missing constructor in class 'CelestialBody'.
                        //TODO: include following block.

                        if (fields[0].equals(body.getName()) && Integer.parseInt(fields[2]) == day) {
                            int j = 0;
                            while (velocity.length() < 1e9) {
                                j++;
                                String[] fieldsNext = lines[i + j].split(";");
                                String[] fieldsPrev = lines[i - j].split(";");
                                double lon = Double.parseDouble(fields[5]);
                                double lat = Double.parseDouble(fields[4]);
                                double rad = Double.parseDouble(fields[3]) * AU;

                                double lonNext = Double.parseDouble(fieldsNext[5]);
                                double latNext = Double.parseDouble(fieldsNext[4]);
                                double radNext = Double.parseDouble(fieldsNext[3]) * AU;
                                double lonPrev = Double.parseDouble(fieldsPrev[5]);
                                double latPrev = Double.parseDouble(fieldsPrev[4]);
                                double radPrev = Double.parseDouble(fieldsPrev[3]) * AU;

                                position = asCartesian(lon, lat, rad);
                                Vector3 posNext = asCartesian(lonNext, latNext, radNext);
                                Vector3 posPrev = asCartesian(lonPrev, latPrev, radPrev);

                                velocity = posNext.minus(posPrev);
                            }
                            velocity = velocity.times(1d / (2 * j * 3600 * 24));
                            body = new CelestialBody(body, position, velocity);
                        }
                    }
                }
            } finally {
                if (in != null)
                    in.close();
            }
        } catch (IOException e) {
            throw new FileNotFoundException(path);
        }
    }
}
