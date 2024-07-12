CREATE TABLE member
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    username          VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    created_at        DATE         NOT NULL,
    profile_image_url VARCHAR(255),
    total_likes       INT DEFAULT 0,
    total_views       INT DEFAULT 0
);


CREATE TABLE post
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    member_id  INT,
    title      TEXT      NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);


CREATE TABLE content
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT          NOT NULL,
    type    VARCHAR(255) NOT NULL,
    `index` INT          NOT NULL,
    data    TEXT         NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id)
);


CREATE TABLE location
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    post_id   INT            NOT NULL,
    latitude  DECIMAL(10, 7) NOT NULL,
    longitude DECIMAL(10, 7) NOT NULL,
    name      VARCHAR(255)   NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id)
);


CREATE TABLE comment
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    post_id    INT       NOT NULL,
    member_id  INT       NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);


CREATE TABLE likes
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    post_id   INT NOT NULL,
    member_id INT NOT NULL,
    UNIQUE (post_id, member_id),
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);


CREATE TABLE views
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    post_id   INT NOT NULL,
    member_id INT,
    FOREIGN KEY (post_id) REFERENCES post (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);


CREATE TABLE interests
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT          NOT NULL,
    interest  VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE search_history
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT          NOT NULL,
    query     VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);


