-- =================================================================
-- Initial Data for Dico Backend
-- Version 2.9 - Final password hash correction
-- =================================================================

-- Belangrijke Opmerkingen:
-- 1. Dit script wordt automatisch uitgevoerd door Spring Boot bij het opstarten.
-- 2. Het wachtwoord voor alle gebruikers is 'password'. De correcte BCrypt hash hiervoor is hieronder gebruikt.

-- -----------------------------------------------------
-- Gebruikers (Users)
-- We creëren een patiënt, een voogd en een zorgverlener met alle velden ingevuld.
-- -----------------------------------------------------
INSERT INTO users (id, username, email, password, first_name, last_name, dob, role, enabled, created_at, email_verified, has_details, has_preferences) VALUES
(1, 'piet.patient@dico.com', 'piet.patient@dico.com', '$2a$10$.Ijkn8wUz3UJXJEWYQ.iQe/JlCwbNqSa0kdAqbGNsO4xYXnW9hDS.', 'Piet', 'Patient', '1990-06-15', 'PATIENT', true, '2023-01-10T10:00:00', true, true, true),
(2, 'gerda.ouder@dico.com', 'gerda.ouder@dico.com', '$2a$10$.Ijkn8wUz3UJXJEWYQ.iQe/JlCwbNqSa0kdAqbGNsO4xYXnW9hDS.', 'Gerda', 'Ouder', '1985-03-25', 'GUARDIAN', true, '2023-01-11T11:00:00', true, true, true),
(3, 'hans.zv@dico.com', 'hans.zv@dico.com', '$2a$10$.Ijkn8wUz3UJXJEWYQ.iQe/JlCwbNqSa0kdAqbGNsO4xYXnW9hDS.', 'Hans', 'Zorgverlener', '1978-11-12', 'PROVIDER', true, '2023-01-12T12:00:00', true, true, true)
ON CONFLICT (id) DO UPDATE SET
    username = EXCLUDED.username,
    email = EXCLUDED.email,
    password = EXCLUDED.password,
    first_name = EXCLUDED.first_name,
    last_name = EXCLUDED.last_name,
    dob = EXCLUDED.dob,
    role = EXCLUDED.role,
    enabled = EXCLUDED.enabled,
    created_at = EXCLUDED.created_at,
    email_verified = EXCLUDED.email_verified,
    has_details = EXCLUDED.has_details,
    has_preferences = EXCLUDED.has_preferences;

-- -----------------------------------------------------
-- Gebruikersprofielen (User Profiles)
-- Gedetailleerde info voor alle gebruikers.
-- -----------------------------------------------------
INSERT INTO user_profiles (id, user_id, first_name, last_name, dob, gender, length, weight, diabetes_type, long_acting_insulin, short_acting_insulin) VALUES
(1, 1, 'Piet', 'Patient', '1990-06-15', 'MALE', 182.5, 88.0, 'TYPE_1', 'Lantus', 'NovoRapid'),
(2, 2, 'Gerda', 'Ouder', '1985-03-25', 'FEMALE', NULL, NULL, NULL, NULL, NULL),
(3, 3, 'Hans', 'Zorgverlener', '1978-11-12', 'MALE', NULL, NULL, NULL, NULL, NULL)
ON CONFLICT (id) DO UPDATE SET
    user_id = EXCLUDED.user_id,
    first_name = EXCLUDED.first_name,
    last_name = EXCLUDED.last_name,
    dob = EXCLUDED.dob,
    gender = EXCLUDED.gender,
    length = EXCLUDED.length,
    weight = EXCLUDED.weight,
    diabetes_type = EXCLUDED.diabetes_type,
    long_acting_insulin = EXCLUDED.long_acting_insulin,
    short_acting_insulin = EXCLUDED.short_acting_insulin;

