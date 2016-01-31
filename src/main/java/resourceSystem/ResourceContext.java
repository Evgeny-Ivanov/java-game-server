package resourceSystem;


import java.util.Map;

/**
 * Created by stalker on 31.01.16.
 */
public class ResourceContext {
    private static Map<Class<?>,Resource> context;
    private static ResourceContext instance = new ResourceContext();
    private static String resourcesDir = "data";

    public static ResourceContext getInstance(){
        return instance;
    }

    private ResourceContext(){
        context = ResourceFactory.loadResources(resourcesDir);
    }

    public Resource get(Class<?> clazz){
        return context.get(clazz);
    }

    public void add(Class<?> clazz, Resource resource){
        context.put(clazz, resource);
    }
}
