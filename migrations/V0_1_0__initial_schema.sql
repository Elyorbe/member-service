CREATE TABLE IF NOT EXISTS member (
      id INT NOT NULL AUTO_INCREMENT COMMENT '회원 번호',
      username VARCHAR(256) NOT NULL COMMENT '아이디',
      password VARCHAR(256) NOT NULL COMMENT '비밀번호',
      email VARCHAR(256) NOT NULL COMMENT '이메일',
      phone_number VARCHAR(256) NOT NULL COMMENT '휴대폰번호',
      created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (id),
      UNIQUE(username)
);
