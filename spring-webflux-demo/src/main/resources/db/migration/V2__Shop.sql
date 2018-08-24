CREATE SEQUENCE SHOP_SEQ start with 1 increment by 1;


CREATE TABLE SHOP
(
    id bigint,
    name character varying(255),
    size double precision,
    PRIMARY KEY (id)
)