INSERT INTO chat.User (login, password) VALUES
                                            ('user1', 'password1'),
                                            ('user2', 'password2'),
                                            ('user3', 'password3'),
                                            ('user4', 'password4'),
                                            ('user5', 'password5'),
                                            ('user6', 'password6'),
                                            ('user7', 'password7'),
                                            ('user8', 'password8'),
                                            ('user9', 'password9'),
                                            ('user10', 'password10');


INSERT INTO chat.Chatroom (name, owner_id) VALUES
                                               ('Room 1', 1),
                                               ('Room 2', 2),
                                               ('Room 3', 3),
                                               ('Room 4', 1),
                                               ('Room 5', 2),
                                               ('Room 6', 3),
                                               ('Room 7', 1),
                                               ('Room 8', 2),
                                               ('Room 9', 3),
                                               ('Room 10', 1);


INSERT INTO chat.Message (author_id, room_id, text, date_time) VALUES
                                                                   (1, 1, 'Hello from user 1', NOW()),
                                                                   (2, 1, 'Hello from user 2', NOW()),
                                                                   (3, 2, 'Hello from user 3', NOW()),
                                                                   (1, 2, 'Hello from user 1 again', NOW()),
                                                                   (2, 3, 'Hello from user 2 again', NOW()),
                                                                   (3, 3, 'Hello from user 3 again', NOW()),
                                                                   (1, 4, 'Hello from user 1 one more time', NOW()),
                                                                   (2, 4, 'Hello from user 2 one more time', NOW()),
                                                                   (3, 5, 'Hello from user 3 one more time', NOW()),
                                                                   (1, 5, 'Hello from user 1 once more', NOW());