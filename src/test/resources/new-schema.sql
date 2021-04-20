DROP TABLE IF EXISTS otp_status_details;
DROP TABLE IF EXISTS email_otp;

CREATE TABLE email_otp (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	uuid VARCHAR(36) UNIQUE NOT NULL,
	code VARCHAR(6) NOT NULL,
	created_at BIGINT,
    duration_before_expiry BIGINT,
	email_address VARCHAR(50) NOT NULL,
    message_body VARCHAR(255) NOT NULL
);

CREATE TABLE otp_status_details (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	otp_uuid VARCHAR(36) UNIQUE NOT NULL,
	current_status VARCHAR(50),
    reason_phrase VARCHAR(255),
	total_attempts INTEGER,
	FOREIGN KEY(otp_uuid) REFERENCES email_otp(uuid)
);