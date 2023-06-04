package textlocale;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TextLocaleTest {
    static String locale = "en";

    static TextPackage rootPackage;

    @Before
    public void setUp() throws Exception {
        rootPackage = TextLocale.loadPackage("textlocale", () -> TextLocaleTest.locale);
    }

    @Test
    public void testLoadPackage() throws IOException {
        locale = "en";
        String text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("Hello, world!", text);
        locale = "es";
        text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("?Hola, mundo!", text);

        text = rootPackage.getText("text1.Hi");
        assertEquals("?Hola, mundo!", text);
    }

    @Test
    public void testSubPackaging() {
        locale = "en";
        TextPackage subPackage = rootPackage.getPackage("subdir.subdir2");

        String text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text);

        text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text);

        text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text);

        locale = "es";

        text = subPackage.getText("text4.greet");
        assertEquals("?Hola, mundo!", text);
    }
}
