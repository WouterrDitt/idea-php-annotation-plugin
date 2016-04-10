package de.espend.idea.php.annotation.tests.navigation;

import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import de.espend.idea.php.annotation.tests.AnnotationLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see de.espend.idea.php.annotation.navigation.AnnotationGoToDeclarationHandler
 */
public class AnnotationGoToDeclarationHandlerTest extends AnnotationLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("classes.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatPhpDocOfNamespaceProvidesNavigation() {
        assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                "namespace Bar;\n" +
                "\n" +
                "use Foo\\Bar;\n" +
                "\n" +
                "/**\n" +
                " * @B<caret>ar()\n" +
                " */\n" +
                "class Foo\n" +
                "{}\n",
            PlatformPatterns.psiElement(PhpClass.class)
        );
    }

    public void testThatPhpDocOfFileScopeProvidesNavigation() {
        assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                "use Foo\\Bar;\n" +
                "\n" +
                "/**\n" +
                " * @B<caret>ar()\n" +
                " */\n" +
                "class Foo\n" +
                "{}\n",
            PlatformPatterns.psiElement(PhpClass.class)
        );
    }

    public void testThatPhpDocOfInlineProvidesNavigation() {
        assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                "namespace Bar;\n" +
                "use Foo\\Bar;\n" +
                "\n" +
                "class Foo\n" +
                "{\n" +
                "  /** @B<caret>ar */" +
                "}\n",
            PlatformPatterns.psiElement(PhpClass.class)
        );

        assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                "use Foo\\Bar;\n" +
                "\n" +
                "class Foo\n" +
                "{\n" +
                "  /** @B<caret>ar */" +
                "}\n",
            PlatformPatterns.psiElement(PhpClass.class)
        );
    }
}
