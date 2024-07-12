create table egov_db.COMTCCMMNCLCODE
(
    CL_CODE           char(3)      not null comment '분류코드'
        primary key,
    CL_CODE_NM        varchar(60)  null comment '분류코드명',
    CL_CODE_DC        varchar(200) null comment '분류코드설명',
    USE_AT            char         null comment '사용여부',
    FRST_REGIST_PNTTM datetime     null comment '최초등록시점',
    FRST_REGISTER_ID  varchar(20)  null comment '최초등록자ID',
    LAST_UPDT_PNTTM   datetime     null comment '최종수정시점',
    LAST_UPDUSR_ID    varchar(20)  null comment '최종수정자ID',
    constraint COMTCCMMNCLCODE_PK
        unique (CL_CODE)
)
    comment '공통분류코드';

