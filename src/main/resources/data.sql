insert into user (id, name, email, date) values
(1, 'user1', 'user1@gmail.com', DATEADD('DAY',-7, NOW())),
(2, 'user2', 'user2@gmail.com', DATEADD('DAY',-5, NOW())),
(3, 'user3', 'user3@gmail.com', DATEADD('DAY',-2, NOW()));

insert into document (id, date, file, name, words) values
(1, DATEADD('DAY',-3, NOW()), FILE_READ('classpath:static/sample1.txt'), 'sample1.txt', 45),
(2, DATEADD('DAY',-3, NOW()), FILE_READ('classpath:static/sample2.txt'), 'sample2.txt', 117),
(3, DATEADD('DAY',-1, NOW()), FILE_READ('classpath:static/sample3.txt'), 'sample3.txt', 69);

insert into document_user (user_id, document_id) values
(1, 1),
(1, 2),
(2, 3);

insert into team values
(1, 'team1'),
(2, 'team2');

insert into user_team (user_id, team_id) values
(1, 1),
(1, 2),
(2, 2),
(3, 1);

commit;