create table egov_db.COMTNVCATNMANAGE
(
    APPLCNT_ID        varchar(20)   not null comment '신청인ID',
    VCATN_SE          char(2)       not null comment '휴가구분',
    BGNDE             char(8)       not null comment '시작일',
    ENDDE             char(8)       not null comment '종료일',
    VCATN_RESN        varchar(200)  not null comment '휴가사유',
    REQST_DE          char(20)      not null comment '신청일',
    OCCRRNC_YEAR      char(4)       null comment '발생연도',
    SANCTNER_ID       varchar(20)   null comment '결재자ID',
    CONFM_AT          char          null comment '승인여부',
    SANCTN_DT         datetime      null comment '결재일시',
    RETURN_RESN       varchar(1000) null comment '반환사유',
    INFRML_SANCTN_ID  char(20)      null comment '약식결재ID',
    FRST_REGISTER_ID  varchar(20)   null comment '최초등록자ID',
    FRST_REGIST_PNTTM datetime      null comment '최초등록시점',
    LAST_UPDUSR_ID    varchar(20)   null comment '최종수정자ID',
    LAST_UPDT_PNTTM   datetime      null comment '최종수정시점',
    NOON_SE           char          null comment '정오구분',
    primary key (APPLCNT_ID, VCATN_SE, BGNDE, ENDDE),
    constraint COMTNVCATNMANAGE_PK
        unique (APPLCNT_ID, VCATN_SE, BGNDE, ENDDE)
)
    comment '휴가관리';
