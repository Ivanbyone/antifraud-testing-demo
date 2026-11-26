CREATE TABLE black_dictionary (
    id          BIGSERIAL PRIMARY KEY,
    blockchain  VARCHAR(64) NOT NULL,
    b_type      VARCHAR(64) NOT NULL,
    b_value     VARCHAR(255) NOT NULL,
    b_reason    TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ,
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
);

CREATE TABLE outbox IF NOT EXISTS (
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    queue_name  VARCHAR(255) NOT NULL,
    o_message   TEXT NOT NULL,
    o_status    VARCHAR(64) NOT NULL,
    updated_at  TIMESTAMPTZ,
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
)
