DROP VIEW IF EXISTS v_masons_with_status;

CREATE VIEW v_masons_with_status AS
SELECT
    m.mason_id,
    m.name,
    m.second_name,
    m.last_name,
    m.second_last_name,
    m.date_of_birth,
    m.is_free_member,
    m.deleted,
    m.created_at,
    m.created_by,
    m.updated_at,
    m.updated_by,
    h.status AS current_status,
    h.change_date AS last_status_change,
    h.reason AS last_status_reason,
    h.outstanding_debt AS last_outstanding_debt
FROM masons m
LEFT JOIN mason_status_history h ON h.mason_id = m.mason_id
AND h.history_id = (
    SELECT history_id
    FROM mason_status_history h2
    WHERE h2.mason_id = m.mason_id
    ORDER BY h2.change_date DESC, h2.history_id DESC
    LIMIT 1
)
WHERE m.deleted = false;