CREATE TABLE IF NOT EXISTS categories(
    id uuid primary key default uuid_generate_v4(),
    name varchar(32) not null unique
);

CREATE TABLE IF NOT EXISTS positions(
    id uuid primary key default uuid_generate_v4(),
    name varchar(64) not null,
    price bigint not null,
    description varchar(5000) not null,
    availability int not null,
    category_id uuid not null,
    CONSTRAINT positions_position_category_fk FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS orders(
    id uuid primary key default uuid_generate_v4(),
    date date not null default CURRENT_DATE,
    order_number bigserial not null,
    order_price bigint not null,
    order_status int not null,
    user_id uuid not null,
    CONSTRAINT orders_order_userId_fk FOREIGN KEY (user_id) REFERENCES users_table(id)
);

CREATE TABLE IF NOT EXISTS order_items(
    id uuid primary key default uuid_generate_v4(),
    name varchar(64) not null,
    quantity int not null,
    price bigint not null
);

CREATE TABLE orders_items(
    order_id uuid not null,
    item_id uuid not null
);