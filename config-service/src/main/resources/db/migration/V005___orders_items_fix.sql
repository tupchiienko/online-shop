ALTER TABLE orders_items
    ADD CONSTRAINT orders_items_order_id_fk FOREIGN KEY (order_id) REFERENCES orders(id),
    ADD CONSTRAINT orders_items_item_id_fk FOREIGN KEY (item_id) REFERENCES order_items(id);
