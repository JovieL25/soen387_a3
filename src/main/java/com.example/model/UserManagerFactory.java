package com.example.model;
import java.io.FileReader;
import java.util.Properties;

public class UserManagerFactory {
    private static UserManagerFactory firstInstance = null;

    private UserManagerFactory(){}

    public static UserManagerFactory getInstance()
    {
        if(firstInstance == null)
            firstInstance = new UserManagerFactory();

        return firstInstance;
    }

    public UserManager create()
    {
//        getServletContext().getRealPath("/") + "users.xml"
        try (FileReader reader = new FileReader("config")){
            Properties properties = new Properties();
            properties.load(reader);

            String className = properties.getProperty("classname");
            System.out.println(className);
            return (UserManager)Class.forName("com.example.impl." +className).newInstance();
//            return null;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
