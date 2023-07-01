CREATE TABLE IF NOT EXISTS users(
    email    VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  INT      NOT NULL DEFAULT 1,
    PRIMARY KEY (email)
);
CREATE TABLE IF NOT EXISTS authorities(
    email     VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (email, authority),
    FOREIGN KEY (email) REFERENCES users (email)
);

INSERT INTO users (email, password, enabled)
VALUES ('admin@mail.ru', '$2y$10$gKJsPvguAvNLYxOozcPAOu3C/uIS4xpq064Cw/faAlcd2/PfbioPa', 1);
INSERT INTO authorities (email, authority)
VALUES ('admin@mail.ru', 'ADMIN');

INSERT INTO users (email, password, enabled)
VALUES ('user@mail.ru', '$2y$10$gKJsPvguAvNLYxOozcPAOu3C/uIS4xpq064Cw/faAlcd2/PfbioPa', 1);
INSERT INTO authorities (email, authority)
VALUES ('user@mail.ru', 'USER');