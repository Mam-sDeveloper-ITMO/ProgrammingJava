package textlocale;

import textlocale.utils.FilesUtils;
import textlocale.utils.TLJsonUtils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TextLocaleTest {
    @Before
    public void setUp() throws IOException {
        TextLocale.loadPackage("textlocale");
        System.out.println();
    }

    @Test
    public void testLoadPackageWithValidPackage() throws IOException {
        System.out.println("loadPackageWithValidPackage");
    }
}
