CREATE TABLE publisher (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE imprint (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  publisher_id BIGINT NOT NULL
);

ALTER TABLE imprint ADD CONSTRAINT imprint_fk1 FOREIGN KEY (publisher_id) REFERENCES publisher (id);

CREATE TABLE series (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  volume VARCHAR(10) DEFAULT NULL,
  begin_year INTEGER NOT NULL,
  end_year INTEGER DEFAULT NULL,
  publisher_id BIGINT NOT NULL,
  imprint_id BIGINT DEFAULT NULL
);

ALTER TABLE series ADD CONSTRAINT series_fk1 FOREIGN KEY (publisher_id) REFERENCES publisher (id);
ALTER TABLE series ADD CONSTRAINT series_fk2 FOREIGN KEY (imprint_id) REFERENCES imprint (id);

CREATE TABLE issue (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  number INTEGER NOT NULL,
  qualifier VARCHAR(10) DEFAULT NULL,
  release_date DATE DEFAULT NULL,
  barcode VARCHAR(17) DEFAULT NULL,
  previews_code VARCHAR(9) DEFAULT NULL
);

CREATE TABLE variant (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

INSERT INTO publisher (id, name) VALUES (1, 'Marvel Comics');

INSERT INTO series (id, name, volume, begin_year, end_year, publisher_id, imprint_id) VALUES (1, 'The Amazing Spider-Man', '5', 2018, NULL, 1, NULL);

INSERT INTO issue (id, number, qualifier, release_date, barcode, previews_code) VALUES (1, 16, NULL, '2019-02-27', '75960608936901611', 'DEC180953');
INSERT INTO issue (id, number, qualifier, release_date, barcode, previews_code) VALUES (2, 16, '.HU', '2019-03-06', '75960609406600111', 'JAN190900');
