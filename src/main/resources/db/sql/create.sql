CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL AUTO_INCREMENT,
    login VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS good;
CREATE TABLE IF NOT EXISTS good (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id INT NOT NULL AUTO_INCREMENT,
    user_id    INT NOT NULL,
    total_price DECIMAL,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);






