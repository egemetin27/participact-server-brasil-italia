-- auto-generated definition
create table ckan_info_bairro
(
  id           serial                   not null
    constraint ckan_info_bairro_pkey
    primary key,
  nm_bairro    varchar(100),
  nm_codade    varchar(100),
  geom_polygon geometry(Polygon, 29192) not null,
  creationdate timestamp                not null,
  updatedate   timestamp                not null,
  removed      integer default 0
);

comment on table ckan_info_bairro
is 'Tabela de Bairros Mapeados';

create unique index ckan_info_bairro_id_uindex
  on ckan_info_bairro (id);

