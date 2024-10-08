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


-- 권한등록
 INSERT INTO COMTNEMPLYRSCRTYESTBS ( SCRTY_DTRMN_TRGET_ID , MBER_TY_CODE , AUTHOR_CODE) VALUES
( 'USRCNFRM_00000000003' , 'USR03' , 'ROLE_ADMIN');


-- 권한 구조 테이블
-- https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte:fdl:server_security:architecture

-- https://www.egovframe.go.kr/wiki/doku.php?id=egovframework:%EA%B6%8C%ED%95%9C%EA%B4%80%EB%A6%AC
-- 권한(롤) 상속 구조

SELECT
    A.PARNTS_ROLE PARENT,
    C.AUTHOR_NM,
    A.CHLDRN_ROLE CHILD,
       D.AUTHOR_NM
  FROM COMTNROLES_HIERARCHY A LEFT JOIN COMTNROLES_HIERARCHY B ON (A.CHLDRN_ROLE = B.PARNTS_ROLE)
       LEFT JOIN COMTNAUTHORINFO C ON A.PARNTS_ROLE = C.AUTHOR_CODE
       LEFT JOIN COMTNAUTHORINFO D ON A.CHLDRN_ROLE = D.AUTHOR_CODE;



-- 현재 ROLE 목록
SELECT AUTHOR_CODE, AUTHOR_NM, AUTHOR_DC, AUTHOR_CREAT_DE FROM COMTNAUTHORINFO ORDER BY AUTHOR_CREAT_DE DESC;
/*
IS_AUTHENTICATED_ANONYMOUSLY,       스프링시큐리티 내부사용(롤부여 금지)
IS_AUTHENTICATED_FULLY,             스프링시큐리티 내부사용(롤부여 금지)
IS_AUTHENTICATED_REMEMBERED,        스프링시큐리티 내부사용(롤부여 금지)
ROLE_ADMIN,                         관리자
ROLE_ANONYMOUS,                     익명 사용자
ROLE_USER,                          일반 사용자

-- 추가 --
일반 직원   ROLE_EMPL
관리 직원   ROLE_MNGM

*/

-- 현재 ROLE 계층
SELECT
    distinct
    A.PARNTS_ROLE PARENT,
    A.CHLDRN_ROLE CHILD
FROM COMTNROLES_HIERARCHY A LEFT JOIN COMTNROLES_HIERARCHY B ON (A.CHLDRN_ROLE = B.PARNTS_ROLE);

SELECT distinct a.CHLDRN_ROLE as child, a.PARNTS_ROLE parent
FROM COMTNROLES_HIERARCHY a LEFT JOIN COMTNROLES_HIERARCHY b on (a.CHLDRN_ROLE = b.PARNTS_ROLE);
/*
ROLE_ANONYMOUS,                 IS_AUTHENTICATED_ANONYMOUSLY
IS_AUTHENTICATED_REMEMBERED,    IS_AUTHENTICATED_FULLY
IS_AUTHENTICATED_REMEMBERED,    IS_AUTHENTICATED_FULLY
IS_AUTHENTICATED_REMEMBERED,    IS_AUTHENTICATED_FULLY
IS_AUTHENTICATED_ANONYMOUSLY,   IS_AUTHENTICATED_REMEMBERED
ROLE_EMPL,                      ROLE_ADMIN
ROLE_MNGM,                      ROLE_ADMIN
ROLE_USER,                      ROLE_ADMIN
IS_AUTHENTICATED_FULLY,         ROLE_EMPL
IS_AUTHENTICATED_FULLY,         ROLE_MNGM
IS_AUTHENTICATED_FULLY,         ROLE_USER

ROLE_ANONYMOUS → IS_AUTHENTICATED_ANONYMOUSLY → IS_AUTHENTICATED_REMEMBERED → IS_AUTHENTICATED_FULLY

    → ROLE_USER → ROLE_ADMIN
      ROLE_MNGM
      ROLE_EMPL
 */

SELECT PARNTS_ROLE, CHLDRN_ROLE  from COMTNROLES_HIERARCHY;
/*
ROLE_ANONYMOUS,                 IS_AUTHENTICATED_ANONYMOUSLY
IS_AUTHENTICATED_ANONYMOUSLY,   IS_AUTHENTICATED_REMEMBERED
IS_AUTHENTICATED_REMEMBERED,    IS_AUTHENTICATED_FULLY
IS_AUTHENTICATED_FULLY,         ROLE_USER
IS_AUTHENTICATED_FULLY,         ROLE_MNGM
IS_AUTHENTICATED_FULLY,         ROLE_EMPL
ROLE_USER,                      ROLE_ADMIN
ROLE_MNGM,                      ROLE_ADMIN
ROLE_EMPL,                      ROLE_ADMIN
 */

-- 권한 계층 등록
INSERT INTO COMTNROLES_HIERARCHY VALUES ('ROLE_ANONYMOUS'               ,'IS_AUTHENTICATED_ANONYMOUSLY');

INSERT INTO COMTNROLES_HIERARCHY VALUES ('IS_AUTHENTICATED_FULLY', 'ROLE_MNGM');
INSERT INTO COMTNROLES_HIERARCHY VALUES ('ROLE_MNGM', 'ROLE_ADMIN');

INSERT INTO COMTNROLES_HIERARCHY VALUES ('IS_AUTHENTICATED_FULLY', 'ROLE_EMPL');
INSERT INTO COMTNROLES_HIERARCHY VALUES ('ROLE_EMPL', 'ROLE_ADMIN');

-- 내 권한 목록
SELECT A.SCRTY_DTRMN_TRGET_ID USER_ID, A.AUTHOR_CODE AUTHORITY
FROM COMTNEMPLYRSCRTYESTBS A,
     COMVNUSERMASTER B
