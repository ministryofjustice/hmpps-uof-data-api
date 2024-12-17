CREATE TABLE report (
    id integer NOT NULL,
    form_response jsonb,
    user_id character varying(32),
    sequence_no integer DEFAULT 1 NOT NULL,
    booking_id bigint DEFAULT '-1'::bigint NOT NULL,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    status character varying(20),
    submitted_date timestamp with time zone,
    offender_no character varying(32) NOT NULL,
    reporter_name character varying(128) NOT NULL,
    incident_date timestamp with time zone,
    agency_id character varying(6),
    updated_date timestamp with time zone,
    deleted timestamp with time zone
);

CREATE SEQUENCE form_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY report ALTER COLUMN id SET DEFAULT nextval('form_id_seq'::regclass);

ALTER TABLE ONLY report
    ADD CONSTRAINT form_pkey PRIMARY KEY (id);