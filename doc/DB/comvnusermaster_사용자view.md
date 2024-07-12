create definer = egov_user@`%` view egov_db.comvnusermaster as
select `egov_db`.`comtngnrlmber`.`ESNTL_ID` AS `ESNTL_ID`,
       `egov_db`.`comtngnrlmber`.`MBER_ID` AS `MBER_ID`,
       `egov_db`.`comtngnrlmber`.`PASSWORD` AS `PASSWORD`,
       `egov_db`.`comtngnrlmber`.`MBER_NM` AS `MBER_NM`,
       `egov_db`.`comtngnrlmber`.`ZIP` AS `ZIP`,
       `egov_db`.`comtngnrlmber`.`ADRES` AS `ADRES`,
       `egov_db`.`comtngnrlmber`.`MBER_EMAIL_ADRES` AS `MBER_EMAIL_ADRES`,
       ' ' AS `Name_exp_8`,
       'GNR' AS `USER_SE`,
       ' ' AS `ORGNZT_ID` 
from `egov_db`.`comtngnrlmber` 
union all 
select `egov_db`.`comtnemplyrinfo`.`ESNTL_ID` AS `ESNTL_ID`,
       `egov_db`.`comtnemplyrinfo`.`EMPLYR_ID` AS `EMPLYR_ID`,
       `egov_db`.`comtnemplyrinfo`.`PASSWORD` AS `PASSWORD`,
       `egov_db`.`comtnemplyrinfo`.`USER_NM` AS `USER_NM`,
       `egov_db`.`comtnemplyrinfo`.`ZIP` AS `ZIP`,
       `egov_db`.`comtnemplyrinfo`.`HOUSE_ADRES` AS `HOUSE_ADRES`,
       `egov_db`.`comtnemplyrinfo`.`EMAIL_ADRES` AS `EMAIL_ADRES`,
       `egov_db`.`comtnemplyrinfo`.`GROUP_ID` AS `GROUP_ID`,
       'USR' AS `USER_SE`,
       `egov_db`.`comtnemplyrinfo`.`ORGNZT_ID` AS `ORGNZT_ID` 
from `egov_db`.`comtnemplyrinfo` 
union all
select `egov_db`.`comtnentrprsmber`.`ESNTL_ID` AS `ESNTL_ID`,
       `egov_db`.`comtnentrprsmber`.`ENTRPRS_MBER_ID` AS `ENTRPRS_MBER_ID`,
       `egov_db`.`comtnentrprsmber`.`ENTRPRS_MBER_PASSWORD` AS `ENTRPRS_MBER_PASSWORD`,
       `egov_db`.`comtnentrprsmber`.`CMPNY_NM` AS `CMPNY_NM`,
       `egov_db`.`comtnentrprsmber`.`ZIP` AS `ZIP`,
       `egov_db`.`comtnentrprsmber`.`ADRES` AS `ADRES`,
       `egov_db`.`comtnentrprsmber`.`APPLCNT_EMAIL_ADRES` AS `APPLCNT_EMAIL_ADRES`,
       ' ' AS `Name_exp_28`,
       'ENT' AS `USER_SE`,
       ' ' AS `ORGNZT_ID` 
from `egov_db`.`comtnentrprsmber`
order by `ESNTL_ID`;

