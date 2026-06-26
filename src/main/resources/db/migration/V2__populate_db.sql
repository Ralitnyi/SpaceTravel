-- Insert planets
INSERT INTO planet (id, name) VALUES 
('MARS', 'Mars - The Red Planet'),
('VEN', 'Venus - The Morning Star'),
('EAR', 'Earth - Our Home'),
('JUP', 'Jupiter - The Gas Giant'),
('SAT', 'Saturn - The Ringed Planet');

-- Insert clients
INSERT INTO client (name) VALUES 
('John Smith'),
('Jane Doe'),
('Alice Johnson'),
('Bob Williams'),
('Charlie Brown'),
('Diana Prince'),
('Edward Stark'),
('Fiona Green'),
('George Wilson'),
('Hannah Davis');

-- Insert tickets
INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES 
('2024-01-15 10:30:00', 1, 'EAR', 'MARS'),
('2024-02-20 14:45:00', 2, 'MARS', 'VEN'),
('2024-03-10 08:15:00', 3, 'VEN', 'EAR'),
('2024-04-05 16:20:00', 4, 'EAR', 'JUP'),
('2024-05-12 11:00:00', 5, 'JUP', 'SAT'),
('2024-06-18 09:30:00', 6, 'SAT', 'EAR'),
('2024-07-22 13:45:00', 7, 'EAR', 'VEN'),
('2024-08-30 15:10:00', 8, 'MARS', 'JUP'),
('2024-09-14 12:00:00', 9, 'VEN', 'SAT'),
('2024-10-25 10:20:00', 10, 'SAT', 'MARS');
