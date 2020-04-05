/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     08/04/2019 13:04:52                          */
/*==============================================================*/


drop table if exists JOGADA;

drop table if exists JOGADOR;

drop table if exists JOGO;

drop table if exists LOGS;

drop table if exists NOTIFICACAO;

drop table if exists UTILIZADOR;

drop table if exists UTILIZADOR_NOTIFICACAO;

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR
(
   U_ID                 int not null auto_increment,
   U_NAME               varchar(35) not null,
   U_LOGIN              varchar(35) not null,
   U_PASSWORD           varchar(35) not null,
   U_ESTADO             bool not null,
   U_ESTADOREPROVACAO   bool not null,
   U_EMAIL              varchar(35) not null,
   U_TIPO               char(1) not null,
   primary key (U_ID)
);

/*==============================================================*/
/* Table: JOGO                                                  */
/*==============================================================*/
create table JOGO
(
   JO_ID                int not null auto_increment,
   U_ID                 int not null,
   JO_DATA              timestamp not null,
   primary key (JO_ID),
   constraint FK_UTILIZADOR_JOGO foreign key (U_ID)
      references UTILIZADOR (U_ID) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: JOGADA                                                */
/*==============================================================*/
create table JOGADA
(
   JOG_ID               int not null auto_increment,
   JO_ID                int not null,
   JOG_ACCAO            varchar(40) not null,
   primary key (JOG_ID),
   constraint FK_JOGO_JOGADA foreign key (JO_ID)
      references JOGO (JO_ID) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: JOGADOR                                               */
/*==============================================================*/
create table JOGADOR
(
   U_ID                 int not null,
   J_NUMJOGOS           int not null,
   J_NUMVITORIAS        int not null,
   J_TEMPOTOTAL         time not null,
   primary key (U_ID),
   constraint FK_UTILIZADOR_JOGADOR foreign key (U_ID)
      references UTILIZADOR (U_ID) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: LOGS                                                  */
/*==============================================================*/
create table LOGS
(
   L_ID_LOG             int not null auto_increment,
   L_DATA_LOG           date not null,
   L_HORA               time not null,
   L_UTILIZADOR         varchar(35) not null,
   L_ACCAO              varchar(100) not null,
   primary key (L_ID_LOG)
);

/*==============================================================*/
/* Table: NOTIFICACAO                                           */
/*==============================================================*/
create table NOTIFICACAO
(
   N_ID                 int not null auto_increment,
   N_MENSAGEM           varchar(150) not null,
   primary key (N_ID)
);

/*==============================================================*/
/* Table: UTILIZADOR_NOTIFICACAO                                */
/*==============================================================*/
create table UTILIZADOR_NOTIFICACAO
(
   U_ID                 int not null,
   N_ID                 int not null,
   primary key (U_ID, N_ID),
   constraint FK_UTILIZADOR_NOTIFICACAO foreign key (U_ID)
      references UTILIZADOR (U_ID) on delete restrict on update restrict,
   constraint FK_UTILIZADOR_NOTIFICACAO2 foreign key (N_ID)
      references NOTIFICACAO (N_ID) on delete restrict on update restrict
);