WHERE A.SCRTY_DTRMN_TRGET_ID = B.ESNTL_ID
--  AND B.USER_ID = 'MNGM01';
   AND B.USER_ID = 'TEST1';


SELECT USER_ID,
       ESNTL_ID AS                                                                 PASSWORD,
       1                                                                           ENABLED,
       USER_NM,
       USER_ZIP,
       USER_ADRES,
       USER_EMAIL,
       USER_SE,
       ORGNZT_ID,
       ESNTL_ID,
       (select a.ORGNZT_NM from COMTNORGNZTINFO a where a.ORGNZT_ID = m.ORGNZT_ID) ORGNZT_NM
FROM COMVNUSERMASTER m
# WHERE CONCAT(USER_SE, USER_ID) = 'USREMPL01';
WHERE CONCAT(USER_SE, USER_ID) = 'USRTEST1';

-- sqlRolesAndUrl
SELECT a.ROLE_PTTRN url, b.AUTHOR_CODE authority
FROM COMTNROLEINFO a, COMTNAUTHORROLERELATE b
WHERE a.ROLE_CODE = b.ROLE_CODE
AND a.ROLE_TY = 'url'  ORDER BY a.ROLE_SORT;

SELECT A.SCRTY_DTRMN_TRGET_ID USER_ID, A.AUTHOR_CODE AUTHORITY FROM COMTNEMPLYRSCRTYESTBS A,
# COMVNUSERMASTER B WHERE A.SCRTY_DTRMN_TRGET_ID = B.ESNTL_ID AND B.USER_ID = 'TEST1';
COMVNUSERMASTER B WHERE A.SCRTY_DTRMN_TRGET_ID = B.ESNTL_ID AND B.USER_ID = 'EMPL01';

/*
web-000001, 로그인롤,      /uat/uia/.*\.do.*       로그인허용을 위한 롤,                   URL,     1
web-000003, 모든접근제한,  /.*\.do.*               모든자원에 대한 접근 제한 롤,           URL,     3
web-000004, 회원관리,      /uss/umt/.*\.do.*       회원관리에 대한 접근 제한 롤,           URL,     1
web-000005, 실명확인,      /sec/rnc/.*\.do.*       실명확인에 대한 접근 제한 롤,           URL,     1
web-000006, 우편번호,      /sym/ccm/zip/.*\.do.*   우편번호관리에 대한 접근 제한 롤,       URL,     1
web-000007, 로그인이미지,  /uss/ion/lsi/.*\.do.*   로그인이미지관리에 대한 접근 제한 롤,   URL,     1

                           /sym/mnu/mpm/.*\.do.*
                           /dty/hnr/.*\.do.*
 */

SELECT MENU_NO AS MENU_NO , MENU_ORDR AS MENU_ORDR , MENU_NM AS MENU_NM , UPPER_MENU_NO AS
UPPER_MENU_ID , MENU_DC AS MENU_DC , RELATE_IMAGE_PATH AS RELATE_IMAGE_PATH , RELATE_IMAGE_NM
AS RELATE_IMAGE_NM , PROGRM_FILE_NM AS PROGRM_FILE_NM FROM COMTNMENUINFO
WHERE binary(MENU_NM) like CONCAT('%', '', '%')
and UPPER_MENU_NO = 10000000
;

-- 메뉴 권한 추가
 INSERT INTO COMTNMENUCREATDTLS ( AUTHOR_CODE ,MENU_NO )
			VALUES (  'ROLE_MNGM', 13000000 );

SELECT next_id FROM COMTECOPSEQ WHERE table_name = 'SYSLOG_ID';
UPDATE COMTECOPSEQ SET next_id = 2341 WHERE table_name = 'SYSLOG_ID';

# 	<select id="selectMainMenuLeft" parameterType="egovframework.com.sym.mnu.mpm.service.MenuManageVO" resultType="egovMap">

			SELECT
			       B.MENU_NO           AS MENU_NO
				 , B.MENU_ORDR         AS MENU_ORDR
				 , B.MENU_NM           AS MENU_NM
				 , B.UPPER_MENU_NO     AS UPPER_MENU_ID
				 , B.RELATE_IMAGE_PATH AS RELATE_IMAGE_PATH
				 , B.RELATE_IMAGE_NM   AS RELATE_IMAGE_NM
				 , (SELECT C.URL FROM COMTNPROGRMLIST C WHERE B.PROGRM_FILE_NM = C.PROGRM_FILE_NM) AS CHK_URL
			FROM   COMTNMENUCREATDTLS A, COMTNMENUINFO B
			WHERE  A.MENU_NO  = B.MENU_NO
			AND    A.AUTHOR_CODE = (SELECT AUTHOR_CODE from COMTNEMPLYRSCRTYESTBS
	                                WHERE  SCRTY_DTRMN_TRGET_ID = 'USRCNFRM_00000000000')
			ORDER BY B.MENU_ORDR;


select count(MENU_NO)
			from   COMTNMENUINFO
			where  UPPER_MENU_NO = 1000000
			and    MENU_ORDR =
			      (select MIN(MENU_ORDR)
			       from COMTNMENUCREATDTLS A, COMTNMENUINFO B
			       where A.MENU_NO = B.MENU_NO
			       AND   A.AUTHOR_CODE = (SELECT AUTHOR_CODE from COMTNEMPLYRSCRTYESTBS
	                                      WHERE  SCRTY_DTRMN_TRGET_ID = 'USRCNFRM_00000000000')
			       AND   B.UPPER_MENU_NO = 1000000);

SELECT URL
			FROM   COMTNPROGRMLIST
			WHERE  PROGRM_FILE_NM =
			       (SELECT PROGRM_FILE_NM FROM COMTNMENUINFO
				    WHERE MENU_NO = 1);

