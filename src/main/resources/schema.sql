CREATE TABLE IF NOT EXISTS email_otp (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	uuid VARCHAR(36) UNIQUE NOT NULL,
	code VARCHAR(6) NOT NULL,
	created_at BIGINT,
    duration_before_expiry BIGINT,
	email_address VARCHAR(50) NOT NULL,
    message_body VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS otp_state_details (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	otp_uuid VARCHAR(36),
	current_state VARCHAR(10),
	reason_phrase VARCHAR(50),
	total_attempts INTEGER,
	FOREIGN KEY(otp_uuid) REFERENCES email_otp(uuid)
);