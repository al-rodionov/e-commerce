CREATE TABLE PAYMENT_METHOD
(
    ID                 SERIAL        PRIMARY KEY,
    NAME               VARCHAR(255)     NOT NULL,
    POINTS_MODIFIER    DOUBLE PRECISION NOT NULL,
    PRICE_MODIFIER_MAX DOUBLE PRECISION NOT NULL,
    PRICE_MODIFIER_MIN DOUBLE PRECISION NOT NULL
);

CREATE TABLE TRANSACTION
(
    ID              SERIAL        PRIMARY KEY,
    ADDITIONAL_ITEM VARCHAR(255),
    CUSTOMER_ID     BIGINT           NOT NULL,
    DATE_TIME       TIMESTAMP        NOT NULL,
    PAYMENT_METHOD  VARCHAR(255)     NOT NULL,
    PRICE           DOUBLE PRECISION NOT NULL,
    PRICE_MODIFIER  DOUBLE PRECISION NOT NULL
);

CREATE TABLE PAYMENT
(
    ID        SERIAL        PRIMARY KEY,
    SALES     DOUBLE PRECISION NOT NULL,
    POINTS    DOUBLE PRECISION NOT NULL,
    DATE_TIME TIMESTAMP        NOT NULL
);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (1, 'CASH', 0.9, 1.0, 0.05);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (2, 'CASH_ON_DELIVERY', 1.0, 1.02, 0.05);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (3, 'VISA', 0.95, 1.0, 0.03);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (4, 'MASTERCARD', 0.95, 1.0, 0.03);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (5, 'AMEX', 0.98, 1.01, 0.02);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (6, 'JCB', 0.95, 1.0, 0.05);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (7, 'LINE_PAY', 1.0, 1.0, 0.01);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (8, 'PAYPAY', 1.0, 1.0, 0.01);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (9, 'POINTS', 1.0, 1.0, 0.0);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (10, 'GRAB_PAY', 1.0, 1.0, 0.01);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (11, 'BANK_TRANSFER', 1.0, 1.0, 0.0);

INSERT INTO PAYMENT_METHOD (ID, NAME, PRICE_MODIFIER_MIN, PRICE_MODIFIER_MAX, POINTS_MODIFIER)
VALUES (12, 'CHEQUE', 0.9, 1.0, 0.0);
