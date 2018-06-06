package pkg;

import com.cooker.zoom.helper.utils.base.EIOUtils;
import com.cooker.zoom.helper.utils.convert.JacksonUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.ClassPath;
import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by yu.kequn on 2018-05-25.
 */
public class LoaderTest {
    static final String DEFAULT_RESOURCE_PACKAGE = EIOUtils.class.getPackage().getName();
    private static final String DEFAULT_RESOURCE_PREFIX;
    static {
        DEFAULT_RESOURCE_PREFIX = DEFAULT_RESOURCE_PACKAGE.replace('.', '/') + "/";
    };

    static final String DEFAULT_RESOURCE_SUFFIX = ".txt";

    public static void main(String[] args) throws IOException {
        ImmutableMap<String, URL> resources =  getLiveResources();
        System.out.println(JacksonUtils.toJSON(resources));
    }

    private static ImmutableMap<String, URL> getLiveResources() throws IOException {
        ClassLoader classLoader = LoaderTest.class.getClassLoader();
        ClassPath classPath = ClassPath.from(classLoader);
        ImmutableMap.Builder<String, URL> resources = new ImmutableMap.Builder<String, URL>();
        for (ClassPath.ResourceInfo resourceInfo : classPath.getResources()) {
            String name = resourceInfo.getResourceName();
            // Find relevant resource files.
            if (name.startsWith(DEFAULT_RESOURCE_PREFIX) && name.endsWith(DEFAULT_RESOURCE_SUFFIX)) {
                String className =
                        name.substring(
                                DEFAULT_RESOURCE_PREFIX.length(),
                                name.length() - DEFAULT_RESOURCE_SUFFIX.length());
                // Include only those resources for live classes.
                if (classExists(classLoader, className)) {
                    resources.put(className, resourceInfo.url());
                }
            }
        }
        return resources.build();
    }

    private static boolean classExists(ClassLoader cl, String className) {
        try {
            cl.loadClass(className);
            return true;
        } catch (ClassNotFoundException e) {
            // Expected for classes removed during incremental compilation.
            return false;
        }
    }
}
