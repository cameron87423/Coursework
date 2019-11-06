package Server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {//program in the rest of the selection menu and function.

    public static Connection db = null;// behaves as a global variable
    //this is the main method
    /*public static void main(String[] args) {
        openDatabase("courseworkDB.db"); // code to get data from, write to the database etc goes here...
        closeDatabase();
    }*/

    public static void main(String[] args) {

        openDatabase("courseworkDB.db");

        ResourceConfig config = new ResourceConfig(); //prepare jetty servlet
        config.packages("Controllers"); //to use the handlers in the controllers' package
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081); //prepare jetty server to listen to port 8081
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) { //error catching if the server fails to start
            e.printStackTrace();
        }
    }

    //this opens the database
    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC"); // this loads the database driver
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true); // needed in order to change the original database file
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) { // throws an exception if the database can't connect to the code
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }
    // this closes the database
    private static void closeDatabase(){
        try {
            db.close(); // closes the database
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }
}

