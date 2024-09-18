CREATE TABLE IF NOT EXISTS cards
(
    bin        NUMERIC PRIMARY KEY,
    min_range  NUMERIC,
    max_range  NUMERIC,
    alpha_code VARCHAR(255),
    bank_name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cards_temp
(
    bin        NUMERIC PRIMARY KEY,
    min_range  NUMERIC,
    max_range  NUMERIC,
    alpha_code VARCHAR(255),
    bank_name  VARCHAR(255)
);