package egovframework.com.dty.hnr.vct.service.impl;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.dty.hnr.vct.service.VctnManage;
import egovframework.com.dty.hnr.vct.service.VctnManageService;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import egovframework.com.dty.hnr.yrc.service.YrycManageService;
import egovframework.com.dty.hnr.yrc.service.YrycManageVO;
import egovframework.com.uss.ion.ism.service.EgovInfrmlSanctnService;
import egovframework.com.uss.ion.ism.service.InfrmlSanctn;
import egovframework.com.uss.ion.vct.service.IndvdlYrycManage;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service("vctNManageService")
public class VctnManageServiceImpl extends EgovAbstractServiceImpl implements VctnManageService {

    @Resource(name = "vctnManageDAO")
    private VctnManageDAO vctnManageDAO;

    @Resource(name = "EgovInfrmlSanctnService")
    protected EgovInfrmlSanctnService infrmlSanctnService;

    @Resource(name = "yrycManageService")
    protected YrycManageService yrycManageService;


    @Override
    public List<VctnManageVO> selectVctnManageList(VctnManageVO searchVO) throws Exception {

        List<VctnManageVO> result = vctnManageDAO.selectVctnManageList(searchVO);

        int num = result.size();

        for (int i = 0; i < num; i++) {
            VctnManageVO vctnManageVO1 = result.get(i);
            vctnManageVO1.setBgnde(EgovDateUtil.formatDate(vctnManageVO1.getBgnde(), "-"));
            vctnManageVO1.setEndde(EgovDateUtil.formatDate(vctnManageVO1.getEndde(), "-"));
            result.set(i, vctnManageVO1);
        }

        return result;
    }

    @Override
    public int selectVctnManageListTotCnt(VctnManageVO searchVO) throws Exception {

        return vctnManageDAO.selectVctnManageListTotCnt(searchVO);
    }


    /**
     * 휴가일자 중복여부 체크
     *
     * @param vctnManageVO
     * @return
     * @throws Exception
     */
    @Override
    public int selectVctnManageDplctAt(VctnManageVO vctnManageVO) throws Exception {

        return vctnManageDAO.selectVctnManageDplctAt(vctnManageVO);
    }


