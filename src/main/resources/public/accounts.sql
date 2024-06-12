create table accounts
(
    account_id integer generated by default as identity
        constraint account_pkey
            primary key,
    user_id    integer        not null
        constraint account_user_id_fkey
            references users
            on delete set null,
    balance    numeric(19, 2) not null
        constraint account_balance_check
            check (balance >= (0)::numeric)
);

alter table accounts
    owner to anpoliakov;
