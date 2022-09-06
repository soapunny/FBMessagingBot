package util;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.NoSuchElementException;

public class EnvHelper {
    private static EnvHelper instance = null;
    private final Dotenv dotenv = Dotenv.load();

    public static EnvHelper getInstance(){
        if(instance == null){
            instance = new EnvHelper();
        }
        return instance;
    }

    public String getValue(String key) throws NoSuchElementException{
        final String value = dotenv.get(key);
        if(value == null || value.equals(""))
            throw new NoSuchElementException(Util.getStackTrace(Thread.currentThread().getStackTrace()));

        return value;
    }
}
