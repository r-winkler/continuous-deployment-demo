CREATE SEQUENCE PRODUCT_SEQ start with 1 increment by 1;


CREATE TABLE PRODUCT
(
    id bigint,
    name character varying(255),
    price double precision,
    PRIMARY KEY (id)
)