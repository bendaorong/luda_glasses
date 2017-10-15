/*==============================================================*/
/* Table: admin_user                                            */
/*==============================================================*/
DROP TABLE IF EXISTS admin_user;
create table admin_user
(
   admin_user_id        int(11)                        not null auto_increment,
   admin_code           varchar(16)                    not null,
   admin_name           varchar(32)                    not null,
   mobile_number        varchar(16)                    not null,
   login_password       varchar(32)                    not null,
   store_id             int                            not null,
   role_id              int                            not null,
   active_flag          tinyint(2)                     not null default 1,
   delete_flag          tinyint(1)                      not null default 0,
   PRIMARY KEY (admin_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX idx_mobile_number on admin_user(mobile_number);



/*==============================================================*/
/* Table: admin_user_detail                                     */
/*==============================================================*/
DROP TABLE IF EXISTS admin_user_detail;
create table admin_user_detail
(
   admin_user_id        int(11)                        not null,
   real_name            varchar(64)                    null,
   gender               char(1)                        null,
   email                varchar(64)                    null,
   id_no                varchar(32)                    null,
   native_place         varchar(32)                    null,
   address              varchar(128)                   null,
   postcode             varchar(16)                    null,
   create_datetime      datetime                       null,
   update_datetime      datetime                       null,
   position             varchar(32)                   null,
   onboard_date         date                           null,
   dimission_date       date                           null,
   PRIMARY KEY (admin_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into admin_user
(admin_code, admin_name, mobile_number, login_password, store_id, role_id, active_flag)
values
('0001', 'admin', '13402176163', '96E79218965EB72C92A549DD5A330112', 1, 1, 1);

insert into admin_user_detail
(admin_user_id, real_name, gender, email, id_no, native_place, address, postcode, create_datetime, update_datetime, position, onboard_date, dimission_date)
values
(1, 'admin_r', 'M', 'admin@163.com', '320981201012010010', '江苏', '苏州市吴中区幸福路1号', '215100', now(), now(), '01', '2017-10-01', null);




/*==============================================================*/
/* Table: admin_role                                            */
/*==============================================================*/
DROP TABLE IF EXISTS admin_role;
create table admin_role
(
   role_id        		int(11)                        	not null auto_increment,
   role_code			char(2)							not null,
   role_name           	varchar(16)                   	not null,
   PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into admin_role (role_code, role_name) values ('00', '超级管理员');
insert into admin_role (role_code, role_name) values ('01', '总经理');
insert into admin_role (role_code, role_name) values ('02', '经理');
insert into admin_role (role_code, role_name) values ('03', '验光师');
insert into admin_role (role_code, role_name) values ('04', '业务员');



/*==============================================================*/
/* Table: store                                                 */
/*==============================================================*/
DROP TABLE IF EXISTS store;
create table store
(
   store_id             int                            not null auto_increment,
   store_code           varchar(16)                    not null,
   store_name           varchar(64)                    not null,
   contact_person       varchar(64)                    not null,
   contact_phone        varchar(32)                    not null,
   fax_phone            varchar(32)                    null,
   store_address        varchar(128)                   not null,
   qq_number            varchar(16)                    null,
   head_office_flag     char(1)                        null default 'N',
   remark               varchar(256)                   null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX idx_store_code on store(store_code);


/*==============================================================*/
/* Table: supplier                                              */
/*==============================================================*/
DROP TABLE IF EXISTS supplier;
create table supplier
(
   supplier_id          int(11)                        not null auto_increment,
   supplier_code        varchar(16)                    not null,
   supplier_name        varchar(128)                   not null,
   bank                 varchar(64)                    null,
   bank_account         varchar(64)                    null,
   reg_tax_no           varchar(64)                    null,
   contact_person       varchar(32)                    null,
   contact_phone        varchar(32)                    null,
   use_flag             tinyint(1)                    not null default 1,
   remark               varchar(256)                   null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (supplier_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX idx_supplier_code on supplier(supplier_code);

/*==============================================================*/
/* Table: supplier_contact                                      */
/*==============================================================*/
DROP TABLE IF EXISTS supplier_contact;
create table supplier_contact
(
   contact_id           int(11)                        not null auto_increment,
   supplier_id          int(11)                        not null,
   contact_name         varchar(32)                    not null,
   gender               char(1)                        null,
   mobile_number        varchar(16)                    null,
   tel_number           varchar(32)                    null,
   address              varchar(128)                   null,
   email                varchar(64)                    null,
   post_code            varchar(16)                    null,
   head_flag            char(1)                        not null default 'N',
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (contact_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;