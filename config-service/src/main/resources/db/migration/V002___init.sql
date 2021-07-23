create table users_table
(
    id         bigserial primary key not null,
    firstName  varchar(32)           not null,
    lastName   varchar(32)           not null,
    age        smallint              not null,
    phone      text                  not null,
    account_id bigint,
    constraint account_table_account_id_fk foreign key (account_id)
        references account_table (id)
        on delete cascade on update cascade
);

create table account_table
(
    id       bigserial primary key not null,
    username varchar(32)           not null,
    email    varchar(32)           not null,
    password varchar(32)           not null,
    role     text
);
