package base.lang;

import com.cooker.zoom.helper.utils.base.prop.AutoProperties;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yu.kequn on 2018-06-29.
 */
public class AutoPropertiesTest {
    @Test
    public void loadProperties() throws IOException, InterruptedException {
        AutoProperties prop = AutoProperties.build("1.properties");
        System.out.println(prop.getString("sa").get());
        Thread.sleep(15000L);
        System.out.println(prop.getString("sa").get());
    }
}
