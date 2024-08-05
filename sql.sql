CREATE DATABASE ttt;
use ttt;

CREATE TABLE member
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    username          VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    introduction      VARCHAR(255) DEFAULT '한 줄 소개입니다. 자신을 멋있게 소개해보세요!',
    created_at        TIMESTAMP    NOT NULL,
    profile_image_url VARCHAR(255),
    total_likes       INT          DEFAULT 0,
    total_views       INT          DEFAULT 0,
    UNIQUE (username),
    UNIQUE (email)
);


CREATE TABLE post
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    member_id  INT,
    title      TEXT      NOT NULL,
    content    LONGTEXT  NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
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


CREATE TABLE search_history
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    member_id   INT          NOT NULL,
    query       VARCHAR(255) NOT NULL,
    searched_at TIMESTAMP    NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE scrap
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    member_id  INT NOT NULL,
    post_id    INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (post_id) REFERENCES post (id),
    UNIQUE (member_id, post_id) -- 사용자가 같은 게시글을 여러 번 스크랩할 수 없도록
);