    /**
     * 휴가관리정보를 신규로 등록한다. 01 : 입력성공, 02 : 연차휴가 등록실패(잔여연차 부족),  03: 반차휴가 등록실패(잔여연차 부족)
     *
     * @param vctnManage
     * @param vctnManageVO
     * @return
     * @throws Exception
     */
    @Override
    public String insertVctnManage(VctnManage vctnManage, VctnManageVO vctnManageVO) throws Exception {

        Calendar cal = Calendar.getInstance();
        String sYear = Integer.toString(cal.get(java.util.Calendar.YEAR));
        String sMonth = Integer.toString(cal.get(java.util.Calendar.MONTH) + 1);

        if (sMonth.length() == 1) {
            sMonth = "0" + sMonth;
        }

        String sDay = Integer.toString(cal.get(java.util.Calendar.DATE));

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        // KISA 보안취약점 조치 (2018-12-10, 신용호)
        String uniqId = "";
        if (user != null) {
            uniqId = user.getUniqId();
        }

        vctnManage.setOccrrncYear(sYear);
        vctnManage.setReqstDe(sYear + sMonth + sDay);

        /*
         * 휴가 승인처리 신청 infrmlSanctnService.insertInfrmlSanctn("000", vctnManage);
         */
        vctnManage.setBgnde(EgovStringUtil.removeMinusChar(vctnManage.getBgnde()));
        vctnManage.setEndde(EgovStringUtil.removeMinusChar(vctnManage.getEndde()));
        vctnManage.setReqstDe(EgovStringUtil.removeMinusChar(vctnManage.getReqstDe()));
        InfrmlSanctn infrmlSanctn = infrmlSanctnService.insertInfrmlSanctn(this.converToInfrmlSanctnObject(vctnManage));
        vctnManage.setInfrmlSanctnId(infrmlSanctn.getInfrmlSanctnId());

        YrycManageVO yrycManageVO = yrycManageService.selectYrycManage(uniqId);
        double iUseYrycCo = yrycManageVO.getUseYrycCo(); //연차테이블의 사용 연차개수
        double iRemndrYrycCo = yrycManageVO.getRemndrYrycCo(); //연차테이블의 잔여 연차개수
        double iCountYryc = 0.0;

        /*
         * 시작일자 와 종료일자 사이의 일자 개수 - 공휴일 or 주말 제외
         */
        if ("01".equals(vctnManage.getVcatnSe())) {
            // 휴가구분이 연차인 경우

            // 연차 휴가 연도 체크
            if (!this.getVctnYearSE(vctnManage)) {
                return "09";
            }

            iCountYryc = getDateCalc(vctnManage.getBgnde(), vctnManage.getEndde());

            if (iCountYryc == 0) {
                return "99"; //연차설정오류
            } else if ((iRemndrYrycCo - iCountYryc) < 0) {
                return "02";
            } else {
                vctnManageDAO.insertVctnManage(vctnManage);
                IndvdlYrycManage indvdlYrycManage = new IndvdlYrycManage();
                indvdlYrycManage.setUseYrycCo(iUseYrycCo + iCountYryc);
                indvdlYrycManage.setRemndrYrycCo(iRemndrYrycCo - iCountYryc);
                indvdlYrycManage.setLastUpdusrId(vctnManage.getApplcntId());
                indvdlYrycManage.setOccrrncYear(vctnManage.getOccrrncYear());
                indvdlYrycManage.setUsid(vctnManage.getApplcntId());
                updtIndvdlYrycManage(indvdlYrycManage);
                return "01";
            }
        } else if ("02".equals(vctnManage.getVcatnSe())) {
            // 휴가구분이 반차인 경우

            // 연차 휴가 연도 체크
            if (!this.getVctnYearSE(vctnManage)) {
                return "09";
            }

            iCountYryc = getDateCalc(vctnManage.getBgnde(), vctnManage.getBgnde()); //반차는 시작일자 종료일자 동일함. 시작일자로만 체크

            if (iCountYryc == 0) {
                return "99"; //연차설정오류
            } else if ((iRemndrYrycCo - 0.5) < 0) {
                return "03";
            } else {
                vctnManageDAO.insertVctnManage(vctnManage);
                IndvdlYrycManage indvdlYrycManage = new IndvdlYrycManage();
                indvdlYrycManage.setUseYrycCo(iUseYrycCo + 0.5);
                indvdlYrycManage.setRemndrYrycCo(iRemndrYrycCo - 0.5);
                indvdlYrycManage.setLastUpdusrId(vctnManage.getApplcntId());
                indvdlYrycManage.setOccrrncYear(vctnManage.getOccrrncYear());
                indvdlYrycManage.setUsid(vctnManage.getApplcntId());
                updtIndvdlYrycManage(indvdlYrycManage);

                return "01";
            }
        } else {
            vctnManageDAO.insertVctnManage(vctnManage);
            return "01";
        }
    }


    /**
     * 등록된 휴가관리의 상세정보를 조회한다.
     *
     * @param vctnManageVO
     * @return
     * @throws Exception
     */
    @Override
    public VctnManageVO selectVctnManage(VctnManageVO vctnManageVO) throws Exception {

        VctnManageVO vctnManageVOTemp = vctnManageDAO.selectVctnManage(vctnManageVO);
        vctnManageVOTemp.setBgnde(EgovDateUtil.formatDate(vctnManageVOTemp.getBgnde(), "-"));
        vctnManageVOTemp.setEndde(EgovDateUtil.formatDate(vctnManageVOTemp.getEndde(), "-"));

        // 연차정보
        YrycManageVO yrycManageVO = yrycManageService.selectYrycManage(vctnManageVO.getApplcntId());
        vctnManageVOTemp.setOccrrncYear(yrycManageVO.getOccrrncYear());
        vctnManageVOTemp.setUsid(yrycManageVO.getUsid());
        vctnManageVOTemp.setOccrncYrycCo(yrycManageVO.getOccrncYrycCo());
        vctnManageVOTemp.setUseYrycCo(yrycManageVO.getUseYrycCo());
        vctnManageVOTemp.setRemndrYrycCo(yrycManageVO.getRemndrYrycCo());

        return vctnManageVOTemp;
    }


