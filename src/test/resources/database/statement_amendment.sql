
CREATE TABLE IF NOT EXISTS public.statement_amendments (
    id integer NOT NULL,
    statement_id integer NOT NULL,
    additional_comment text NOT NULL,
    date_submitted timestamp with time zone DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    deleted timestamp with time zone
);

CREATE SEQUENCE IF NOT EXISTS public.statement_amendments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.statement_amendments_id_seq OWNED BY public.statement_amendments.id;

ALTER TABLE ONLY public.statement_amendments ALTER COLUMN id SET DEFAULT nextval('public.statement_amendments_id_seq'::regclass);

ALTER TABLE ONLY public.statement_amendments
    ADD CONSTRAINT statement_amendments_pkey PRIMARY KEY (id);
