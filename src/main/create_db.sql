# 四张表
create database javase_bank_system;
use javase_bank_system;
drop table if exists card, card_password, record, admin;

# 卡号表
create table card
(
    id          integer     primary key auto_increment,
    money       double      default 0.0,
    bank        varchar(80) not null,
    uid         long        not null,
    user_inform varchar(120),
    status      integer     default 1
);

insert into card(id, money, bank, uid, user_inform)
values (13128310, 150.0, '中国银行', 310105199909090009, '张三'),
       (12345678, 123.4, '工商银行', 310006198808080008, '李四'),
       (21839122, 1000, '工商银行', 310006198808080008, '李四'),
       (41239812, 0, '天地银行', 312106213808032008, '王五');

# 卡号密码表
create table card_password
(
    id          integer     primary key auto_increment,
    password    varchar(40) not null
);

insert into card_password(id, password)
values (13128310, '123456'),
       (12345678, '123456'),
       (21839122, 'qwerty'),
       (41239812, 'abcdef');

# 交易记录表
create table record
(
    id          integer     primary key auto_increment,
    from_id     integer     not null,
    to_id       integer     not null,
    money       double      not null,
    type        integer     not null,
    time        date        not null
);

insert into record(from_id, to_id, money, type, time)
values (41239812, 13128310, 150, 1, localtime),
       (0, 12345678.4, 123.4, 2, localtime);

# 管理员表
create table admin
(
    id          integer     primary key auto_increment,
    password    varchar(40) not null,
    status      integer     default 1
);

insert into admin(id, password)
values (1, '123'),
       (2, 'abc');
