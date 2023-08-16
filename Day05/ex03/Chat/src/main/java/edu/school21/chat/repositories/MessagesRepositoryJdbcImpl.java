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
    @Override
    public void update(Message message) {
        String query = "UPDATE chat.message SET author_id = ?, room_id = ?, text = ?, date_time = ? WHERE id = ?";
        String checkUserExists = "SELECT 1 FROM chat.User WHERE id = ?";
        String checkRoomExists = "SELECT 1 FROM chat.Chatroom WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {

            if (message.getAuthor() != null) {
                try (PreparedStatement checkStatement = connection.prepareStatement(checkUserExists)) {
                    checkStatement.setLong(1, message.getAuthor().getId());
                    ResultSet resultSet = checkStatement.executeQuery();
                    if (!resultSet.next()) {
                        System.out.println("No user found with ID: " + message.getAuthor().getId());
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return;
                }
            }

            if (message.getRoom() != null) {
                try (PreparedStatement checkStatement = connection.prepareStatement(checkRoomExists)) {
                    checkStatement.setLong(1, message.getRoom().getId());
                    ResultSet resultSet = checkStatement.executeQuery();
                    if (!resultSet.next()) {
                        System.out.println("No room found with ID: " + message.getRoom().getId());
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return;
                }
            }

            // Если author_id и room_id существуют, выполняем обновление
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (message.getAuthor() != null) {
                    statement.setLong(1, message.getAuthor().getId());
                } else {
                    statement.setNull(1, Types.BIGINT);
                }

                if (message.getRoom() != null) {
                    statement.setLong(2, message.getRoom().getId());
                } else {
                    statement.setNull(2, Types.BIGINT);
                }

                if (message.getText() != null) {
                    statement.setString(3, message.getText());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }

                if (message.getDateTime() != null) {
                    statement.setTimestamp(4, message.getDateTime());
                } else {
                    statement.setNull(4, Types.TIMESTAMP);
                }

                statement.setLong(5, message.getId());

                int updatedRows = statement.executeUpdate();
                if (updatedRows == 0) {
                    System.out.println("No message found with ID: " + message.getId());
                } else {
                    System.out.println("Message updated successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
