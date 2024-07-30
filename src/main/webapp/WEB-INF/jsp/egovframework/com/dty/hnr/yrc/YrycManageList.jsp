<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title><spring:message code="comUssIonYrc.indvdlYrycManageList.title"/></title><!-- 개인연차관리 목록 -->
    <link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
    <script type="text/javaScript" language="javascript">
        <!--
        /* ********************************************************
         * 등록 화면 호출 함수
         ******************************************************** */
        function fncIndvdlYrycRegist() {
            location.href = "<c:url value='/uss/ion/yrc/EgovIndvdlYrycRegist.do'/>";
        }

        -->
    </script>
</head>
<body>
<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg"/></noscript>

<div class="board">
    <h1><spring:message code="comUssIonYrc.indvdlYrycManageList.title"/></h1><!-- 개인연차관리 목록 -->

    <span>${messageTemp}</span> <!-- /uss/ion/vct/web/EgovVcatnManageController.java 휴가 등록 시 개인연차가 없을 경우 메세지를 받음. -->


    <div id="search-box" class="search_box" title="<spring:message code="common.searchCondition.msg" />">
    </div>


    <table class="board_list">
        <caption></caption>
        <colgroup>
            <col style="width:15%"/>
            <col style="width:15%"/>
            <col style="width:15%"/>
            <col style="width:15%"/>
            <col style="width:30%"/>
        </colgroup>
        <thead>
        <tr>
            <th scope="col"><spring:message code="comUssIonYrc.indvdlYrycManageList.occrrncYear"/></th><!-- 발생연도 -->
            <th scope="col"><spring:message code="comUssIonYrc.indvdlYrycManageList.occrncYrycCo"/></th><!-- 발생연차 -->
            <th scope="col"><spring:message code="comUssIonYrc.indvdlYrycManageList.useYrycCo"/></th><!-- 사용연차 -->
            <th scope="col"><spring:message code="comUssIonYrc.indvdlYrycManageList.remndrYrycCo"/></th><!-- 잔여연차 -->
            <th scope="col"><spring:message code="comUssIonYrc.indvdlYrycManageList.mberNm"/></th><!-- 사용자 -->
        </tr>
        </thead>
        <tbody id="table-body">
        </tbody>
    </table>
</div>


<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

<script type="importmap">
    {
      "imports": {
        "vue": "https://unpkg.com/vue@3/dist/vue.esm-browser.js"
      }
    }
</script>

<script type="module">
    import {createApp, ref, onMounted} from 'vue'

    const searchBox = createApp({
        template: `
          <ul>
            <li>
              <label for="">휴가년도 : </label>
              <select name="searchKeyword" title="휴가년도" v-model="selectedYear">
                <option value="">전체</option>
                <option v-for="year in yearList" key="year" v-bind:value="year" v-bind:selected="year === selectedYear">
                  {{ year }}
                </option>
              </select>년

              <span class="btn_b">
					<a href="<c:url value='/uss/ion/yrc/EgovIndvdlYrycRegist.do'/>"
                       onclick="fncIndvdlYrycRegist(); return false;" title="">
                        <spring:message code="button.create"/>
					</a>
			  </span>
            </li>
          </ul>
        `
        ,
        setup() {
            const yearList = ref([])
            let selectedYear = ''

            onMounted(
                    () => {
                        const yearDate = new Date()

                        selectedYear = yearDate.getFullYear()

                        for (let i = 0; i < 5; i++) {
                            yearList.value.push(yearDate.getFullYear() - i)
                        }
                    }
            )

            return {
                yearList,
                selectedYear,
            }
        },
    }).mount('#search-box')

    const tableBody = createApp({
        template: `
          <tr v-for="item in yrycList" key="item.occrrncYear+'|'+item.userId">
            <td>{{ item.occrrncYear }}</td>
            <td>{{ item.occrncYrycCo }}</td>
            <td>{{ item.useYrycCo }}</td>
            <td>{{ item.remndrYrycCo }}</td>
            <td>{{ item.mberNm }}</td>
          </tr>
          <tr v-if="yrycList.length < 1">
            <td colspan="5"> {{ message }}</td>
          </tr>`
        ,
        setup() {
            let yrycList = ref([])
            const message = ref('<spring:message code="info.nodata.msg" />')

            const selectYrycList = async (selectedYear) => {

                const form = new FormData();
                form.append("occrrncYear", selectedYear);


                console.log(form.values())

                try {
                    const data = await axios.get('/dty/hnr/yrc/yryc-manages', form.toString().valueOf())
                    yrycList.value = data.data.datas
                    // console.log('yrycList -> ', yrycList)
                } catch (error) {
                    console.log('error -> ', error)
                }
            }

            onMounted(
                    async () => {
                        await selectYrycList('2023')
                    }
            )

            return {
                message,
                yrycList,
                selectYrycList,
            }
        },
    }).mount('#table-body')

</script>


</body>
</html>




