
DROP TABLE IF EXISTS test;

CREATE TABLE test (
id INT AUTO_INCREMENT PRIMARY KEY,
username varchar(64),
password varchar(64)

);

INSERT INTO test (username, password) VALUES
('username', 'password'), ('anothername', 'wordpass');