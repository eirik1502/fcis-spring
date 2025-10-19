CREATE TABLE ARBEIDSFORHOLD
(
    ID                    TEXT DEFAULT gen_random_uuid()::text PRIMARY KEY,
    NAV_ARBEIDSFORHOLD_ID TEXT                     NOT NULL UNIQUE,
    FNR                   TEXT                     NOT NULL,
    ORGNUMMER             TEXT                     NOT NULL,
    JURIDISK_ORGNUMMER    TEXT                     NOT NULL,
    ORGNAVN               TEXT                     NOT NULL,
    FOM                   DATE                     NOT NULL,
    TOM                   DATE                     NULL,
    ARBEIDSFORHOLD_TYPE   TEXT                     NULL,
    OPPRETTET             TIMESTAMP WITH TIME ZONE NOT NULL
);
