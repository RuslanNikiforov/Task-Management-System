
INSERT INTO users(id, email, name, password) VALUES (101, 'ex6@mail.ru', 'Sancho',
                                                     '$2a$10$92Hr/Ez47.8PE8Gy7zxC7u1A/pfy1B16jkN2w19nCASrTEYvavlVy'),
    (102, 'ex9@mail.ru', 'Alex', '$2a$10$JUyunPN4eTM6xtreeglRKekKXxB91tsrpcb/mSesfUi8EpigailFq'),
    (103, 'ex1@mail.ru', 'Anna', '$2a$10$qHz.U84P8J/CK1n0FrxGsOWtYJq35Ui4/qxOqglIc.6LQBHucyXJO'),
    (104, 'abc@mail.ru', 'Bob', '$2a$10$lRIA2G4oh5xh/pZgh4l6COmvgr/eXyKZ2Td8KoY0dfJYf/QkRsG66');

INSERT INTO tasks(author_id, id, description, header, priority, status) VALUES (101, 101, 'dscr1', 'header1', 'MEDIUM',
                                                                                'WAITING'),
        (101, 102, 'dscr2', 'header2', 'HIGH', 'IN_PROGRESS'),
        (102, 103, 'dscr3', 'header3', 'MEDIUM', 'WAITING'),
        (102, 104, 'dscr4', 'header4', 'LOW', 'FINISHED'),
        (104, 105, 'dscr5', 'header5', 'LOW', 'FINISHED');

INSERT INTO tasks_executors(executors_id, task_id) VALUES (102, 101), (102, 102), (103, 102), (104, 103), (101, 104);

INSERT INTO comments(id, task_id, user_id, time_sent, text) VALUES (31, 101, 101, now(), 'hello1!'),
                                                                   (32, 101, 102, now(), 'hello2!'),
                                                                   (33, 102, 101, now(), 'hello3!'),
                                                                   (34, 102, 103, now(), 'hello4!'),
                                                                   (35, 103, 104, now(), 'hello5!');




