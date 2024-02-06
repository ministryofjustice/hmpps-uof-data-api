CREATE TABLE IF NOT EXISTS statement (
    id integer NOT NULL,
    report_id integer NOT NULL,
    user_id character varying(255),
    name character varying(255),
    email character varying(255),
    submitted_date timestamp with time zone,
    statement_status character varying(255) NOT NULL,
    last_training_month smallint,
    last_training_year smallint,
    job_start_year smallint,
    statement text,
    staff_id integer,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    updated_date timestamp with time zone,
    next_reminder_date timestamp with time zone,
    overdue_date timestamp with time zone,
    in_progress boolean DEFAULT false NOT NULL,
    deleted timestamp with time zone,
    removal_requested_reason text,
    removal_requested_date timestamp with time zone
);

CREATE SEQUENCE IF NOT EXISTS involved_staff_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY statement ALTER COLUMN id SET DEFAULT nextval('involved_staff_id_seq'::regclass);

ALTER TABLE ONLY statement
    ADD CONSTRAINT involved_staff_pkey PRIMARY KEY (id);

