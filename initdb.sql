create table customers
(
    id uuid not null
        constraint customers_pkey
            primary key,
    title varchar(255) not null,
    is_deleted boolean not null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    modified_at timestamp default CURRENT_TIMESTAMP
);

comment on table customers is 'Table with customer info';

comment on column customers.id is 'Customer ID';

comment on column customers.title is 'Customer title';

comment on column customers.is_deleted is 'Deletion flag';

comment on column customers.created_at is 'Creation timestamp';

comment on column customers.modified_at is 'Modification timestamp';

alter table customers owner to appsmart_user;

create index customers_index
    on customers (id);

create table products
(
    id uuid not null
        constraint products_pkey
            primary key,
    customers_id uuid
        constraint products_customers_id_fkey
            references customers,
    title varchar(255) not null,
    description varchar(1024),
    price numeric(10,2) not null,
    is_deleted boolean not null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    modified_at timestamp default CURRENT_TIMESTAMP
);

comment on table products is 'Table with product info';

comment on column products.id is 'Product ID';

comment on column products.customers_id is 'Customer ID';

comment on column products.title is 'Product title';

comment on column products.description is 'Product description';

comment on column products.price is 'Product price';

comment on column products.is_deleted is 'Deletion flag';

comment on column products.created_at is 'Creation timestamp';

comment on column products.modified_at is 'Modification timestamp';

alter table products owner to appsmart_user;

create index products_index
    on products (id);

create index products__customer_index
    on products (customers_id);

create table role_table
(
    id   serial      not null
        constraint role_table_pk
        primary key,
    name varchar(20) not null
);

alter table role_table
    owner to appsmart_user;

create table user_table
(
    id       serial not null
        constraint user_table_pk
        primary key,
    login    varchar(50),
    password varchar(500),
    role_id  integer
        constraint user_table_role_table_id_fk
        references role_table
);

alter table user_table
    owner to appsmart_user;

create unique index user_table_login_index
    on user_table (login);

insert into role_table(name) values ('ROLE_ADMIN');
insert into role_table(name) values ('ROLE_USER');



