INSERT IGNORE INTO casa_madre (id, ragione_sociale, sede_legale, paese, anno_fondazione, fatturato_annuo) VALUES
(1, 'Auto Italia Group', 'Roma', 'Italia', 1950, 5000000),
(2, 'Global Motors', 'Berlino', 'Germania', 1970, 8500000),
(3, 'Japan Automotive', 'Tokyo', 'Giappone', 1960, 7200000),
(4, 'Electric Mobility', 'New York', 'USA', 2010, 3000000);

INSERT IGNORE INTO concessionario (id, nome, citta, fatturato, casa_madre_id) VALUES
(1, 'Auto Roma', 'Roma', 500000, 1),
(2, 'Auto Milano', 'Milano', 750000, 1),
(3, 'Motors Berlin', 'Berlino', 900000, 2),
(4, 'Motors Monaco', 'Monaco', 600000, 2),
(5, 'Tokyo Cars', 'Tokyo', 800000, 3),
(6, 'Tokyo Motors', 'Osaka', 400000, 3);

INSERT IGNORE INTO cliente (id, nome, cognome, email, data_registrazione, concessionario_id) VALUES
(1, 'Mario', 'Rossi', 'mario@email.it', NOW(), 1),
(2, 'Luca', 'Bianchi', 'luca@email.it', NOW(), 1),
(3, 'Anna', 'Verdi', 'anna@email.it', NOW(), 2),
(4, 'Paolo', 'Neri', 'paolo@email.it', NOW(), 3),
(5, 'Giulia', 'Gialli', 'giulia@email.it', NOW(), 3),
(6, 'Marco', 'Blu', 'marco@email.it', NOW(), 4),
(7, 'Sara', 'Rosa', 'sara@email.it', NOW(), 5),
(8, 'Andrea', 'Rossi', 'andrea@email.it', NOW(), 5),
(9, 'Francesca', 'Bianchi', 'francesca@email.it', NOW(), 6);
