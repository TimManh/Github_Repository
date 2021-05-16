
-----------------------------------------------
--180 B Github Project(Team 2) DML Scripts
-----------------------------------------------

INSERT user VALUES
(1,'cndn56','Rajesh','rajesh@gmail.com','San Jose','California','US'),
(2,'vdfv11','Thin','Thin@gmail.com','Atlanta','Georgia','US'),
(3,'vdsv51','Nithin','nithin@gmail.com','Phoenix','Arizona','US'),
(4,'cds511','Devanshi','devanshi@gmail.com','Los Angeles','California','US'),
(5,'dscd15','siddarth','siddarth@gmail.com','Dallas','Texas','US'),
(6,'vfd511','keerthi','keerthi@gmail.com','Des Moines','Iowa','US'),
(7,'vfds51','akhil','akhil@gmail.com','Santa Clara','California','US'),
(8,'cwfvd0','sanith','sanith@gmail.com','Tampa','Florida','US');

INSERT Followers VALUES
(1,2),
(2,3),
(3,4),
(1,3),
(1,4),
(1,5),
(1,7),
(1,8);

INSERT respository VALUES
(1,'www.github.com/rajesh/projet','180B','It contains project files of 180 B course','2018-11-14',1),
(2,'www.github.com/thin/projet','180B','It contains project files of 180 B course','2017-10-14',2),
(3,'www.github.com/nithin/projet','180B','It contains project files of 180 B course','2016-12-14',3),
(4,'www.github.com/devanshi/projet','180B','It contains project files of 180 B course','2015-11-14',4),
(5,'www.github.com/siddarth/projet','180B','It contains project files of 180 B course','2014-11-14',5);


INSERT branches VALUES
(1,1),
(1,2),
(1,3),
(2,4),
(2,5),
(2,6);

INSERT project_files VALUES
(1,1,1),
(1,1,2),
(1,1,3),
(1,2,4),
(1,2,5),
(1,2,6);

INSERT project_details VALUES
(1,'alpha','2018-11-23'),
(2,'beeta','2017-12-19'),
(3,'theeta','2016-10-16');

INSERT commits VALUES
(1,1,1),
(2,2,2),
(3,3,3),
(4,4,1),
(5,5,2),
(1,6,3);

INSERT pull_requests VALUES
(1,1,1),
(2,2,2),
(3,3,3),
(4,4,1),
(5,5,2),
(1,6,3);


INSERT Makes VALUES
(1,1,1,1),
(2,2,2,2),
(3,3,3,3),
(4,4,1,4),
(5,5,2,5),
(6,1,3,6),
(7,2,1,1),
(8,3,2,2);

INSERT commit_details VALUES
(1,'version 1.0','2018-11-18'),
(2,'version 1.1','2018-11-19'),
(3,'version 1.2','2018-11-20'),
(4,'version 1.3','2018-11-21'),
(5,'version 1.4','2018-11-22'),
(6,'version 1.5','2018-11-23');

INSERT requests VALUES
(1,1,1,1),
(2,2,2,2),
(3,3,3,3),
(4,4,1,4),
(5,5,2,5),
(6,1,3,6),
(7,2,1,1),
(8,3,2,2);


INSERT pullreq_details VALUES
(1,'2018-11-10'),
(2,'2018-11-11'),
(3,'2018-11-12'),
(4,'2018-11-13'),
(5,'2018-11-14'),
(6,'2018-11-15');
