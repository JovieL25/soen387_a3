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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO: Add a session object or cookie to keep track login user.

        String download = request.getParameter("download-file");
        if (download != null)
            downloadFile(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("signup") != null) {
            request.setAttribute("signupError","1");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        String login = request.getParameter("login");
        if (login != null)
            login(request, response);

        String search = request.getParameter("search");
        if (search != null) {

        }

        String post = request.getParameter("create-post");
        if (post != null)
            createPost(request, response);

        String logout = request.getParameter("logout");
        if (logout != null)
            logout(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email    = request.getParameter("login-email");
        String password = request.getParameter("login-password");

        File usersFile = new File(getServletContext().getRealPath("/") + "users.xml");

        User user = Manager.authenticate(email, password, usersFile);
        if (user != null) {
            request.getSession().setAttribute("user", user);

            request.setAttribute("posts", Manager.getAllPost());

            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
        else
            request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("user", null);

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("create-post-title");
        String text  = request.getParameter("create-post-text");

        User user = (User)request.getSession().getAttribute("user");

        int userId = Integer.parseInt(user.getUserId());

        Post post = new Post(userId, title, text);

        Manager.createPost(post);

        int postId = post.getPostId();

        Part part = request.getPart("file");

        if (part.getSize() > 0)
            Manager.insertFile(part, postId);

        request.setAttribute("posts", Manager.getAllPost());

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String format = request.getParameter("download-file-post-id");

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
}