package Utils;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XMLFile {

    public static void main(String[] args){


        String passwordToHash_1 = "12345";
        String passwordToHash_2 = "12345";

        String generatedPassword_1 = hashPassword(passwordToHash_1);
        String generatedPassword_2 = hashPassword(passwordToHash_2);


        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<users>\n" +
                "\t<user id = \"1\">\n" +
                "\t\t<name>Kaixin Dai</name>\n" +
                "\t\t<email>kaixindai@gmail.com</email>\n" +
                "\t\t<password>"+generatedPassword_1+"</password>\n" +
                "\t</user>\n" +
                "\t<user id = \"2\">\n" +
                "\t\t<name>Tianming Chen</name>\n" +
                "\t\t<email>tianmingchen@gmail.com</email>\n" +
                "\t\t<password>"+generatedPassword_2+"</password>\n" +
                "\t</user>\n" +
                "</users>";

        try {
            FileWriter myWriter = new FileWriter("user.xml");
            myWriter.write(xmlContent);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File file = new File("user.xml");

        // check if the file exists
        boolean exists = file.exists();
        file.setWritable(false);
        if(exists == true)
        {
            // printing the permissions associated with the file
            System.out.println("Executable: " + file.canExecute());
            System.out.println("Readable: " + file.canRead());
            System.out.println("Writable: "+ file.canWrite());
        }
        else
        {
            System.out.println("File not found.");
        }

    }


    public static String hashPassword(String passwordToHash){
        String generatedPassword = null;

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);

        return generatedPassword;
    }
}
