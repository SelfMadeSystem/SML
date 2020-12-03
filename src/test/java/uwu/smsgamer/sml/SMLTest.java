package uwu.smsgamer.sml;

import org.junit.Test;
import uwu.smsgamer.sml.exceptions.SMLParseException;
import uwu.smsgamer.sml.map.*;
import uwu.smsgamer.sml.map.values.*;
import uwu.smsgamer.sml.parser.SMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class SMLTest {
    private static final File TEST_FILE = new File("run/test.sml");
    private static final File LONG_FILE = new File("run/long.sml");
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
        synchronized(this) {
            final StringBuilder contentBuilder = new StringBuilder();
            final Stream<String> stream = Files.lines(LONG_FILE.toPath(), StandardCharsets.UTF_8);
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
            long startMs = System.currentTimeMillis();
            long start = System.nanoTime();
            SMLParser.parse(contentBuilder.toString());
            long end = System.nanoTime();
            long endMs = System.currentTimeMillis();
            long duration = end - start;
            long durationMs = endMs - startMs;
            System.out.println(duration + "  " + durationMs);
            if (durationMs >= 10 || duration >= 10000000) {
                throw new Exception("Duration too long :c");
            }
        }
    }
}
