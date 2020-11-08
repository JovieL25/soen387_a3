import java.util.ArrayList;
import java.util.Date;


public class ChatManager {

    public static ArrayList<Message>messages = new ArrayList<Message>();
    //todo anonymous user
    public static String PostMessage(String user, String message){

        if (user.equals("")) {
            user = "anonymous";
        }


        Message m = new Message(user,message);

        messages.add(m);

        return "User: " + m.user+" Message: " + m.text + " "+ m.date;
    }

    //overloading PostMessage for custom public time
    public static String PostMessage(String user, String message, String date){

        Message m = new Message(user, message, date);

        messages.add(m);

        return "User: " + m.user+" Message: " + m.text + " "+ m.date;
    }



    public static ArrayList<Message> ListMessages(String from, String to){
        ArrayList<Message>found_messages = new ArrayList<Message>();

        Date start = Message.parseDate(from);
        Date end = Message.parseDate(to);

        for (int i = 0; i < messages.size(); i++) {

            if(messages.get(i).date.after(start) && messages.get(i).date.before(end)){

                found_messages.add(messages.get(i));

            }
        }



        return found_messages;
    }


    //overloading ListMessages with no date_rage specified
    public static ArrayList<Message> ListMessages(){
        return messages;
    }

    public static void ClearChat(String from, String to){
        ArrayList<Message>found_messages = new ArrayList<Message>();

        Date start = Message.parseDate(from);
        Date end = Message.parseDate(to);

        for (int i = 0; i < messages.size(); i++) {

           if(messages.get(i).date.after(start) && messages.get(i).date.before(end)){

            found_messages.add(messages.get(i));

           }
        }
        messages.removeAll(found_messages);
    }

    // overloading ClearChat with no date_rage specified
    public static void ClearChat(){
        messages.removeAll(messages);
    }




    public static void main(String[] args) {
        System.out.println(ChatManager.PostMessage("1", "text1"));
        System.out.println(ChatManager.PostMessage("2", "text2"));
        System.out.println(ChatManager.PostMessage("3", "text3" ));
        System.out.println(ChatManager.PostMessage("", "text4"));
        System.out.println(ChatManager.PostMessage("5", "text5","2015-12-14"));
        System.out.println(ChatManager.PostMessage("6", "text6","2016-12-14"));

        String before1 = "2000-01-01";
        String after1 = "2021-01-01";
//        ArrayList<Message> a = ListMessages(before1,after1);

        ArrayList<Message> a = ListMessages();

        for (int i = 0; i < a.size() ; i++){
            System.out.println(a.get(i));
        }

        String before2 = "2019-01-01";
        String after2 = "2021-01-01";
        ClearChat(before2,after2);



        ArrayList<Message> b = ListMessages();

        for (int i = 0; i < b.size() ; i++){
            System.out.println(b.get(i));
        }

        System.out.println("deleting");
        ClearChat();

        ArrayList<Message> c = ListMessages();

        for (int i = 0; i < c.size() ; i++){
            System.out.println(c.get(i));
        }
        System.out.println("completed");










    }
}
