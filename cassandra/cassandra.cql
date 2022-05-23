CREATE KEYSPACE IF NOT EXISTS desafio_2_keyspace
WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS desafio_2_keyspace.tb_conta (
  id_conta               uuid,
  id_pessoa              uuid,
  saldo                  double,
  limite_saque_diario    double,
  conta_ativa            boolean,
  tipo_conta             int,
  data_criacao           timestamp,
  PRIMARY KEY (id_conta)
);

CREATE TABLE IF NOT EXISTS desafio_2_keyspace.tb_transacao (
      id_conta               uuid,
      data_transacao         date,
      id_transacao           bigint,
      valor                  double,
      PRIMARY KEY ((id_conta), data_transacao, id_transacao)
);

CREATE TABLE IF NOT EXISTS desafio_2_keyspace.tb_pessoa(
      id_pessoa              uuid,
      nome                   text,
      cpf                    text,
      data_nascimento        date,
      PRIMARY KEY (id_pessoa)
);
