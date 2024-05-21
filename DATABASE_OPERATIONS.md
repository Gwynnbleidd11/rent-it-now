**ROUTINES AND EVENT**

**Creation of Top3Movies procedure:**

DROP PROCEDURE IF EXISTS Top3Movies;

DELIMITER $$

CREATE PROCEDURE Top3Movies(first INT, second INT, third INT)
BEGIN
UPDATE movies
SET top_level = CASE
WHEN movie_id = first THEN 'ONE'
WHEN movie_id = second THEN 'TWO'
WHEN movie_id = third THEN 'THREE'
ELSE 'OUT OF RANKING'
END;

END $$
DELIMITER ;

**Creation of UpdateTopLevels procedure:**

DROP PROCEDURE IF EXISTS UpdateTopLevels;

DELIMITER $$

CREATE PROCEDURE UpdateTopLevels()
BEGIN
DECLARE mov_id, mov1_id, mov2_id, mov3_id INT;
DECLARE finished INT DEFAULT 0;
DECLARE top_one INT DEFAULT 0;
DECLARE top_two INT DEFAULT 0;
DECLARE top_three INT DEFAULT 0;
DECLARE rents_in_last_week INT;
DECLARE all_movies CURSOR FOR SELECT movie_id FROM movies;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

    OPEN all_movies;

    WHILE (finished = 0) DO
        FETCH all_movies INTO mov_id;
        IF (finished = 0) THEN
            SELECT COUNT(*) FROM rents
                WHERE movie_id = mov_id
                    AND rent_date >= DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)
                        INTO rents_in_last_week;

            IF rents_in_last_week >= top_one THEN
                SET top_three = top_two;
                SET top_two = top_one;
                SET top_one = rents_in_last_week;
                SET mov1_id = mov_id;
            ELSEIF rents_in_last_week > top_two THEN
                SET top_three = top_two;
                SET top_two = rents_in_last_week;
                SET mov2_id = mov_id;
            ELSEIF rents_in_last_week > top_three THEN
                SET top_three = rents_in_last_week;
                SET mov3_id = mov_id;
            END IF;
        END IF;
    END WHILE;

    CALL Top3Movies(mov1_id, mov2_id, mov3_id);

    CLOSE all_movies;
END $$

DELIMITER ;

**Creation of top_movies event:**

CREATE EVENT top_movies
ON SCHEDULE EVERY 1 DAY
DO CALL UpdateTopLevels();


**USER AUDIT**

**Creation of user_audit table:**

CREATE TABLE user_audit (
event_id BIGINT NOT NULL AUTO_INCREMENT,
event_date DATETIME NOT NULL ,
event_type varchar(255) DEFAULT NULL,
user_id BIGINT NOT NULL,
old_email VARCHAR(255),
new_email VARCHAR(255),
old_password VARCHAR(255),
new_password VARCHAR(255),
old_firstname VARCHAR(255),
new_firstname VARCHAR(255),
old_lastname VARCHAR(255),
new_lastname VARCHAR(255),
old_phone_number VARCHAR(255),
new_phone_number VARCHAR(255),
old_birth_date DATE,
new_birth_date DATE,
PRIMARY KEY (event_id)
);

**Creation of user_insert trigger:**

DROP TRIGGER IF EXISTS user_insert;

DELIMITER $$

CREATE TRIGGER user_insert AFTER INSERT ON users
FOR EACH ROW
BEGIN
INSERT INTO user_audit (event_date, event_type, user_id, new_email, new_password, new_firstname,
new_lastname, new_phone_number, new_birth_date)
VALUE (CURTIME(), 'INSERT', new.user_id, new.email, new.password, new.firstname,
new.lastname, new.phone_number, new.birth_date);

END $$

DELIMITER ;

**Creation of user_delete trigger (due to how I coded my application, user is currently deleted through deletion of cart, 
that will be changed in future):**

DROP TRIGGER IF EXISTS user_delete;

DELIMITER $$

CREATE TRIGGER user_delete AFTER DELETE ON users
FOR EACH ROW
BEGIN
INSERT INTO user_audit (event_date, event_type, user_id)
VALUE (CURTIME(), 'DELETE', old.user_id);
END $$

DELIMITER ;

**Creation of user_change trigger:**

DROP TRIGGER IF EXISTS user_update;

DELIMITER $$

CREATE TRIGGER user_update AFTER UPDATE ON users
FOR EACH ROW
BEGIN
INSERT INTO user_audit (event_date, event_type, user_id, old_email, new_email,
old_password, new_password, old_firstname, new_firstname,
old_lastname, new_lastname, old_phone_number, new_phone_number,
old_birth_date, new_birth_date)
VALUE (CURTIME(), 'UPDATE', old.user_id, old.email, new.email, old.password, new.password,
old.firstname, new.firstname, old.lastname, new.lastname, old.phone_number, new.phone_number,
old.birth_date, new.birth_date);
END $$

