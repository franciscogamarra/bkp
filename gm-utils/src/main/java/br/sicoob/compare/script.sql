alter table bnd.tipoPeriodicidade add codBndes varchar(10);
update bnd.tipoPeriodicidade set codBndes = '1001' where codTipoPeriodicidade = 15;
update bnd.tipoPeriodicidade set codBndes = '1003' where codTipoPeriodicidade = 13;
update bnd.tipoPeriodicidade set codBndes = '1005' where codTipoPeriodicidade = 12;
update bnd.tipoPeriodicidade set codBndes = '1006' where codTipoPeriodicidade = 11;
insert into bnd.tipoPeriodicidade(codTipoPeriodicidade, descTipoPeriodicidade, codBndes) values (99, 'Sem periodicidade', '1008');