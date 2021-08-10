ALTER TABLE positions
    ALTER COLUMN article TYPE varchar(12),
    ALTER COLUMN article DROP default,
    ALTER COLUMN article SET NOT NULL,
    ADD UNIQUE (article);

DROP SEQUENCE positions_article_seq;