DROP TABLE IF EXISTS
apps,
hits;

CREATE TABLE IF NOT EXISTS apps
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name            varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS hits
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app_id          BIGINT REFERENCES apps(id) ON DELETE CASCADE,
    uri             varchar(32) NOT NULL,
    ip              varchar(15) NOT NULL,
    time_stamp      TIMESTAMP
);
