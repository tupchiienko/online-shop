ALTER TABLE images
    DROP COLUMN description,
    ADD COLUMN name varchar NOT NULL;

