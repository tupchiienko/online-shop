ALTER TABLE positions_images
    DROP CONSTRAINT positions_images_image_id,
    DROP CONSTRAINT positions_images_position_id,
    ADD CONSTRAINT positions_images_image_id FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT positions_images_position_id FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE CASCADE ON UPDATE CASCADE;