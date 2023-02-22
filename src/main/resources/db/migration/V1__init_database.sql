DROP TABLE IF EXISTS "public"."payment";

CREATE TABLE "public"."payment"
(
    "id"                 character varying(36) PRIMARY KEY,
    "price"              character varying(36) NOT NULL,
    "payment_method"     character varying(40),
    "point"              int,
    "datetime"           timestamptz
);