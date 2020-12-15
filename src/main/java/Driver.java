import com.example.model.UserManager;
import com.example.model.UserManagerFactory;

public class Driver {

    public static void main(String[] args)
    {
        UserManager manager = UserManagerFactory.getInstance().create();
        System.out.println(manager.getClass());

//        Util u = new Uitl();



    }
}
