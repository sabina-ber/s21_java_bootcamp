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
        try {
            User sabina = new User(9L, "sabina", "123", new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
            User sabina1 = new User(13L, "sabina", "123", new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
            Chatroom room = new Chatroom(9L, "goodRoom", sabina, new ArrayList<Message>());
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            Message message = new Message(null, sabina, room, "Wow! new message!", timestamp);
            Message message1 = new Message(null, sabina1, room, "Wow! new message!", timestamp);
            MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);
            repository.save(message);
            System.out.println("Message saved with ID: " + message.getId());
            repository.save(message1);
        } catch (NotSavedSubEntityException e) {
            System.err.println("Error updating database: " + e.getMessage());
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