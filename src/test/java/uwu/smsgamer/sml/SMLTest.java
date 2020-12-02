package uwu.smsgamer.sml;

import org.junit.Test;
import uwu.smsgamer.sml.exceptions.SMLParseException;
import uwu.smsgamer.sml.map.*;
import uwu.smsgamer.sml.map.values.*;
import uwu.smsgamer.sml.parser.SMLParser;

import java.io.*;

public class SMLTest {
    @Test
    public void fileTest() throws Exception {
        File file = new File("run/test.sml");
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
        SMLMap map = SMLParser.parse(new File("run/test.sml"));
        System.out.println(map.getValue("int"));
        System.out.println(map.getValue("object:obj:cool"));
        System.out.println(map.getValue("object:string"));
        System.out.println(map.getValue("object:string").completeString());
    }
}
