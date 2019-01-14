package model.connectivity;
import java.sql.*;

public class JDBCConnectivityModel {
    private String JDBC_DRIVER;
    private String mysql_url;
    private String db_url;
    private Connection conn;
    private String user;
    private String pass;

    public JDBCConnectivityModel(String user, String pass) throws SQLException, ClassNotFoundException {
        JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        mysql_url = "jdbc:mysql://localhost/";
        db_url = "jdbc:mysql://localhost/ankiety";
        conn = null;
        this.user = user;
        this.pass = pass;

        System.out.println("------------------------------------");
        this.connectToMySQL();
        boolean exists = this.checkIfDataBaseExists();
        if(!exists){
            System.out.println("Database is creating...");
            this.createDatabaseWithData();
        }
        else{
            System.out.println("Database exists...");
        }
        this.close();
        this.connectToDataBase();
        if(!exists){
            System.out.println("Tables are creating...");
            this.createTables();
        }
        System.out.println("------------------------------------");
    }

    private void connectToMySQL() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(this.mysql_url, this.user, this.pass);
        System.out.println("Connected mysql successfully...");
    }

    private boolean checkIfDataBaseExists(){
        ResultSet resultSet = null;
        String db_name;
        try {
            resultSet = this.conn.getMetaData().getCatalogs();
            while (resultSet.next()) {
                db_name = resultSet.getString(1);
                if(db_name.equals("ankiety")){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createDatabaseWithData(){
        try {
            Statement statement = this.conn.createStatement();
            //database
            statement.addBatch("CREATE DATABASE IF NOT EXISTS  `ankiety` DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void connectToDataBase(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(this.db_url, this.user, this.pass);
            System.out.println("Connected database ankiety successfully...");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables(){
        try {
            Statement statement = this.conn.createStatement();
            //tables
            statement.addBatch("CREATE TABLE `completedSurvey` (\n" +
                    "  `id` int(255) NOT NULL,\n" +
                    "  `surveyId` int(255) NOT NULL,\n" +
                    "  `userLogin` varchar(255) NOT NULL,\n" +
                    "  `date` date NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            statement.addBatch("CREATE TABLE `question` (\n" +
                    "  `id` int(255) NOT NULL,\n" +
                    "  `surveyId` int(255) NOT NULL,\n" +
                    "  `content` varchar(255) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            statement.addBatch("CREATE TABLE `survey` (\n" +
                    "  `id` int(255) NOT NULL,\n" +
                    "  `name` varchar(255) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            statement.addBatch("CREATE TABLE `user` (\n" +
                    "  `login` varchar(255) NOT NULL,\n" +
                    "  `password` varchar(255) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            statement.addBatch("CREATE TABLE `answer` (\n" +
                    "  `id` int(255) NOT NULL,\n" +
                    "  `questionId` int(255) NOT NULL,\n" +
                    "  `content` varchar(255) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            //primary key
            statement.addBatch("ALTER TABLE `answer`\n" +
                    "  ADD PRIMARY KEY (`id`);");
            statement.addBatch("ALTER TABLE `completedSurvey`\n" +
                    "  ADD PRIMARY KEY (`id`);");
            statement.addBatch("ALTER TABLE `question`\n" +
                    "  ADD PRIMARY KEY (`id`);");
            statement.addBatch("ALTER TABLE `survey`\n" +
                    "  ADD PRIMARY KEY (`id`);");
            statement.addBatch("ALTER TABLE `user`\n" +
                    "  ADD PRIMARY KEY (`login`);");
            //auto increment
            statement.addBatch("ALTER TABLE `answer`\n" +
                    "  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;");
            statement.addBatch("ALTER TABLE `completedSurvey`\n" +
                    "  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;");
            statement.addBatch("ALTER TABLE `question`\n" +
                    "  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;");
            statement.addBatch("ALTER TABLE `survey`\n" +
                    "  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }

    public boolean close() {
        try {
            if(conn!=null) {
                conn.close();
                return true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}