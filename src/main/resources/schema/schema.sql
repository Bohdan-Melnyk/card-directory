CREATE TABLE IF NOT EXISTS cards
(
    bin        BIGINT PRIMARY KEY,
    min_range  DOUBLE PRECISION,
    max_range  DOUBLE PRECISION,
    alpha_code VARCHAR(255),
    bank_name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cards_old
(
    bin        BIGINT PRIMARY KEY,
    min_range  BIGINT,
    max_range  BIGINT,
    alpha_code VARCHAR(255),
    bank_name  VARCHAR(255)
);