DELIMITER ;

**MOVIE AUDIT**

**Create movie_audit table:**

CREATE TABLE movie_audit (
event_id BIGINT NOT NULL AUTO_INCREMENT,
event_date DATETIME NOT NULL ,
event_type varchar(255) DEFAULT NULL,
movie_id BIGINT NOT NULL,
old_imdb_movie_id VARCHAR(255),
new_imdb_movie_id VARCHAR(255),
old_title VARCHAR(255),
new_title VARCHAR(255),
old_director VARCHAR(255),
new_director VARCHAR(255),
old_cast VARCHAR(255),
new_cast VARCHAR(255),
old_publication_date DATE,
new_publication_date DATE,
old_price DECIMAL (19,2),
new_price DECIMAL (19,2),
old_top_level VARCHAR(255),
new_top_level VARCHAR(255),
PRIMARY KEY (event_id)
);

**Create movie_insert trigger:**

DROP TRIGGER IF EXISTS movie_insert;

DELIMITER $$

CREATE TRIGGER movie_insert AFTER INSERT ON movies
FOR EACH ROW
BEGIN
INSERT INTO movie_audit (event_date, event_type, movie_id, new_imdb_movie_id, new_title,
new_director, new_cast, new_publication_date, new_price, new_top_level)
VALUE (CURTIME(), 'INSERT', new.movie_id, new.imdb_movie_id, new.title, new.director,
new.cast, new.publication_date, new.price, new.top_level);

END $$

DELIMITER ;

**Create movie_delete trigger:**

DROP TRIGGER IF EXISTS movie_delete;

DELIMITER $$

CREATE TRIGGER movie_delete AFTER DELETE ON movies
FOR EACH ROW
BEGIN
INSERT INTO movie_audit (event_date, event_type, movie_id)
VALUE (CURTIME(), 'DELETE', old.movie_id);
END $$

DELIMITER ;

**Create movie_update trigger:**

DROP TRIGGER IF EXISTS movie_update;

DELIMITER $$

CREATE TRIGGER movie_update AFTER UPDATE ON movies
FOR EACH ROW
BEGIN
INSERT INTO movie_audit (event_date, event_type, movie_id, old_imdb_movie_id, new_imdb_movie_id,
old_title, new_title, old_director, new_director, old_cast, new_cast,
old_publication_date, new_publication_date, old_price, new_price,
old_top_level, new_top_level)
VALUE (CURTIME(), 'UPDATE', old.movie_id, old.imdb_movie_id, new.imdb_movie_id, old.title, new.title,
old.director, new.director, old.cast, new.cast, old.publication_date, new.publication_date,
old.price, new.price, old.top_level, new.top_level);
END $$

DELIMITER ;

**CART AUDIT**

**Create cart_audit table:**

CREATE TABLE cart_audit (
event_id BIGINT NOT NULL AUTO_INCREMENT,
event_date DATETIME NOT NULL ,
event_type varchar(255) DEFAULT NULL,
cart_id BIGINT NOT NULL,
user_id BIGINT,
old_transaction_id BIGINT,
new_transaction_id BIGINT,
old_movie_id BIGINT,
new_movie_id BIGINT,
comment varchar(255),
PRIMARY KEY (event_id)
);

**Create cart_insert trigger:**

DROP TRIGGER IF EXISTS cart_insert;

DELIMITER $$

CREATE TRIGGER cart_insert AFTER INSERT ON carts
FOR EACH ROW
BEGIN
INSERT INTO cart_audit (event_date, event_type, cart_id, user_id, new_transaction_id)
VALUE (CURTIME(), 'INSERT', new.cart_id, new.user_id, new.transaction_id);

END $$

DELIMITER ;

**Create cart_delete trigger:**

DROP TRIGGER IF EXISTS cart_delete;

DELIMITER $$

CREATE TRIGGER cart_delete AFTER DELETE ON carts
FOR EACH ROW
BEGIN
INSERT INTO cart_audit (event_date, event_type, cart_id, user_id)
VALUE (CURTIME(), 'DELETE', old.cart_id, old.user_id);
END $$

DELIMITER ;

**Create cart_update trigger:**

DROP TRIGGER IF EXISTS cart_update;

DELIMITER $$

CREATE TRIGGER cart_update AFTER UPDATE ON carts
FOR EACH ROW
BEGIN
INSERT INTO cart_audit (event_date, event_type, cart_id, user_id, old_transaction_id, new_transaction_id)
VALUE (CURTIME(), 'UPDATE', old.cart_id, old.user_id, old.transaction_id, new.transaction_id);
END $$

