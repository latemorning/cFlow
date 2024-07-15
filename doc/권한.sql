-- 권한관리
SELECT AUTHOR_CODE, AUTHOR_NM, AUTHOR_DC, AUTHOR_CREAT_DE FROM COMTNAUTHORINFO WHERE 1=1 ORDER
BY AUTHOR_CREAT_DE DESC LIMIT 10 OFFSET 0;
/*
IS_AUTHENTICATED_ANONYMOUSLY,   스프링시큐리티 내부사용(롤부여 금지)
IS_AUTHENTICATED_FULLY,         스프링시큐리티 내부사용(롤부여 금지)
IS_AUTHENTICATED_REMEMBERED,    스프링시큐리티 내부사용(롤부여 금지)
ROLE_ADMIN,                     관리자
ROLE_ANONYMOUS,                 익명 사용자
ROLE_USER,                      일반 사용자
 */


-- 권한그룹관리
SELECT A.USER_ID, A.USER_NM, A.GROUP_ID, A.MBER_TY_CODE, (SELECT CODE_NM FROM COMTCCMMNDETAILCODE
WHERE CODE_ID = 'COM012' AND CODE = A.MBER_TY_CODE AND USE_AT = 'Y') AS MBER_TY_NM, B.AUTHOR_CODE,
(CASE WHEN B.SCRTY_DTRMN_TRGET_ID IS NULL THEN 'N' ELSE 'Y' END) AS REG_YN, ESNTL_ID FROM (SELECT
MBER_ID USER_ID, MBER_NM USER_NM, GROUP_ID, 'USR01' MBER_TY_CODE, ESNTL_ID FROM COMTNGNRLMBER
UNION ALL SELECT ENTRPRS_MBER_ID USER_ID, CMPNY_NM USER_NM, GROUP_ID, 'USR02' MBER_TY_CODE,
ESNTL_ID FROM COMTNENTRPRSMBER UNION ALL SELECT EMPLYR_ID USER_ID, USER_NM USER_NM, GROUP_ID,
'USR03' MBER_TY_CODE, ESNTL_ID FROM COMTNEMPLYRINFO ) A LEFT OUTER JOIN COMTNEMPLYRSCRTYESTBS
B ON A.ESNTL_ID = B.SCRTY_DTRMN_TRGET_ID WHERE 1 = 1 LIMIT 10 OFFSET 0;
/*
USER,       일반회원,   GROUP_00000000000000,   USR01, 일반 회원 유형,            ROLE_USER,  USRCNFRM_00000000001
ENTERPRISE, NIA,        GROUP_00000000000000,   USR02, 기업 회원 유형,            ROLE_USER,  USRCNFRM_00000000002
TEST1,      테스트1,    GROUP_00000000000000,   USR03, 업무 담당자(사용자) 유형,  ROLE_ADMIN, USRCNFRM_00000000000
webmaster,  웹마스터,   GROUP_00000000000000,   USR03, 업무 담당자(사용자) 유형,  ROLE_ADMIN, USRCNFRM_99999999999
 */


-- 그룹관리
SELECT GROUP_ID, GROUP_NM, GROUP_DC, GROUP_CREAT_DE FROM COMTNAUTHORGROUPINFO WHERE 1=1 ORDER
BY GROUP_CREAT_DE DESC LIMIT 10 OFFSET 0;
/*
GROUP_00000000000000,   0번  그룹입니다,  0번  그룹입니다,  2024-07-12 14:08:39
 */


-- 롤관리
SELECT ROLE_CODE, ROLE_NM, ROLE_PTTRN, ROLE_DC, (SELECT CODE_NM FROM COMTCCMMNDETAILCODE WHERE
CODE_ID = 'COM029' AND CODE = ROLE_TY) AS ROLE_TY, ROLE_SORT, ROLE_CREAT_DE FROM COMTNROLEINFO
WHERE 1=1 ORDER BY ROLE_CREAT_DE DESC;
/*
web-000001, 로그인롤,      \A/uat/uia/.*\.do.*\Z,       로그인허용을 위한 롤,                   URL,     1
web-000002, 좌측메뉴,      /EgovLeft.do,                좌측 메뉴에 대한 접근 제한 롤,          URL,     2
web-000003, 모든접근제한,  \A/.*\.do.*\Z,               모든자원에 대한 접근 제한 롤,           URL,     3
web-000004, 회원관리,      \A/uss/umt/.*\.do.*\Z,       회원관리에 대한 접근 제한 롤,           URL,     1
web-000005, 실명확인,      \A/sec/rnc/.*\.do.*\Z,       실명확인에 대한 접근 제한 롤,           URL,     1
web-000006, 우편번호,      \A/sym/ccm/zip/.*\.do.*\Z,   우편번호관리에 대한 접근 제한 롤,       URL,     1
web-000007, 로그인이미지,  \A/uss/ion/lsi/.*\.do.*\Z,   로그인이미지관리에 대한 접근 제한 롤,   URL,     1
web-000008, 파일다운로드,  /cmm/fms/FileDown.do.*,      파일다운로드에 대한 접근 제한 롤,       URL,     1
web-000009, 상단메뉴,      /EgovTop.do,                 상단메뉴에 대한 접근 제한 롤,           URL,     1
web-000010, 하단메뉴,      /EgovBottom.do,              하단메뉴에 대한 접근 제한 롤,           URL,     1
web-000011, 왼쪽메뉴,      /EgovLeft.do,                왼쪽메뉴에 대한 접근 제한 롤,           URL,     1
web-000012, Validator모듈, /validator.do,               Validator에 대한 접근 제한 롤,          URL,     1
 */


/*
 1. 업무사용자관리 - 업무 사용자 추가
 2. 권한그룹관리에서 - 권한 등록
 3. 업무사용자관리 - 회원가입승인상태 업데이트

 1. 프로그램관리 - 등록
 2. 메뉴관리 - 등록
 3. 메뉴생성관리 - 생성
 */

-- 권한등록
 INSERT INTO COMTNEMPLYRSCRTYESTBS ( SCRTY_DTRMN_TRGET_ID , MBER_TY_CODE , AUTHOR_CODE) VALUES
( 'USRCNFRM_00000000003' , 'USR03' , 'ROLE_ADMIN');

