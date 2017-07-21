CREATE TABLE security_permissions (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT PERMISSIONS_NAME_UQ UNIQUE(name)
);

CREATE TABLE security_role_permissions (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL
);

CREATE TABLE security_roles (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT ROLES_NAME_UQ UNIQUE(name)
);

CREATE TABLE security_subject_roles (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL
);

CREATE TABLE security_subjects (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    salt      VARCHAR(255) NOT NULL,
    enabled   BOOLEAN,
    user_id   BIGINT,
    CONSTRAINT SUBJECTS_USERNAME_UQ UNIQUE(username)
);

CREATE TABLE users
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    pay_number         VARCHAR(20)    NOT NULL,
    name               VARCHAR(256)   NOT NULL,
    email              VARCHAR(256)   NOT NULL UNIQUE,
    manager_id         BIGINT,
    created_by_subject BIGINT,
    creation_date      TIMESTAMP      NOT NULL,
    is_manager         VARCHAR(1)     NOT NULL CHECK (is_manager IN ('Y', 'N')),
    phone_number       VARCHAR(50),
    mobile_number      VARCHAR(50),
    organisation       VARCHAR(100),
    job_title          VARCHAR(256),
    department         VARCHAR(256),
    address            VARCHAR(2000),
    location           VARCHAR(100),
    licence            VARCHAR(2000)
);