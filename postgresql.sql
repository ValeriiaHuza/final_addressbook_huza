-- Drop the database if it exists
DROP DATABASE IF EXISTS address_book_gfl;

-- Create a new database
CREATE DATABASE address_book_gfl;

\c address_book_gfl

-- Create the note_user table
CREATE TABLE note_user
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    login    VARCHAR(50) NOT NULL,
    password VARCHAR(50)
);
-- Create the connection table
CREATE TABLE connection
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(255)
);

-- Create the person table
CREATE TABLE person
(
    id                 SERIAL PRIMARY KEY,
    user_id            INT         NOT NULL,
    first_name         VARCHAR(50) NOT NULL,
    last_name          VARCHAR(50),
    surname            VARCHAR(50),
    address            VARCHAR(255),
    email              VARCHAR(255),
    connection_id      INT         NOT NULL,
    person_description VARCHAR(255),
    birthday           DATE,
    last_modified      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT person_connection_id_fk FOREIGN KEY (connection_id) REFERENCES connection (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT person_user_id_fk FOREIGN KEY (user_id) REFERENCES note_user (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Create the phonenumber table
CREATE TABLE phonenumber
(
    id        SERIAL PRIMARY KEY,
    phone     VARCHAR(255),
    person_id INT,
    CONSTRAINT phonenumber_person_id_fk FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create the job table
CREATE TABLE job
(
    id        SERIAL PRIMARY KEY,
    job_place VARCHAR(255),
    vacancy   VARCHAR(255),
    person_id INT,
    CONSTRAINT job_person_id_fk FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create the educaton table
CREATE TABLE education
(
    id              SERIAL PRIMARY KEY,
    education_place VARCHAR(255),
    specialization  VARCHAR(255),
    person_id       INT,
    CONSTRAINT study_person_id_fk FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO note_user (name, login, password)
VALUES ('Валерія', 'user', '1234'),
       ('Михайло', 'misha_notebook', 'qwerty'),
       ('Kate', 'kate', '12345678');

INSERT INTO connection (title)
VALUES ('Родина'),
       ('Друг'),
       ('Колега'),
       ('Знайомий'),
       ('Однокласник'),
       ('Сусід'),
       ('Одногрупник'),
       ('Клієнт'),
       ('Викладач');

INSERT INTO person (user_id, first_name, last_name, surname, address, email, connection_id, person_description,
                    birthday)
VALUES (1, 'Іван', 'Іванов', 'Михайлович', 'вул. Головна, 1', 'ivan@gmail.com', 3,
        'Добрий і приємний у спілкуванні. Працювали разом', '1985-05-10'),
       (1, 'Олена', 'Петренко', 'Сергіївна', 'пров. Лісовий, 2', 'olena@gmail.com', 1, 'Двоюрідна сестра',
        '1998-09-15'),
       (1, 'Микола', 'Сидоренко', 'Олександрович', 'пл. Незалежності, 3', 'mykola@gmail.com', 5, NULL, '2001-12-20'),
       (1, 'Олександра', 'Григоренко', 'Володимирівна', 'вул. Зелена, 4', 'oleksandra@gmail.com', 1,
        'Любить фіолетовий колір', '2002-07-25'),
       (1, 'Віталій', 'Ігнатьєв', NULL, NULL, 'vitaliy@gmail.com', 4, NULL, NULL),
       (1, 'Оксана', 'Коваль', NULL, NULL, 'oksana@gmail.com', 2, NULL, '1991-11-05'),
       (1, 'Петро', NULL, NULL, 'вул. Центральна, 7', NULL, 6, NULL, NULL),
       (1, 'Віра', 'Павленко', 'Михайлівна', NULL, 'vira@gmail.com', 9, NULL, '1985-01-17'),
       (1, 'Андрій', 'Лисенко', NULL, NULL, 'andriy@gmail.com', 1, 'Троюрідний брат', '1993-06-22'),
       (1, 'Наталія', 'Морозова', NULL, NULL, 'nataliya@gmail.com', 2, NULL, '1999-04-18'),
       (1, 'Олег', 'Шевченко', 'Вікторович', 'вул. Хрещатик, 11', 'oleg@gmail.com', 2, 'Друг із дитинства',
        '1998-08-20'),
       (1, 'Юлія', 'Коваленко', NULL, NULL, NULL, 4, 'ЗУстрілись на день народження Каті', NULL),
       (1, 'Василь', 'Петров', 'Миколайович', 'просп. Перемоги, 13', 'vasyl@gmail.com', 3, 'Колега з роботи',
        '1978-11-10'),
       (1, 'Марія', 'Григоренко', 'Анатоліївна', NULL, 'maria@gmail.com', 7, NULL, '2001-04-25'),
       (2, 'Сергій', 'Іванов', 'Петрович', 'вул. Степна, 15', 'sergiy@gmail.com', 7, NULL, '1995-07-30'),
       (2, 'Олексій', 'Сидоров', NULL, 'вул. Зарічна, 16', 'oleksiy@gmail.com', 3, 'Колега по роботі', '1992-01-12'),
       (2, 'Вікторія', NULL, NULL, 'вул. Городня, 17', 'viktoriya@gmail.com', 6, NULL, '1989-09-05'),
       (2, 'Олег', 'Павлюк', 'Ігорович', 'вул. Лугова, 18', 'oleg.pavluk@gmail.com', 8, NULL, '1975-12-18'),
       (2, 'Наталія', 'Козак', 'Андріївна', 'пл. Шевченка, 19', 'nataliya.kozak@gmail.com', 8, NULL, '1991-03-08'),
       (2, 'Артем', 'Кузьмін', 'Олегович', 'вул. Літня, 20', 'artem.kuzmin@gmail.com', 8, NULL, '1980-06-22');

INSERT INTO phonenumber (phone, person_id)
VALUES ('380701185', 1),
       ('380907096', 1),
       ('380065469', 2),
       ('380880078', 2),
       ('380930741', 3),
       ('380265272', 4),
       ('380697653', 6),
       ('380136017', 7),
       ('380855895', 8),
       ('380374640', 9),
       ('380120718', 9),
       ('380614841', 9),
       ('380690545', 10),
       ('380410784', 11),
       ('380342473', 13),
       ('380826541', 15),
       ('380823523', 15),
       ('380694114', 16),
       ('380092345', 17),
       ('380189005', 17),
       ('380470393', 18),
       ('380179873', 18),
       ('380878871', 19),
       ('380169259', 20),
       ('380549019', 8),
       ('380132301', 7),
       ('380752531', 6);

INSERT INTO job (job_place, vacancy, person_id)
VALUES ('Dou', 'Інженер-програміст', 1),
       ('Green Корпорація', 'Менеджер з маркетингу', 2),
       ('Apple', 'Swift developer', 4),
       ('Acme Інк.', 'Менеджер проектів', 6),
       ('Tech Рішення', 'Веб-розробник', 7),
       ('Глобальна Логістика', 'Координатор логістики', 10),
       ('Сміт і Сини', 'Бухгалтер', 11),
       ('Індустрії Джонсона', 'Спеціаліст з кадрів', 9),
       ('Світле Майбутнє', 'Вчитель', 8);

INSERT INTO education (education_place, specialization, person_id)
VALUES ('КПІ', 'Інженерія програмного забезпечення', 1),
       ('Наукма', 'Маркетинг', 2),
       ('Наукма', 'Інженерія програмного забезпечення', 4),
       ('Глобальний Університет', 'Менеджмент', 5),
       ('456 Інститут', 'Продажі', 9),
       ('Університет LMN', 'Освіта', 10),
       ('OPQ Коледж', 'Психологія', 3);

CREATE TABLE birthday_wish
(
    id   SERIAL PRIMARY KEY,
    wish TEXT NOT NULL
);

INSERT INTO birthday_wish (wish)
VALUES ('З днем народження! Бажаю вам щастя, здоровʼя та невичерпної енергії!'),
       ('Вітаю з Днем Народження! Нехай цей день принесе вам багато позитиву та радості!'),
       ('Вітаю з Днем Народження! Натхнення, успіхів та любові!'),
       ('Вітаю з Днем Народження! Нехай всі ваші мрії здійсняться!'),
       ('Щиро вітаю вас з Днем Народження! Нехай кожен день буде сповнений радості та любові!'),
       ('З Днем Народження! Бажаю вам здоров’я, достатку та благополуччя!'),
       ('Вітаю з Днем Народження! Бажаю вам море позитиву та незабутніх вражень!'),
       ('Щиро вітаю вас з Днем Народження! Бажаю успіхів у всіх починаннях та вдалих досягнень!'),
       ('Зичу вам яскравого свята, добрих друзів і веселих гостей! Щасливого Дня Народження!'),
       ('Вітаю з Днем Народження! Нехай ваше життя буде наповнене яскравими подіями та приємними враженнями!');
