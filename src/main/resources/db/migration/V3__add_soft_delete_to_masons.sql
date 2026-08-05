ALTER TABLE masons
ADD COLUMN deleted BOOLEAN DEFAULT FALSE NOT NULL;

CREATE INDEX idx_masons_deleted ON masons(deleted);