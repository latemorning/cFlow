create table egov_db.COMTCCMMNCODE
(
    CODE_ID           varchar(6)   not null comment '코드ID'
        primary key,
    CODE_ID_NM        varchar(60)  null comment '코드ID명',
    CODE_ID_DC        varchar(200) null comment '코드ID설명',
    USE_AT            char         null comment '사용여부',
    CL_CODE           char(3)      null comment '분류코드',
    FRST_REGIST_PNTTM datetime     null comment '최초등록시점',
    FRST_REGISTER_ID  varchar(20)  null comment '최초등록자ID',
    LAST_UPDT_PNTTM   datetime     null comment '최종수정시점',
    LAST_UPDUSR_ID    varchar(20)  null comment '최종수정자ID',
    constraint COMTCCMMNCODE_PK
        unique (CODE_ID),
    constraint comtccmmncode_ibfk_1
        foreign key (CL_CODE) references egov_db.COMTCCMMNCLCODE (CL_CODE)
)
    comment '공통코드';

create index COMTCCMMNCODE_i01
    on egov_db.COMTCCMMNCODE (CL_CODE);