DELIMITER ;

**Create cart_add_movie trigger:**

DROP TRIGGER IF EXISTS cart_add_movie;

DELIMITER $$

CREATE TRIGGER cart_add_movie AFTER INSERT ON join_movie_cart
FOR EACH ROW
BEGIN
INSERT INTO cart_audit (event_date, event_type, cart_id, new_movie_id, comment)
VALUE (CURTIME(), 'UPDATE', new.cart_id, new.movie_id, 'ADD TO CART');
END $$

DELIMITER ;

**Create cart_remove_movie trigger:**

DROP TRIGGER IF EXISTS cart_remove_movie;

DELIMITER $$

CREATE TRIGGER cart_remove_movie AFTER DELETE ON join_movie_cart
FOR EACH ROW
BEGIN
INSERT INTO cart_audit (event_date, event_type, cart_id, old_movie_id, comment)
VALUE (CURTIME(), 'UPDATE', old.cart_id, old.movie_id, 'REMOVE FROM CART');
END $$

DELIMITER ;

**TRANSACTION AUDIT**

**Create transaction_audit table:**

CREATE TABLE transaction_audit (
event_id BIGINT NOT NULL AUTO_INCREMENT,
event_date DATETIME NOT NULL ,
event_type varchar(255) DEFAULT NULL,
transaction_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
new_transaction_date_and_time DATETIME,
new_transaction_value DECIMAL (19,2),
old_is_transaction_payed BOOLEAN,
new_is_transaction_payed BOOLEAN,
new_transaction_type VARCHAR(255),
PRIMARY KEY (event_id)
);

**Create transaction_insert trigger:**

DROP TRIGGER IF EXISTS transaction_insert;

DELIMITER $$

CREATE TRIGGER transaction_insert AFTER INSERT ON transactions
FOR EACH ROW
BEGIN
INSERT INTO transaction_audit (event_date, event_type, transaction_id, user_id, new_is_transaction_payed)
VALUE (CURTIME(), 'INSERT', new.transaction_id, new.user_id, new.is_transaction_payed);

END $$

DELIMITER ;

**Create transaction_update trigger:**

DROP TRIGGER IF EXISTS transaction_update;

DELIMITER $$

CREATE TRIGGER transaction_update AFTER UPDATE ON transactions
FOR EACH ROW
BEGIN
INSERT INTO transaction_audit (event_date, event_type, transaction_id, user_id, new_transaction_date_and_time,
new_transaction_value, old_is_transaction_payed, new_is_transaction_payed,
new_transaction_type)
VALUE (CURTIME(), 'UPDATE', old.transaction_id, old.user_id, new.transaction_date_and_time,
new.transaction_value, old.is_transaction_payed, new.is_transaction_payed, new.transaction_type);
END $$

DELIMITER ;

**RENT AUDIT**

**Create rent_audit table:**

CREATE TABLE rent_audit (
event_id BIGINT NOT NULL AUTO_INCREMENT,
event_date DATETIME NOT NULL ,
event_type varchar(255) DEFAULT NULL,
rent_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
transaction_id BIGINT NOT NULL,
movie_id BIGINT NOT NULL,
old_cost DECIMAL (19,2),
new_cost DECIMAL(19,2),
rent_date DATE,
old_return_date DATE,
new_return_date DATE,
PRIMARY KEY (event_id)
);

**Create rent_insert trigger:**

DROP TRIGGER IF EXISTS rent_insert;

DELIMITER $$

CREATE TRIGGER rent_insert AFTER INSERT ON rents
FOR EACH ROW
BEGIN
INSERT INTO rent_audit (event_date, event_type, rent_id, user_id, transaction_id, movie_id,
new_cost, rent_date, new_return_date)
VALUE (CURTIME(), 'INSERT', new.rent_id, new.user_id, new.transaction_id, new.movie_id,
new.cost, new.rent_date, new_return_date);

END $$

DELIMITER ;

**Create rent_update trigger:**

DROP TRIGGER IF EXISTS rent_update;

DELIMITER $$

CREATE TRIGGER rent_update AFTER UPDATE ON rents
FOR EACH ROW
BEGIN
INSERT INTO rent_audit (event_date, event_type, rent_id, user_id, transaction_id, movie_id,
old_cost, new_cost, rent_date, old_return_date, new_return_date)
VALUE (CURTIME(), 'UPDATE', old.rent_id, old.user_id, new.transaction_id, old.movie_id,
old.cost, new.cost, old.rent_date, old.return_date, new.return_date);
END $$

DELIMITER ;