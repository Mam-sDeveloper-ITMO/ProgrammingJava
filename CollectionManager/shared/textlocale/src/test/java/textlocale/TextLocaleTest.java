package textlocale;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TextLocaleTest {
    @Before
    public void setUp() throws IOException {
        TextLocale.loadPackage("textlocale");
    }

    @Test
    public void testLoadPackage() throws IOException {
        TextLocale.setLocale("en");
        String text = TextLocale.getText("subdir.subdir2.text4.greet");
        assertEquals("Hello, world!", text);
        TextLocale.setLocale("es");
        text = TextLocale.getText("subdir.subdir2.text4.greet");
        assertEquals("¡Hola, mundo!", text);
    }
}