-- -----------------------------------------------------
-- Medische Profielen (Medical Profiles)
-- Aanvullende medische data alleen voor de patiënt.
-- -----------------------------------------------------
INSERT INTO medical_profile (id, diabetes_type, diagnosis_year, hba1c, carbs_per_day_grams, activity_minutes, sleep_hours, stress_score, systolic, diastolic, weight_kg, last_updated) VALUES
(1, 'TYPE_1', 2005, 6.8, 150, 30, 7.5, 3, 120, 80, 88.0, NOW())
ON CONFLICT (id) DO UPDATE SET
    diabetes_type = EXCLUDED.diabetes_type,
    diagnosis_year = EXCLUDED.diagnosis_year,
    hba1c = EXCLUDED.hba1c,
    carbs_per_day_grams = EXCLUDED.carbs_per_day_grams,
    activity_minutes = EXCLUDED.activity_minutes,
    sleep_hours = EXCLUDED.sleep_hours,
    stress_score = EXCLUDED.stress_score,
    systolic = EXCLUDED.systolic,
    diastolic = EXCLUDED.diastolic,
    weight_kg = EXCLUDED.weight_kg,
    last_updated = EXCLUDED.last_updated;

-- -----------------------------------------------------
-- Apparaten van de Gebruiker (User Devices)
-- -----------------------------------------------------
DELETE FROM user_devices WHERE user_id = 1;
INSERT INTO user_devices (user_id, category, manufacturer, model) VALUES
(1, 'POMP', 'Medtronic', 'MiniMed 780G'),
(1, 'CGM', 'Dexcom', 'G6');

-- -----------------------------------------------------
-- Glucosemetingen (Glucose Measurements)
-- -----------------------------------------------------
DELETE FROM glucose_measurement WHERE user_id = 1;
INSERT INTO glucose_measurement (user_id, timestamp, value, source, measurement_type) VALUES
(1, NOW() - INTERVAL '2 days', 5.5, 'MANUAL_UPLOAD', 'HISTORIC'),
(1, NOW() - INTERVAL '1 day' + INTERVAL '4 hours', 8.2, 'LIBREVIEW', 'SCANNED'),
(1, NOW() - INTERVAL '1 day' + INTERVAL '10 hours', 6.8, 'MANUAL_ENTRY', 'MANUAL'),
(1, NOW() - INTERVAL '8 hours', 7.1, 'LIBREVIEW', 'SCANNED'),
(1, NOW() - INTERVAL '2 hours', 6.2, 'MANUAL_UPLOAD', 'HISTORIC');

-- -----------------------------------------------------
-- Koppelingen (User Patient Links)
-- -----------------------------------------------------
INSERT INTO user_patient_links (user_id, patient_id) VALUES
(2, 1), -- Gerda (voogd) -> Piet (patiënt)
(3, 1)  -- Hans (zorgverlener) -> Piet (patiënt)
ON CONFLICT (user_id, patient_id) DO NOTHING;

-- -----------------------------------------------------
-- Service Verbindingen (User Service Connections)
-- -----------------------------------------------------
INSERT INTO user_service_connections (id, user_id, service_name, email, password, access_token, last_sync) VALUES
(1, 1, 'LIBREVIEW', NULL, NULL, NULL, NULL)
ON CONFLICT (id) DO UPDATE SET
    user_id = EXCLUDED.user_id,
    service_name = EXCLUDED.service_name,
    email = NULL,
    password = NULL,
    access_token = NULL,
    last_sync = NULL;

-- Zorg ervoor dat de sequence voor de primary keys wordt bijgewerkt na handmatige inserts
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users), true);
SELECT setval('user_profiles_id_seq', (SELECT MAX(id) FROM user_profiles), true);
SELECT setval('user_devices_id_seq', (SELECT MAX(id) FROM user_devices), true);
SELECT setval('glucose_measurement_id_seq', (SELECT MAX(id) FROM glucose_measurement), true);
SELECT setval('user_service_connections_id_seq', (SELECT MAX(id) FROM user_service_connections), true);
