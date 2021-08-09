ALTER TABLE positions ADD COLUMN article bigserial;

CREATE TABLE IF NOT EXISTS images(
    id uuid primary key default uuid_generate_v4(),
    description varchar(64) not null,
    format int not null
);

CREATE TABLE IF NOT EXISTS positions_images(
    position_id uuid not null,
    image_id uuid not null,
    CONSTRAINT positions_images_position_id FOREIGN KEY (position_id) REFERENCES positions(id),
    CONSTRAINT positions_images_image_id FOREIGN KEY (image_id) REFERENCES images(id)
);