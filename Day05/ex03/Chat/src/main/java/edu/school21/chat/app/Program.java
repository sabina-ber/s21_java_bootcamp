package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.*;
import edu.school21.chat.repositories.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    private static final String EXIT_COMMAND = "exit";
    private static final String MESSAGE_ID_PROMPT = "Enter a message ID: ";

    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();

        try (Connection connection = dataSource.getConnection()) {
            updateData("schema.sql", connection);
            updateData("data.sql", connection);
        } catch (SQLException e) {
            System.err.println("Error updating database: " + e.getMessage());
            e.printStackTrace();
        }
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> ifExistMessage = repository.findById(2L);
        User sabina = new User(145L, "sab", "er2", new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
        if (ifExistMessage.isPresent()) {
            Message msg = ifExistMessage.get();
            msg.setText("hello");
            msg.setDateTime(null);
            msg.setAuthor(sabina);
            repository.update(msg);
            System.out.println(msg);
            System.out.println(msg.getId());
        }
        dataSource.close();
    }

    private static void updateData(String filePath, Connection connection) {
        try {
            Statement statement = connection.createStatement();

            InputStream inputStream = Program.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String[] queries = sql.split(";");
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    statement.addBatch(query);
                }
            }
            statement.executeBatch();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleUserInput(JdbcDataSource dataSource) {
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(MESSAGE_ID_PROMPT);

            try {
                String input = scanner.nextLine().trim();

                if (EXIT_COMMAND.equals(input)) {
                    scanner.close();
                    System.exit(0);
                }

                Long id = Long.parseLong(input);
                Optional<Message> message = repository.findById(id);

                if (message.isPresent()) {
                    System.out.println(formatMessage(message.get()));
                } else {
                    System.out.println("Message not found.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid message ID.");
            } catch (Exception e) {
                System.err.println("Error retrieving message: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static String formatMessage(Message message) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Message : {\n");
        sb.append("  id=").append(message.getId()).append(",\n");
        sb.append("  author={id=").append(message.getAuthor().getId()).append(",");
        sb.append("login=\"").append(message.getAuthor().getLogin()).append("\",");
        sb.append("password=\"").append(message.getAuthor().getPassword()).append("\",");
        sb.append("createdRooms=null,rooms=null},\n");
        sb.append("  room={id=").append(message.getRoom().getId()).append(",");
        sb.append("name=\"").append(message.getRoom().getName()).append("\",");
        sb.append("creator=null,messages=null},\n");
        sb.append("  text=\"").append(message.getText()).append("\",\n");
        sb.append("  dateTime=").append(sdf.format(message.getDateTime())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}