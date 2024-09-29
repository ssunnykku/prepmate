CREATE TABLE `questions`
(
    `question_id`  bigint        NOT NULL,
    `question`     VARCHAR(600)  NULL,
    `answer`       VARCHAR(3000) NULL,
    `created_at`   DATETIME      NULL,
    `interview_id` bigint        NOT NULL
);

CREATE TABLE `interviews`
(
    `interview_id`   bigint       NOT NULL,
    `interview_name` VARCHAR(200) NULL,
    `description`    VARCHAR(600) NULL,
    `created_at`     DATETIME     NULL,
    `user_id`        UUID         NOT NULL
);

CREATE TABLE `users`
(
    `user_id`    UUID         NOT NULL,
    `name`       VARCHAR(100) NULL,
    `email`      VARCHAR(100) NULL,
    `password`   VARCHAR(100) NULL,
    `created_at` DATETIME     NULL
);

ALTER TABLE `questions`
    ADD CONSTRAINT `PK_QUESTIONS` PRIMARY KEY (
                                               `question_id`
        );

ALTER TABLE `interviews`
    ADD CONSTRAINT `PK_INTERVIEWS` PRIMARY KEY (
                                                `interview_id`
        );

ALTER TABLE `users`
    ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
                                           `user_id`
        );

