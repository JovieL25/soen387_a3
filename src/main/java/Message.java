import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    public String user;
    public String text;
    public Date date;

    Message(String user, String text){
        this.user = user;
        this.text = text;
        date = new Date();
    }

    //format of data has to be yyyy-mm-dd
    Message(String user, String text, String date){
        this.user = user;
        this.text = text;
        this.date = parseDate(date);
    }

    public String toString() {
        return "Message{" +
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                "}\n";
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            System.out.println("Wrong format for Date, should be yyyy-MM-dd");
            return null;
        }
    }

    public String toXmlString() {
        return "<message>\n"
                + "    <user>" + user + "</user>\n"
                + "    <text>" + text + "</text>\n"
                + "    <date>" + date + "</date>\n"
                + "</message>\n"
                + "\n";
    }

    public String getText() {
        return text;
    }

    public String getUser(){
        return user;
    }

    public String getDateString() {
        return date.toString();
    }
}
