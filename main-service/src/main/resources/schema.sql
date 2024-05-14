DROP TABLE IF EXISTS
users,
categories,
locations,
events,
compilations,
compilations_events,
requests;

CREATE TABLE IF NOT EXISTS users
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email               varchar(254) UNIQUE NOT NULL,
    name                varchar(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name                varchar(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat                 FLOAT NOT NULL,
    lon                 FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation          varchar(2000) NOT NULL,
    category_id         BIGINT REFERENCES categories(id),
    confirmed_requests  BIGINT,
    created_on          TIMESTAMP NOT NULL,
    description         varchar(7000) NOT NULL,
    event_date          TIMESTAMP NOT NULL,
    initiator_id        BIGINT REFERENCES users(id),
    location_id         BIGINT REFERENCES locations(id),
    paid                Boolean NOT NULL,
    participant_limit   BIGINT NOT NULL,
    published_on        TIMESTAMP,
    request_moderation  Boolean NOT NULL,
    state               varchar(16) NOT NULL,
    title               varchar(120) NOT NULL,
    views               BIGINT

    CHECK (state IN ('PENDING',
                     'PUBLISHED',
                     'REJECTED',
                     'CANCELED'))
);

CREATE TABLE IF NOT EXISTS compilations
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title               varchar(50) UNIQUE NOT NULL,
    pinned              Boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilations_id     BIGINT REFERENCES compilations(id) ON DELETE CASCADE,
    events_id           BIGINT REFERENCES events(id) ON DELETE CASCADE,

    PRIMARY KEY (compilations_id, events_id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created             TIMESTAMP NOT NULL,
    event_id            BIGINT REFERENCES events(id),
    requester_id        BIGINT REFERENCES users(id),
    status              VARCHAR(16) NOT NULL,

    CHECK (status IN ('PENDING',
                      'CONFIRMED',
                      'REJECTED',
                      'CANCELED'))
);
