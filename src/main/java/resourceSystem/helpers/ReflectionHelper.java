package resourceSystem.helpers;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Created by stalker on 31.01.16.
 */
public class ReflectionHelper {
    @Nullable
    public static Object createInstance(String className){
        try {
            return Class.forName(className).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void setFieldValue(Object object, String fieldName, String value){
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if(field.getType().equals(String.class)){
                field.set(object, value);
            }
            if(field.getType().equals(int.class)){
                field.set(object, Integer.decode(value));
            }
        } catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

}
