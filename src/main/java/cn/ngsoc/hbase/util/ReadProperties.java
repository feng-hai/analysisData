package cn.ngsoc.hbase.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 文件读取辅助�?
 */
public class ReadProperties {

    /**
     *
     * @param fileName
     *            文件�?
     * @param key
     *            key
     * @param defaultValue
     *            NULL的时候默认返回�??
     * @return
     */
    public static Object getPropertyFromConfiguration(String fileName, String key, Object defaultValue) {
        InputStream inputStream = ReadProperties.class.getClassLoader()
                .getResourceAsStream(fileName);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e) {

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }
        return p.getProperty(key) == null ? defaultValue : p.getProperty(key);
    }

    /**
     * TODO 读取配置文件的相关项
     *
     * @param fileName
     * @return
     */
    public static Properties loadConfiguration(String fileName) {
        InputStream inputStream = ReadProperties.class.getClassLoader().getResourceAsStream(fileName);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e) {
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        return p;
    }

}
