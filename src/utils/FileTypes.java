package utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface FileTypes {
    int FILE = 0;
    int FOLDER = 1;
    int JAR = 2;
    int JAR_AS_FILE = 3;
    int JAR_CONTENT = 4;
}
