package info.leftpi;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import info.leftpi.annotation.BindView;

import static javax.lang.model.element.Modifier.*;

/**
 * Created by zhaochunyu on 16/12/2.
 */

@AutoService(Processor.class)
public class InjectProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(BindView.class);


        for ( Element element:set) {

            Element typeElement= element.getEnclosingElement();
//            for (Element el:list1) {
//                System.out.printf(el.getSimpleName()+"--1\n\r");
//            }
            System.out.printf(typeElement.getSimpleName()+"--1\n\r");
            System.out.printf(element.getSimpleName()+"--2\n\r");

            MethodSpec main=MethodSpec.methodBuilder("bind")
                    .addStatement("activity.$L= ($T) activity.findViewById($L);", element,element.asType(), element.getAnnotation(BindView.class).value())
                    .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(ClassName.get(typeElement.asType()), "activity")
                    .build();



            TypeSpec helloWorld =
                    TypeSpec
                            .classBuilder(typeElement.getSimpleName()+"_Bind")
                            .addMethod(main)
                            .superclass(TypeName.get(typeElement.asType()))
                            .build();


            JavaFile javaFile = JavaFile.builder("info.leftpi.aptviewinject", helloWorld).build();


           try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }


        }
        System.out.printf("你好\n\r");

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
