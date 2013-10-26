-- Table: "value"

-- DROP TABLE "value";

CREATE TABLE "value"
(
  id bigserial NOT NULL,
  balance_id integer,
  value_key_id integer,
  "value" double precision,
  CONSTRAINT value_pkey PRIMARY KEY (id),
  CONSTRAINT value_balance_fkey FOREIGN KEY (balance_id)
      REFERENCES balance (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT value_value_key_pkey FOREIGN KEY (value_key_id)
      REFERENCES value_key (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unique_balance_valuekey UNIQUE (balance_id, value_key_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "value" OWNER TO postgres;
