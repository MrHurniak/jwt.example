CREATE TABLE hall
(
  id      BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  address VARCHAR(255)
);

CREATE TABLE exposition
(
  id      BIGINT(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(225)        NOT NULL,
  price   VARCHAR(45),
  begin   DATE                NOT NULL,
  end     DATE                NOT NULL,
  hall_id BIGINT(20) UNSIGNED NOT NULL,
  FOREIGN KEY (hall_id) REFERENCES hall (id),
  CONSTRAINT `fk_hall_id`
    FOREIGN KEY (`hall_id`)
      REFERENCES `hall` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);



