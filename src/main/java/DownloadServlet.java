import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.example.model.Manager;
import javax.activation.MimetypesFileTypeMap;

import com.example.model.Post;
import com.example.model.User;
import org.apache.commons.io.FileUtils;


@MultipartConfig
@WebServlet(name = "DownloadServlet")
public class DownloadServlet extends HttpServlet {
    private static final MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
    private static ArrayList<User> Users_list;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("Referer");
        if (referer == null) {
            HttpSession session = request.getSession();

            session.setAttribute("referer-error", "Referer is not present.");

            response.sendRedirect(request.getContextPath());

            return;
        }
        //TODO: Add a session object or cookie to keep track login user.
        String download = request.getParameter("download");
        if (download != null)
            downloadMessages(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("Referer");
        if (referer == null) {
            request.getSession().setAttribute("referer-error", "Referer is not present.");
            response.sendRedirect(request.getContextPath());
            return;
        }
        if(request.getParameter("signup")!=null){
            request.setAttribute("signupError","1");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        String login = request.getParameter("login");
        if (login != null)
            login(request, response);

        String search = request.getParameter("search");
        if (search!=null) {

        }
        String post = request.getParameter("post");
        if (post != null)
            postMessage(request, response);


        String clear = request.getParameter("clear");
        if (clear != null)
            clearMessages(request, response);

        String refresh = request.getParameter("refresh");
        if (refresh != null)
            refresh(request, response);

        String switch_theme = request.getParameter("switch-theme");
        if (switch_theme != null)
            switchTheme(request, response);

        if (request.getParameter("Logout")!=null)
            request.getRequestDispatcher("login.jsp").forward(request, response);

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email    = request.getParameter("email");
        String password = request.getParameter("password");

        File usersFile = new File(getServletContext().getRealPath("/") + "users.xml");

        boolean isValid = Manager.authenticate(email, password, usersFile);
        if (isValid){
            //request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            refresh(request,response);
        }
        else
            request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void downloadMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from   = request.getParameter("download-from");
        String to     = request.getParameter("download-to");
        String format = request.getParameter("postid");

        int postId = Integer.parseInt(format);

        File file = Manager.selectFile(postId);

        String contentType = mimetypesFileTypeMap.getContentType(file);
        response.setContentType(contentType);

        String fileName = file.getName();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        OutputStream outputStream = response.getOutputStream();
        outputStream.write(FileUtils.readFileToByteArray(file));
        outputStream.flush();
        outputStream.close();
    }


    private void postMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title    = request.getParameter("title");
        String message = request.getParameter("posttext");

        int postId = Integer.parseInt("1");

        Post post = new Post();
        post.setPostId(postId);
        post.setUserId(1);
        post.setText(message);

        Manager.createPost(post);

        Part part = request.getPart("file");

        Manager.insertFile(part, postId);

        request.setAttribute("posts", Manager.getAllPost());

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    private void clearMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("clear-from");
        String to   = request.getParameter("clear-to");

        if (!from.equals("") && !to.equals(""))
            ChatManager.ClearChat(from, to);
        else
            ChatManager.ClearChat();

        request.setAttribute("messages", ChatManager.ListMessages());

        request.getRequestDispatcher("/").forward(request, response);
    }

    private void refresh(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("posts", Manager.getAllPost());
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    private void switchTheme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String theme = (String)session.getAttribute("theme");
        if (theme == null || theme.equals("blue"))
            session.setAttribute("theme", "green");
        else
            session.setAttribute("theme", "blue");

        request.setAttribute("messages", ChatManager.ListMessages());

        request.getRequestDispatcher("/").forward(request,response);
    }
}