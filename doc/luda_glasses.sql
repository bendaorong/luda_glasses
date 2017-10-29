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


/*==============================================================*/
/* Table: materiel                                              */
/*==============================================================*/
DROP TABLE IF EXISTS materiel;
create table materiel
(
   id                   int(11)                        not null auto_increment,
   type_id              int(4)                         not null,
   code                 varchar(16)                    not null,
   bar_code             varchar(16)                    null,
   name                 varchar(128)                   null,
   sell_price           double(10,2)                   null,
   trade_price          double(10,2)                   null,
   cost_price           double(10,2)                   null,
   unit                 varchar(32)                    null,
   brand                varchar(32)                    null,
   specification        varchar(64)                    null,
   color                varchar(32)                   null,
   texture              varchar(32)                    null,
   manufacturer         varchar(128)                   null,
   min_inventory        int(11)                        null,
   max_inventory        int(11)                        null,
   use_flag             tinyint(1)                    null,
   remark               varchar(256)                   null,
   creator_user_id      int(11)                        null,
   create_time          datetime                       null,
   update_user_id       int(11)                        null,
   update_time          datetime                       null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX idx_code on materiel(code);


/*==============================================================*/
/* Table: goods_type                                              */
/*==============================================================*/
DROP TABLE IF EXISTS goods_type;
create table goods_type
(
   type_id              int(4)                         not null auto_increment,
   type_name            varchar(32)                   not null,
   kind_id              int(4)                         not null,
   delete_flag          tinyint(1)                      default 0,
  PRIMARY KEY (type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: dictionary                                              */
/*==============================================================*/
DROP TABLE IF EXISTS dictionary;
create table dictionary
(
   dict_id              int(4)                         not null auto_increment,
   dict_type            varchar(32)                   not null,
   dict_name            varchar(32)                   not null,
   delete_flag          tinyint(1)                      default 0,
  PRIMARY KEY (dict_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: goods_kind                                            */
/*==============================================================*/
DROP TABLE IF EXISTS goods_kind;
create table goods_kind
(
   kind_id              int(4)                         not null auto_increment,
   kind_name            varchar(32)                   not null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (kind_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into goods_kind (kind_name) values ('普通镜片'),('隐形眼镜'),('眼镜框架'),('护理液');

/*==============================================================*/
/* Table: goods_color                                           */
/*==============================================================*/
DROP TABLE IF EXISTS goods_color;
create table goods_color
(
   color_id              int(4)                         not null auto_increment,
   color_name            varchar(32)                   not null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (color_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: goods_unit                                           */
/*==============================================================*/
DROP TABLE IF EXISTS goods_unit;
create table goods_unit
(
   unit_id              int(4)                         not null auto_increment,
   unit_name            varchar(32)                   not null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (unit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: goods_unit                                           */
/*==============================================================*/
DROP TABLE IF EXISTS goods_brand;
create table goods_brand
(
   brand_id              int(4)                         not null auto_increment,
   brand_name            varchar(32)                   not null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (brand_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: MARD 商品库存表，与商品表一一对应                     */
/*==============================================================*/
DROP TABLE IF EXISTS mard;
create table mard
(
   id                   int(11)                        not null auto_increment,
   materiel_id          int(11)                        not null comment '商品id',
   store_id             int(11)                        not null comment '门店id',
   cur_inventory        int(11)                        not null comment '当前库存量',
   create_time          datetime                       null,
   update_time          datetime                       null,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table mard add unique index(materiel_id, store_id);

/*==============================================================*/
/* Table: purchase_order 采购入库单                             */
/*==============================================================*/
DROP TABLE IF EXISTS purchase_order;
create table purchase_order
(
   purchase_order_id    int(11)                        not null auto_increment,
   code                 varchar(16)                    not null comment '采购单编号',
   purchase_date        date                           not null comment '采购日期',
   store_id             int(11)                        not null comment '门店id',
   supplier_id          int(11)                        not null comment '供应商id',
   businessman_id       int(11)                        not null comment '业务员',
   total_quantity       int(11)                         not null comment '总数量 明细中的数量总计',
   total_amount          decimal(23,2)                  not null comment '总金额 明细中的金额总计',
   remark               varchar(256)                   null,
   create_user_id       int(11)                        null,
   create_time          datetime                       null,
   update_user_id       int(11)                        null,
   update_time          datetime                       null,
   delete_flag          tinyint(1)                    default 0 comment '删除标示 1:删除 0:不删除'
   PRIMARY KEY (purchase_order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: purchase_order_item 采购单明细                        */
/*==============================================================*/
DROP TABLE IF EXISTS purchase_order_item;
create table purchase_order_item
(
   item_id              int(11)                        not null auto_increment,
   purchase_order_id    int(11)                        not null comment '采购单id',
   materiel_id          int(11)                        not null comment '商品id',
   purchase_price       double(10,2)                   not null comment '采购价',
   purchase_quantity    int(11)                        not null comment '采购数量',
   remark               varchar(256)                   null,
   PRIMARY KEY (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: inventory_verification 库存盘点信息表                 */
/*==============================================================*/
DROP TABLE IF EXISTS inventory_verification;
create table inventory_verification
(
   id                   int(11)           not null auto_increment,
   code                 varchar(16)       not null comment '盘点编号',
   verif_date           DATE              not null comment '盘点日期',
   store_id             int(11)                     not   null,
   businessman_id       int(11)                     not   null,
   remark               varchar(256)                   null,
   create_user_id       int(11)                        null,
   create_time          datetime                       null,
   update_user_id       int(11)                        null,
   update_time          datetime                       null,
   delete_flag          tinyint(1)                     default 0,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: inventory_verification_item  库存盘点明细表           */
/*==============================================================*/
DROP TABLE IF EXISTS inventory_verification_item;
create table inventory_verification_item
(
   id                   int(11)                        not null auto_increment,
   inventory_verification_id int(11)                   not null,
   materiel_id          int(11)                        not null,
   quantity             int(11)                        not null,
   invnt_type           char(2)                        not null,
   remark               varchar(256)                   null,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: inventory_transfer 库存调拨信息表                     */
/*==============================================================*/
DROP TABLE IF EXISTS inventory_transfer;
create table inventory_transfer
(
   id                   int(11)                        not null auto_increment,
   code                 varchar(16)                    null,
   transfer_date        date                           null,
   out_store_id         int(11)                        null,
   in_store_id          int(11)                        null,
   businessman_id       int(11)                        null,
   remark               varchar(256)                   null,
   create_user_id       int(11)                        null,
   create_time          datetime                       null,
   update_user_id       int(11)                        null,
   update_time          datetime                       null,
   delete_flag          tinyint(1)                     default 0,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: inventory_transfer_item   调拨单明细表                */
/*==============================================================*/
DROP TABLE IF EXISTS inventory_transfer_item;
create table inventory_transfer_item
(
   id                   int(11)                        not null auto_increment,
   inventory_transfer_id int(11)                       not null,
   materiel_id          int(11)                        not null,
   quantity             int(11)                        not null,
   remark               varchar(256)                   null,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: customer   客户表                                     */
/*==============================================================*/
DROP TABLE IF EXISTS customer;
create table customer
(
   id                   int                            not null auto_increment,
   code                 varchar(16)                    not null,
   name                 varchar(32)                    not null,
   gender               char(1)                        null default 'M',
   birthday             date                           null,
   mobile_num           varchar(16)                    null,
   QQ                   varchar(16)                    null,
   wechat_num           varchar(32)                    null,
   email                varchar(64)                    null,
   region               varchar(32)                    null,
   region_name          varchar(64)                    null,
   address              varchar(128)                   null,
   delete_flag          tinyint(1)                      default 0,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX idx_mobile_number on customer(mobile_num);
CREATE UNIQUE INDEX idx_code on customer(code);

/*==============================================================*/
/* Table: sales_list 销售单                                     */
/*==============================================================*/
DROP TABLE IF EXISTS sales_order;
create table sales_order (
  id                    int(11)               not null auto_increment,
  code                  varchar(16)           not null,
  sale_date             date                  not null,
  store_id              int(11)               not null,
  businessman_id        int(11),
  customer_id           int(11)                not null,
  pick_up_date          DATE                  null,
  total_quantity        int(11) comment '总数量',
  total_amount          double(10,2) comment '总价',
  remark                varchar(512)          null,
  create_user_id       int(11)                        null,
  create_time          datetime                       null,
  update_user_id       int(11)                        null,
  update_time          datetime                       null,
  delete_flag          tinyint(1)                     default 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: sales_list 销售明细                                   */
/*==============================================================*/
DROP TABLE IF EXISTS sales_order_item;
create table sales_order_item (
  id int(11) not null auto_increment,
  sales_order_id int(11) not null comment '销售单Id',
  materiel_id int(11) not null comment '商品',
  quantity int(11) comment '数量',
  sell_price double(10,2) comment '售价',
  remark    varchar(256) comment '备注',
  PRIMARY KEY (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;