-- data.sql

-- Datos iniciales (grados simbólicos)
INSERT INTO degrees (degree_code, degree_name, degree_level, created_at, created_by) VALUES
('AM', 'Aprendiz Masón', 1, CURRENT_TIMESTAMP, 'SYSTEM'),
('CM', 'Compañero Masón', 2, CURRENT_TIMESTAMP, 'SYSTEM'),
('MM', 'Maestro Masón',   3, CURRENT_TIMESTAMP,'SYSTEM')
ON CONFLICT (degree_code) DO NOTHING;

-- Insertar oficios típicos de una logia simbólica
INSERT INTO lodge_offices (office_name, created_at, created_by) VALUES
('Venerable Maestro', CURRENT_TIMESTAMP, 'system'),
('Primer Vigilante', CURRENT_TIMESTAMP, 'system'),
('Segundo Vigilante', CURRENT_TIMESTAMP, 'system'),
('Orador', CURRENT_TIMESTAMP, 'system'),
('Secretario', CURRENT_TIMESTAMP, 'system'),
('Tesorero', CURRENT_TIMESTAMP, 'system'),
('Hospitalario', CURRENT_TIMESTAMP, 'system');

-- Insertar conceptos de ingreso típicos
INSERT INTO income_concepts (description, created_at, created_by) VALUES
('Cuota mensual', CURRENT_TIMESTAMP, 'system'),
('Cuota semestral', CURRENT_TIMESTAMP, 'system'),
('Cuota anual', CURRENT_TIMESTAMP, 'system'),
('Iniciación', CURRENT_TIMESTAMP, 'system'),
('Aumento de salario (Compañero)', CURRENT_TIMESTAMP, 'system'),
('Exaltación (Maestro)', CURRENT_TIMESTAMP, 'system'),
('Donativo voluntario', CURRENT_TIMESTAMP, 'system'),
('Regularización', CURRENT_TIMESTAMP, 'system');

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