CREATE TABLE IF NOT EXISTS user_preferences
(
    id uuid NOT NULL,
    employment_types character varying(255) COLLATE pg_catalog."default",
    country character varying(2) COLLATE pg_catalog."default",
    keywords character varying(255) NOT NULL COLLATE pg_catalog."default",
    user_id uuid NOT NULL,
    exclude_job_publishers character varying(255) COLLATE pg_catalog."default",
    remote_work boolean NOT NULL DEFAULT false,
    CONSTRAINT user_preferences_pkey PRIMARY KEY (id),
    CONSTRAINT user_id_ukey UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS jobs_user
(
    job_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id uuid NOT NULL,
    received_at timestamp(6) with time zone,
    CONSTRAINT jobs_user_pkey PRIMARY KEY (job_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_jobs_user_user_id ON jobs_user(user_id);
CREATE INDEX IF NOT EXISTS idx_jobs_user_received_at ON jobs_user(received_at);