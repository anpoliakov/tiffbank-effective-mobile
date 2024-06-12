create sequence account_account_id_seq;

alter sequence account_account_id_seq owner to anpoliakov;

alter sequence account_account_id_seq owned by accounts.account_id;

