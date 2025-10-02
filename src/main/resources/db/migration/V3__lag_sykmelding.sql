
CREATE TABLE sykmelding (
    database_id TEXT PRIMARY KEY DEFAULT gen_random_uuid()::text,
    sykmelding_id TEXT NOT NULL,
    fnr TEXT NOT NULL,
    fom DATE NOT NULL,
    tom DATE NOT NULL
)
