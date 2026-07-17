-- data.sql
-- Insertar oficios típicos de una logia simbólica
INSERT INTO lodge_offices (office_name, created_at, created_by) VALUES
('Venerable Maestro', CURRENT_DATE, 'system'),
('Primer Vigilante', CURRENT_DATE, 'system'),
('Segundo Vigilante', CURRENT_DATE, 'system'),
('Orador', CURRENT_DATE, 'system'),
('Secretario', CURRENT_DATE, 'system'),
('Tesorero', CURRENT_DATE, 'system'),
('Hospitalario', CURRENT_DATE, 'system');

-- Insertar conceptos de ingreso típicos
INSERT INTO income_concepts (description, created_at, created_by) VALUES
('Cuota mensual', CURRENT_DATE, 'system'),
('Cuota semestral', CURRENT_DATE, 'system'),
('Cuota anual', CURRENT_DATE, 'system'),
('Iniciación', CURRENT_DATE, 'system'),
('Aumento de salario (Compañero)', CURRENT_DATE, 'system'),
('Exaltación (Maestro)', CURRENT_DATE, 'system'),
('Donativo voluntario', CURRENT_DATE, 'system'),
('Regularización', CURRENT_DATE, 'system');

-- Insertar usuario sistema para referencias administrativas
INSERT INTO masons (name, second_name, last_name, second_last_name, date_of_birth,
                    is_free_member, created_at, created_by, updated_at, updated_by ) VALUES
('System', NULL, 'User', NULL,
'1900-01-01', NULL, CURRENT_TIMESTAMP,
'system', CURRENT_TIMESTAMP, 'system');

INSERT INTO mason_contacts (mason_id, mobile, email, emergency_contact_name,
                            emergency_contact_phone, contact_preference, created_at,
                            created_by, updated_at, updated_by) VALUES
((SELECT mason_id FROM masons WHERE name = 'System' AND last_name = 'User'),
'0000000000', 'system@lodgetreasury.local', 'Admin',
'0000000000', 'EMAIL', CURRENT_TIMESTAMP,
'system', CURRENT_TIMESTAMP, 'system');