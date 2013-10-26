-- Table: balance

-- DROP TABLE balance;

CREATE TABLE balance
(
  id serial NOT NULL,
  company_id integer,
  period character(7),
  CONSTRAINT balance_pkey PRIMARY KEY (id),
  CONSTRAINT balance_company_fkey FOREIGN KEY (company_id)
      REFERENCES company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE balance OWNER TO postgres;
