DROP TABLE IF EXISTS "crypto_price";
DROP TABLE IF EXISTS "app_user";


CREATE TABLE "crypto_price"(
                                id INTEGER NOT NULL,
                                symbol VARCHAR(10) NOT NULL,
                                price DECIMAL(10,30) NOT NULL,
                                created DATE NOT NULL,
                                PRIMARY KEY (id));

CREATE TABLE "app_user"(
                                id INTEGER NOT NULL,
                                name VARCHAR(255) NOT NULL,
                                active BOOLEAN NOT NULL,
                                number VARCHAR(50) NOT NULL,
                                PRIMARY KEY (id));