package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by apple on 2017/11/3.
 */
public class PropertiesHelper {

    public static Map<String, String> properties = new HashMap<>();

    static {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("gskyline.properties"));
            prop.load(in);
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                properties.put(key, prop.getProperty(key));
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
