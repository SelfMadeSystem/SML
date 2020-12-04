package uwu.smsgamer.sml;

import org.junit.Test;
import uwu.smsgamer.sml.exceptions.SMLParseException;
import uwu.smsgamer.sml.map.*;
import uwu.smsgamer.sml.map.values.*;
import uwu.smsgamer.sml.parser.SMLParser;

import java.io.*;

public class SMLTest {
    private static final File TEST_FILE = new File("run/test.sml");
    private static final char[] chars = new char[]{
      '\\',
      '\'',
      '"',
      'b',
      'f',
      'n',
      'r',
      't'
    };

    @Test
    public void fileTest() throws Exception {
        File file = TEST_FILE;
        System.out.println(file.getAbsolutePath());

        BufferedReader br = new BufferedReader(new FileReader(file));
        br.lines().forEach(System.out::println);
    }

    @Test
    public void mapStringTest() {
        SMLMap map = new SMLMap();
        map.parentNode.value.add(new SMLNode("int", new SMLNum(3)));
        map.parentNode.value.add(new SMLNode("double", new SMLNum(3.3)));
        map.parentNode.value.add(new SMLNode("cool", new SMLBool(true)));
        SMLObject obj = (SMLObject) map.parentNode.value.add(new SMLNode("cool", new SMLObject()));
        obj.add(new SMLNode("owo", new SMLBool(true)));
        obj.add(new SMLNode("gg", new SMLBool(true)));
        obj.add(new SMLNode("wp", new SMLBool(true)));
        obj.add(new SMLNode("wd", new SMLBool(true)));
        SMLObject obj0 = (SMLObject) obj.add(new SMLNode("kkk", new SMLObject()));
        obj0.add(new SMLNode("wd", new SMLBool(true)));
        obj0.add(new SMLNode("basically a comment UwU", SMLNull.getInstance()));
        obj.add(new SMLNode("Array",
          new SMLArray(new SMLBool(true), new SMLNum(3), new SMLArray(new SMLString("Ayeeee")))));
        System.out.println(map.toString());
    }

    @Test
    public void parseTest() throws IOException, SMLParseException {
        SMLMap map = SMLParser.parse(TEST_FILE);
        System.out.println(map.getValue("int"));
        System.out.println(map.getValue("object:obj:cool"));
        System.out.println(map.getValue("object:string"));
        System.out.println(map.getValue("object:string").completeString());
        map.set("object:owo a string", "Herro");
        System.out.println(map.getValue("object:owo a string"));
        map.set("very:super:ultra:cool", "Wow");
        System.out.println(map.getValue("very:super:ultra:cool"));
        System.out.println(map.getInt("int"));
        System.out.println(map.getInt("decimal"));
        System.out.println(map.getFloat("decimal"));
        System.out.println(map.getInt("hex"));
        System.out.println(map.getInt("bin"));
        System.out.println(map.getInt("afgasds:asdsad"));
        System.out.println(map.getString("text"));
        System.out.println("=============================================");
        System.out.println(map.toString());
    }

    @Test
    public void timingTest() throws Exception {
        synchronized (this) {
            long avgDuration = -1;
            long avgDurationMs = -1;
            for (int i = 0; i < 100; i++) {
                final StringBuilder contentBuilder = new StringBuilder("{");
                for (int j = 0; j < 500000; j++) {
                    if (Math.random() < 0.5)
                        contentBuilder.append(":nice").append(Math.random()).append("(").append(Math.random()).append("):");
                    else
                        contentBuilder.append(":owo").append(Math.random()).append("\"\\")
                          .append(chars[(int) (chars.length * Math.random())]).append("\":");
                }
                contentBuilder.append("}");
                long startMs = System.currentTimeMillis();
                long start = System.nanoTime();
                SMLParser.parse(contentBuilder.toString());
                long end = System.nanoTime();
                long endMs = System.currentTimeMillis();
                long duration = end - start;
                long durationMs = endMs - startMs;
                System.out.println("Test #" + i + " Duration: " + duration + "  DurationMS: " + durationMs);
                if (avgDuration == -1) avgDuration = duration;
                else avgDuration = (avgDuration + duration) / 2;
                if (avgDurationMs == -1) avgDurationMs = durationMs;
                else avgDurationMs = (avgDurationMs + durationMs) / 2;
            }
            System.out.println("Test Finished. AvgDuration: " + avgDuration + "  AvgDurationMS: " + avgDurationMs);
        }
    }
}
