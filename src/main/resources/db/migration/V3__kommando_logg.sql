CREATE TABLE kommando_logg
(
    kommando_logg_id VARCHAR PRIMARY KEY               DEFAULT gen_random_uuid()::text,
    opprettet        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    trace_id         TEXT                     NULL,
    kommando         JSONB                    NOT NULL,
    plan             JSONB                    NOT NULL,
    suksess          BOOLEAN                  NOT NULL,
    feilmelding      TEXT                     NULL,
    kildesystem      TEXT                     NULL,
    aktortype        TEXT                     NULL,
    aktorident       TEXT                     NULL
);