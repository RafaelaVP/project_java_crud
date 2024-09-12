package br.com.syonet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.syonet.database.InitDb;
import br.com.syonet.database.StudentDao;
import br.com.syonet.model.StudentRepository;
import br.com.syonet.service.StudentService;
import br.com.syonet.view.StudentView;

public class Application {

    public static void main(String[] args) throws SQLException, IOException {
        try (Connection connection = ConnectionPool.getConnection();  Scanner scanner = new Scanner(System.in)) {
            new InitDb(connection).start();
            StudentRepository studantRepository = new StudentDao(connection);
            StudentService studantService = new StudentService(studantRepository);
            StudentView studantView = new StudentView(studantService, scanner);

            studantView.init();
            while (!studantView.isExit()) {
                studantView.showOptions();
                studantView.readSelectedOption();
                studantView.executeSelectedOperation();
            }
        }
    }

    class ConnectionPool {
        private static final String URL = "jdbc:postgresql://localhost:5432/java_crud";
        private static final String USER = "syonet";
        private static final String PASSWORD = "syonet";

        public static Connection getConnection() throws SQLException {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        }
    }
}