    /**
     * VctnManage model을 InfrmlSanctn model로 변환한다.
     *
     * @param vctnManage
     * @return
     * @throws Exception
     */
    private InfrmlSanctn converToInfrmlSanctnObject(VctnManage vctnManage) throws Exception {
        InfrmlSanctn infrmlSanctn = new InfrmlSanctn();
        infrmlSanctn.setJobSeCode("003"); // 업무구분코드 (공통코드 COM75)
        infrmlSanctn.setApplcntId(vctnManage.getApplcntId()); // 신청자ID
        infrmlSanctn.setReqstDe(vctnManage.getReqstDe()); // 신청일자
        infrmlSanctn.setSanctnerId(vctnManage.getSanctnerId()); // 결재자ID
        infrmlSanctn.setConfmAt(vctnManage.getConfmAt()); // 승인구분
        infrmlSanctn.setSanctnDt(vctnManage.getSanctnDt()); // 결재일시
        infrmlSanctn.setReturnResn(vctnManage.getReturnResn()); // 반려사유
        infrmlSanctn.setFrstRegisterId(vctnManage.getFrstRegisterId());
        infrmlSanctn.setFrstRegisterPnttm(vctnManage.getFrstRegisterId());
        infrmlSanctn.setLastUpdusrId(vctnManage.getLastUpdusrId());
        infrmlSanctn.setLastUpdusrPnttm(vctnManage.getLastUpdusrPnttm());
        infrmlSanctn.setInfrmlSanctnId(vctnManage.getInfrmlSanctnId());// 약식결재ID
        return infrmlSanctn;
    }

    /**
     * 휴가일자 해당연차발생연도에 속하는지 여부 체크
     *
     * @param vctnManage
     * @return
     * @throws Exception
     */
    private boolean getVctnYearSE(VctnManage vctnManage) throws Exception {

        boolean bRetrunValue = false;
        Calendar cal = Calendar.getInstance();

        int iYear = cal.get(java.util.Calendar.YEAR);
        // 시작일자
        int iYearBgnVctn = Integer.parseInt(vctnManage.getBgnde().substring(0, 4));
        // 종료일자
        int iYearEndVctn = Integer.parseInt(vctnManage.getEndde().substring(0, 4));
        if (iYear == iYearBgnVctn && iYear == iYearEndVctn) {
            bRetrunValue = true;
        }
        return bRetrunValue;
    }


    /**
     * 해당일자의 날짜사이 일수를 구한다
     *
     * @param fromDay
     * @param toDay
     * @return
     * @throws Exception
     */
    private double getDateCalc(String fromDay, String toDay) throws Exception {

        // 시작일자
        int fromYear = Integer.parseInt(fromDay.substring(0, 4));
        int fromMonth = Integer.parseInt(fromDay.substring(4, 6)) - 1;
        int fromDate = Integer.parseInt(fromDay.substring(6, 8));
        // 종료일자
        int toYear = Integer.parseInt(toDay.substring(0, 4));
        int toMonth = Integer.parseInt(toDay.substring(4, 6)) - 1;
        int toDate = Integer.parseInt(toDay.substring(6, 8));

        Calendar startDay = Calendar.getInstance();
        startDay.set(fromYear, fromMonth, fromDate);

        Calendar endDay = Calendar.getInstance();
        endDay.set(toYear, toMonth, toDate);

        double Count = 0.0;

        // 시작일자 부터 종료일자까지 while
        while (!startDay.after(endDay)) {
            // 토요일, 일요일은  일수 count에서 제외
            if (startDay.get(Calendar.DAY_OF_WEEK) != 1 && startDay.get(Calendar.DAY_OF_WEEK) != 7) {
                Count++;
            }
            startDay.add(Calendar.DATE, 1);
        }

        return Count;
    }


    /**
     * 개인별 연차를 수정 처리한다.
     *
     * @param indvdlYrycManage
     * @throws Exception
     */
    public void updtIndvdlYrycManage(IndvdlYrycManage indvdlYrycManage) throws Exception {

        vctnManageDAO.updtIndvdlYrycManage(indvdlYrycManage);
    }


}
