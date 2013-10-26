-- Table: value_key

-- DROP TABLE value_key;

CREATE TABLE value_key
(
  id serial NOT NULL,
  xbrlid integer,
  key_name character varying(255),
  CONSTRAINT value_key_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE value_key OWNER TO postgres;
