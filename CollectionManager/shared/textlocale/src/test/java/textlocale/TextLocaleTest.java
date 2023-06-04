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
        TextPackage rootPackage = TextLocale.getRootPackage();
        String text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("Hello, world!", text);
        TextLocale.setLocale("es");
        text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("?Hola, mundo!", text);

        text = rootPackage.getText("text1.Hi");
        assertEquals("?Hola, mundo!", text);
    }

    @Test
    public void testHotReload() throws IOException {
        TextLocale.setLocale("en");
        TextPackage rootPackage = TextLocale.getRootPackage();
        String text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("Hello, world!", text);
        TextLocale.setLocale("es");
        text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("?Hola, mundo!", text);

        TextLocale.loadPackage("textlocale.subdir");
        TextLocale.setLocale("en");
        text = rootPackage.getText("subdir2.text4.greet");
        assertEquals("Hello, world!", text);
    }

    @Test
    public void testSubPackaging() {
        TextLocale.setLocale("en");
        TextPackage rootPackage = TextLocale.getRootPackage();
        TextPackage subPackage = rootPackage.getPackage("subdir.subdir2");

        String text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text);

        text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text);

        text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text);

        TextLocale.setLocale("es");

        text = subPackage.getText("text4.greet");
        assertEquals("?Hola, mundo!", text);
    }
}
