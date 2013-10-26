-- Table: company

-- DROP TABLE company;

CREATE TABLE company
(
  id serial NOT NULL,
  "name" character varying(64),
  cif character varying(10),
  ticker character(6),
  CONSTRAINT company_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE company OWNER TO postgres;
