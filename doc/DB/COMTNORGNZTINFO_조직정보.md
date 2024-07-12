create table egov_db.COMTNORGNZTINFO
(
    ORGNZT_ID char(20) default '' not null comment '조직ID'
        primary key,
    ORGNZT_NM varchar(20)         not null comment '조직명',
    ORGNZT_DC varchar(100)        null comment '조직설명',
    constraint COMTNORGNZTINFO_PK
        unique (ORGNZT_ID)
)
    comment '조직정보';



-- 부서