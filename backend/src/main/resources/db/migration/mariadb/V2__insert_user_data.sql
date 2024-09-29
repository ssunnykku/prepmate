INSERT INTO users (user_id, name, email, password, created_at)
VALUES (UUID(), 'sun kim', 'sun@example.com', '1234', NOW());

UPDATE users
SET user_id = '28eadf23-dc55-49d7-8398-d5e215f177fd'
WHERE email = 'sun@example.com';
