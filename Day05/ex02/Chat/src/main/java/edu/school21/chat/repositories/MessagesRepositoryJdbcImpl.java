package edu.school21.chat.repositories;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final JdbcDataSource dataSource;

    public MessagesRepositoryJdbcImpl(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM chat.message WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long authorId = resultSet.getLong("author_id");
                Long roomId = resultSet.getLong("room_id");
                User user = findUserById(authorId);
                Chatroom chatroom = findChatroomById(roomId);
                return Optional.of(new Message(
                        resultSet.getLong("id"),
                        user,
                        chatroom,
                        resultSet.getString("text"),
                        resultSet.getTimestamp("date_time")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save (Message message) {
        String sqlQuery = "INSERT INTO chat.Message (author_id, room_id, text, date_time) VALUES (?, ?, ?, ?)";
        if (message.getAuthor().getId() == null || message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Author or room has no valid ID");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime().toLocalDateTime()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save message");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Failed to retrieve message ID");
                }
            }

        } catch (SQLException e) {
            throw new NotSavedSubEntityException("Failed to save message", e);
        }
    }



    private User findUserById(Long id) {
        String query = "SELECT * FROM chat.user WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        null,
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    private Chatroom findChatroomById(Long id) {
        String query = "SELECT * FROM chat.chatroom WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Chatroom(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        null,
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
