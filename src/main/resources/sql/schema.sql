-- Iterasjon 1

-- Oppretter ansatt tabell
CREATE TABLE ansatt (
                        id SERIAL PRIMARY KEY,
                        brukernavn VARCHAR(4) UNIQUE NOT NULL,
                        fornavn VARCHAR(50) NOT NULL,
                        etternavn VARCHAR(50) NOT NULL,
                        ansettelsesdato DATE NOT NULL,
                        stilling VARCHAR(50) NOT NULL,
                        maanedslonn DECIMAL(10, 2) NOT NULL
);

-- Oppretter ansatte
INSERT INTO ansatt (brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES
    ('lph', 'Lars-Petter', 'Helland', '2025-01-01', 'Professor', 75000.00),
    ('abc', 'Anna', 'Bakken', '2024-03-15', 'Utvikler', 62000.00),
    ('def', 'Daniel', 'Eriksen', '2023-07-22', 'Designer', 58000.00),
    ('ghi', 'Gunnar', 'Hansen', '2022-11-01', 'Konsulent', 71000.00),
    ('jkl', 'Johanne', 'Kleiven', '2021-09-30', 'Analytiker', 65000.00),
    ('mno', 'Marte', 'Nordli', '2020-05-12', 'Teamleder', 69000.00),
    ('pqr', 'Per', 'Qvenild', '2024-08-14', 'Systemarkitekt', 73000.00),
    ('stu', 'Sofie', 'Thorsen', '2023-01-10', 'Prosjektleder', 67000.00),
    ('vwx', 'Vegard', 'Wang', '2022-06-25', 'Support', 54000.00),
    ('yza', 'Yngve', 'Zachariassen', '2021-12-05', 'Sjef', 85000.00),
    ('bcd', 'Beate', 'Christiansen', '2020-04-18', 'Koordinator', 59000.00),
    ('efg', 'Erik', 'Fossum', '2024-02-13', 'Utvikler', 63000.00),
    ('hij', 'Hilde', 'Johansen', '2023-09-07', 'Designer', 60000.00),
    ('klm', 'Knut', 'Larsen', '2022-10-30', 'Konsulent', 70000.00),
    ('nop', 'Nina', 'Olsen', '2021-07-19', 'Analytiker', 64000.00),
    ('qrs', 'Quentin', 'Rasmussen', '2020-11-11', 'Teamleder', 68000.00),
    ('tuv', 'Tina', 'Sørensen', '2024-04-28', 'Systemarkitekt', 74000.00),
    ('wxy', 'William', 'Xandersen', '2023-05-03', 'Prosjektleder', 72000.00),
    ('zab', 'Zara', 'Bergen', '2022-02-14', 'Support', 56000.00),
    ('cde', 'Carl', 'Dahl', '2021-08-29', 'Sjef', 83000.00);

-- Iterasjon 3

-- Oppretter tabell for avdeling
CREATE TABLE avdeling (
                          id SERIAL PRIMARY KEY,
                          navn VARCHAR(50) NOT NULL,
                          sjef_id INTEGER NOT NULL REFERENCES ansatt(id)
);

-- Legger til avdeling_id kolonnen (midlertidig nullable)
ALTER TABLE ansatt ADD COLUMN avdeling_id INTEGER;

-- Setter inn avdelinger med midlertidige sjef-IDer
INSERT INTO avdeling (navn, sjef_id) VALUES
                                         ('IT', 1),       -- Sjef: Lars-Petter Helland
                                         ('HR', 6),       -- Sjef: Marte Nordli
                                         ('Marked', 11),  -- Sjef: Beate Christiansen
                                         ('Support', 20); -- Sjef: Carl Dahl

-- Oppdater ansattes avdelingstilhørighet
UPDATE ansatt SET avdeling_id = 1 WHERE id BETWEEN 1 AND 5;    -- IT-avdelingen
UPDATE ansatt SET avdeling_id = 2 WHERE id BETWEEN 6 AND 10;   -- HR-avdelingen
UPDATE ansatt SET avdeling_id = 3 WHERE id BETWEEN 11 AND 15;  -- Markedsavdelingen
UPDATE ansatt SET avdeling_id = 4 WHERE id BETWEEN 16 AND 20;  -- Supportavdelingen

-- Gjør avdeling_id obligatorisk
ALTER TABLE ansatt ALTER COLUMN avdeling_id SET NOT NULL;

-- Legger til fremmednøkkelbegrensning
ALTER TABLE ansatt
    ADD CONSTRAINT fk_avdeling
        FOREIGN KEY (avdeling_id)
            REFERENCES avdeling(id);

-- Iterasjon 5

CREATE TABLE prosjekt (
                          id SERIAL PRIMARY KEY,
                          navn VARCHAR(50) NOT NULL,
                          beskrivelse TEXT
);

CREATE TABLE prosjektdeltagelse (
                                    id SERIAL PRIMARY KEY,
                                    ansatt_id INTEGER REFERENCES ansatt(id) ON DELETE RESTRICT,
                                    prosjekt_id INTEGER REFERENCES prosjekt(id) ON DELETE RESTRICT,
                                    rolle VARCHAR(50),
                                    timer INTEGER DEFAULT 0
);

-- 1. Legger til 10 prosjekter
INSERT INTO prosjekt (navn, beskrivelse) VALUES
                                             ('Digital Plattformutvikling', 'Utvikling av en ny digital plattform for kundeengasjement'),
                                             ('App Modernisering', 'Modernisering av eksisterende mobilapplikasjon'),
                                             ('Kundeportal 2.0', 'Utvikling av ny kundeportal med forbedret UX'),
                                             ('AI Analyseverktøy', 'Bygging av AI-drevet dataanalyseverktøy'),
                                             ('Sikkerhetsoppgradering', 'Oppgradering av systemers sikkerhet mot cybertrusler'),
                                             ('Skyløsning Migrering', 'Migrering av on-premises systemer til skyen'),
                                             ('IoT Enhetssystem', 'Utvikling av styringssystem for IoT-enheter'),
                                             ('Markedsplass Plattform', 'Bygging av B2B markedsplass for bedrifter'),
                                             ('Internt Kommunikasjonsverktøy', 'Utvikling av chat og prosjektverktøy for ansatte'),
                                             ('Automatiserte Rapporter', 'Automatisering av månedlige driftsrapporter');

-- 2. Legger til 50 prosjektdeltagelser (ansatt 1-20, prosjekt 1-10)
-- Maks 3 prosjekter per ansatt og varierte roller/timer
INSERT INTO prosjektdeltagelse (ansatt_id, prosjekt_id, rolle, timer) VALUES
-- Prosjekt 1 (6 deltagere)
(1, 1, 'Prosjektleder', 120),
(3, 1, 'Frontend-utvikler', 80),
(5, 1, 'Backend-utvikler', 95),
(7, 1, 'UX-designer', 60),
(9, 1, 'Tester', 45),
(11, 1, 'DevOps', 75),

-- Prosjekt 2 (5 deltagere)
(2, 2, 'Scrum Master', 110),
(4, 2, 'Fullstack-utvikler', 85),
(6, 2, 'Database-arkitekt', 90),
(8, 2, 'Sikkerhetsspesialist', 70),
(10, 2, 'Teknisk dokumentasjon', 50),

-- Prosjekt 3 (5 deltagere)
(12, 3, 'Backend-utvikler', 100),
(14, 3, 'Frontend-utvikler', 75),
(16, 3, 'Prosjektleder', 130),
(18, 3, 'Cloud Engineer', 65),
(20, 3, 'Tester', 55),

-- Prosjekt 4 (5 deltagere)
(1, 4, 'Data Scientist', 150),
(3, 4, 'Machine Learning Engineer', 120),
(5, 4, 'Prosjektleder', 95),
(7, 4, 'AI-etikk konsulent', 40),
(9, 4, 'DevOps', 80),

-- Prosjekt 5 (5 deltagere)
(2, 5, 'Sikkerhetsarkitekt', 200),
(4, 5, 'Penetration Tester', 180),
(6, 5, 'Nettverksingeniør', 160),
(8, 5, 'Compliance Officer', 90),
(10, 5, 'Prosjektleder', 110),

-- Prosjekt 6 (5 deltagere)
(11, 6, 'Cloud Architect', 140),
(13, 6, 'Systemadministrator', 120),
(15, 6, 'Database Administrator', 100),
(17, 6, 'Prosjektleder', 130),
(19, 6, 'Sikkerhetsspesialist', 85),

-- Prosjekt 7 (5 deltagere)
(12, 7, 'Embedded Systems Engineer', 170),
(14, 7, 'IoT-utvikler', 150),
(16, 7, 'Prosjektleder', 110),
(18, 7, 'Hardware-spesialist', 95),
(20, 7, 'Tester', 75),

-- Prosjekt 8 (5 deltagere)
(2, 8, 'Fullstack-utvikler', 130),
(4, 8, 'Frontend-utvikler', 115),
(7, 8, 'Backend-utvikler', 125),
(10, 8, 'Prosjektleder', 140),
(13, 8, 'UX-designer', 90),

-- Prosjekt 9 (5 deltagere)
(5, 9, 'Backend-utvikler', 110),
(8, 9, 'Prosjektleder', 120),
(11, 9, 'Frontend-utvikler', 95),
(14, 9, 'Tester', 80),

-- Prosjekt 10 (4 deltagere)
(3, 10, 'Automasjonsingeniør', 160),
(6, 10, 'Data Analyst', 140),
(9, 10, 'Prosjektleder', 130),
(15, 10, 'DevOps', 110);