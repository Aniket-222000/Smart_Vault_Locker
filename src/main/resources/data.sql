-- Insert Admin user (plain text password "adminpass")
INSERT INTO users(username, password, role, email)
VALUES ('admin', 'adminpass', 'ADMIN', 'admin@bank.com');

-- Insert Customer user (plain text password "alicepass", fingerprint "fingerprint123")
INSERT INTO users(username, password, role, email, phone, fingerprint_hash)
VALUES ('alice', 'alicepass', 'CUSTOMER', 'alice@example.com', '1234567890', 'fingerprint123');

-- Insert Locker for customer alice (assuming her id becomes 2)
INSERT INTO lockers(locker_number, user_id)
VALUES ('LCK-100', 2);
