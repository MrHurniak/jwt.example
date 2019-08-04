create table user
(
  id       BIGINT(20) auto_increment,
  login    varchar(100) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
create table user_role
(
  id    BIGINT(20) not null,
  roles varchar(255),
  UNIQUE KEY `unique_role` (`id`, `roles`),
  FOREIGN KEY (id) REFERENCES user (id)
);