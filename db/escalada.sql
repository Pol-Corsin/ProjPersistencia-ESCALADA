-- ======================================================
-- CONFIGURACIÓN INICIAL
-- ======================================================
PRAGMA foreign_keys = ON;

-- ======================================================
-- 1. TABLA POBLACIO
-- ======================================================
CREATE TABLE IF NOT EXISTS Poblacio (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT UNIQUE NOT NULL
);

-- ======================================================
-- 2. TABLA ESCOLA
-- ======================================================
CREATE TABLE IF NOT EXISTS Escola (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT UNIQUE NOT NULL,
    aproximacio TEXT,
    popularitat TEXT CHECK (popularitat IN ('baixa', 'mitjana', 'alta')),
    restriccions TEXT
    -- Se elimina num_vies: se calcula con un COUNT(*) en Java/SQL
);

-- ======================================================
-- 3. TABLA INTERMEDIA ESCOLA_POBLACIO
-- ======================================================
CREATE TABLE IF NOT EXISTS Escola_Poblacio (
    escola_id INTEGER,
    poblacio_id INTEGER,
    PRIMARY KEY (escola_id, poblacio_id),
    FOREIGN KEY (escola_id) REFERENCES Escola (id) ON DELETE CASCADE,
    FOREIGN KEY (poblacio_id) REFERENCES Poblacio (id) ON DELETE CASCADE
);

-- ======================================================
-- 4. TABLA SECTOR
-- ======================================================
CREATE TABLE IF NOT EXISTS Sector (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    escola_id INTEGER NOT NULL,
    nom TEXT NOT NULL,
    coordenades TEXT,
    aproximacio TEXT,
    popularitat TEXT CHECK (popularitat IN ('baixa', 'mitjana', 'alta')),
    restriccions TEXT,
    FOREIGN KEY (escola_id) REFERENCES Escola (id) ON DELETE CASCADE,
    UNIQUE (escola_id, nom)
    -- Se elimina num_vies: se calcula dinámicamente
);

-- ======================================================
-- 5. TABLA ESCALADOR
-- ======================================================
CREATE TABLE IF NOT EXISTS Escalador (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    alias TEXT UNIQUE NOT NULL,
    nom TEXT NOT NULL,
    edat INTEGER,
    estil_pref TEXT CHECK (estil_pref IN ('esportiva', 'clàssica', 'gel'))
    -- Se elimina nivell_max: se calcula desde la tabla Assoliments
);

-- ======================================================
-- 6. TABLA VIA
-- ======================================================
CREATE TABLE IF NOT EXISTS Via (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    sector_id INTEGER NOT NULL,
    creador_id INTEGER NOT NULL,
    nom TEXT NOT NULL,
    tipus TEXT CHECK (tipus IN ('esportiva', 'clàssica', 'gel')),
    estat TEXT CHECK (estat IN ('Apte', 'construcció', 'tancada')),
    data_reobertura DATE, 
    roca TEXT CHECK (roca IN ('conglomerat', 'granit', 'calcaria', 'arenisca', 'altres')),
    ancoratge TEXT, 
    orientacio TEXT, 
    restriccions TEXT,
    FOREIGN KEY (sector_id) REFERENCES Sector (id) ON DELETE CASCADE,
    FOREIGN KEY (creador_id) REFERENCES Escalador (id) ON UPDATE CASCADE
);

-- ======================================================
-- 7. TABLA LLARG
-- ======================================================
CREATE TABLE IF NOT EXISTS Llarg (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    via_id INTEGER NOT NULL,
    numero_llarg INTEGER NOT NULL,
    llargada REAL NOT NULL,
    grau TEXT NOT NULL,
    FOREIGN KEY (via_id) REFERENCES Via (id) ON DELETE CASCADE
);

-- ======================================================
-- 8. TABLA ASSOLIMENTS
-- ======================================================
CREATE TABLE IF NOT EXISTS Assoliments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    escalador_alias TEXT NOT NULL,
    via_id INTEGER NOT NULL,
    data_completat DATE DEFAULT (date('now')), 
    grau_assolit TEXT NOT NULL, 
    FOREIGN KEY (escalador_alias) REFERENCES Escalador (alias) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (via_id) REFERENCES Via (id) ON DELETE CASCADE
);