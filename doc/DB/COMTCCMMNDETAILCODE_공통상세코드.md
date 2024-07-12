create table egov_db.COMTCCMMNDETAILCODE
(
    CODE_ID           varchar(6)   not null comment '코드ID',
    CODE              varchar(15)  not null comment '코드',
    CODE_NM           varchar(60)  null comment '코드명',
    CODE_DC           varchar(200) null comment '코드설명',
    USE_AT            char         null comment '사용여부',
    FRST_REGIST_PNTTM datetime     null comment '최초등록시점',
    FRST_REGISTER_ID  varchar(20)  null comment '최초등록자ID',
    LAST_UPDT_PNTTM   datetime     null comment '최종수정시점',
    LAST_UPDUSR_ID    varchar(20)  null comment '최종수정자ID',
    primary key (CODE_ID, CODE),
    constraint COMTCCMMNDETAILCODE_PK
        unique (CODE_ID, CODE),
    constraint comtccmmndetailcode_ibfk_1
        foreign key (CODE_ID) references egov_db.COMTCCMMNCODE (CODE_ID)
)
    comment '공통상세코드';

create index COMTCCMMNDETAILCODE_i01
    on egov_db.COMTCCMMNDETAILCODE (CODE_ID);

