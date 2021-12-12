package ru.sfedu.movie.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static ru.sfedu.movie.Constant.ENV_PROP_VALUE;
import static ru.sfedu.movie.Constant.ENV_PROP_KEY;

public class ConfigurationUtil {
    private static final String CONFIG_PATH = System.getProperty(ENV_PROP_KEY, ENV_PROP_VALUE);

    private static final Properties configuration = new Properties();
    /**
     * Hides default constructor
     */
    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }



    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException {
        File nf = new File(CONFIG_PATH);
        System.out.println(nf);
        try (InputStream in = new FileInputStream(nf) // DEFAULT_CONFIG_PATH.getClass().getResourceAsStream(DEFAULT_CONFIG_PATH);
        ) {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }
}
