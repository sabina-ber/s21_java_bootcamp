DROP SCHEMA IF EXISTS chat CASCADE;
CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE IF NOT EXISTS chat.User (
                                         id SERIAL PRIMARY KEY,
                                         login VARCHAR(255) NOT NULL,
                                         password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.Chatroom (
                                             id SERIAL PRIMARY KEY,
                                             name VARCHAR(255) NOT NULL,
                                             owner_id INT,
                                             FOREIGN KEY (owner_id) REFERENCES chat.User(id)
);

CREATE TABLE IF NOT EXISTS chat.Message (
                                            id SERIAL PRIMARY KEY,
                                            author_id INT,
                                            room_id INT,
                                            text TEXT,
                                            date_time TIMESTAMP,
                                            FOREIGN KEY (author_id) REFERENCES chat.User(id),
                                            FOREIGN KEY (room_id) REFERENCES chat.Chatroom(id)
);