package io.noties.android;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class AndroidProcessor extends AbstractProcessor {

    private static final String NAME = "io.noties.android.Android";

    private Filer filer;
    private Elements elements;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        elements = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(
                "android.support.annotation.NonNull",
                "android.support.annotation.Nullable",
                "androidx.annotation.NonNull",
                "androidx.annotation.Nullable"));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver()) {

            final TypeElement element = elements.getTypeElement(NAME);

            messager.printMessage(
                    Diagnostic.Kind.NOTE,
                    NAME + " exists: " + (element != null));

            if (element == null) {

                final String source;
                try {
                    // there seems to be a lot of hacks in android build system,
                    // most likely there are regexps to process (for whatever reason)
                    // *[Aa]ndroid.* file names... so, such file cannot be found in a jar...
                    //
                    // does not work:
                    // * _Android.java
                    // * Android.java
                    // * Android.java.link
                    // * Android.src
                    // * AndroidSource.java
                    // * src.android.java
                    source = IOUtils.resourceToString(
                            "/AndroidSymLink.java",
                            StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("read resource", e);
                }

                Writer writer = null;
                try {
                    final JavaFileObject javaFileObject = filer.createSourceFile(NAME);
                    writer = javaFileObject.openWriter();
                    writer.write(source);
                } catch (IOException e) {
                    throw new RuntimeException("write source", e);
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            // no op
                        }
                    }
                }
            }
        }

        return false;
    }
}
