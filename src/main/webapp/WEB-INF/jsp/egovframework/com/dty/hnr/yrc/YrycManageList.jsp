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
        function fncYrycRegist() {
            location.href = "<c:url value='/dty/hnr/yrc/YrycRegist.do'/>";
        }

        -->
    </script>
</head>
<body>
<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg"/></noscript>

<div id="app" class="board">
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
    import {createApp, ref, onMounted, } from 'vue'

    const app = createApp({
        template: `
          <h1>개인연차관리 목록</h1>

          <SearchBox v-on:changeYear="onChangeYear" v-bind:yearArr="yearArr" v-bind:selectedYear="selectedYear" />

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
              <th scope="col">발생연도</th>
              <th scope="col">발생연차</th>
              <th scope="col">사용연차</th>
              <th scope="col">잔여연차</th>
              <th scope="col">사용자</th>
            </tr>
            </thead>
            <TableBody v-bind:yrycList="yrycList" />
          </table>`
        ,
        setup() {
            let yrycList = ref([])
            const yearArr = ref([])
            let occrrncYear = ref("")
            let selectedYear = ref("")

            onMounted(
                    async () => {
                        const date = new Date()
                        selectedYear.value = date.getFullYear().toString()

                        for (let i = 0; i < 5; i++) {
                            yearArr.value.push((date.getFullYear() - i).toString())
                        }

                        console.log("app.onMounted -> ", selectedYear.value)

                        yrycList.value = await selectYrycList(selectedYear.value)
                    }
            )


            const onChangeYear = async (selectedYear) => {
                occrrncYear.value = selectedYear
                console.log("app.onChangeYear.occrrncYear.value -> ", occrrncYear.value)
                console.log("app.onChangeYear.selectedYear.value -> ", selectedYear)

                yrycList.value = await selectYrycList(occrrncYear.value)
            }

            const selectYrycList = async (occrrncYear) => {

                let uri = "?";
                const url = "/dty/hnr/yrc/yryc-manages"

                const form = new FormData();
                form.append("occrrncYear", occrrncYear);

                const entries = form.entries()
                for (const pair of entries) {
                    uri = uri + pair[0] + '=' + pair[1] + '&'
                }

                console.log('url => ', url + uri)

                try {
                    const data = await axios.get(url + uri)
                    return data.data.datas
                } catch (error) {
                    console.log('error -> ', error)
                    return ""
                }
            }

            return {
                yearArr,
                yrycList,
                selectedYear,
                onChangeYear,
            }
        },
    })

    app.component("SearchBox", {
        template: `
          <div id="search-box" class="search_box" title='<spring:message code="common.searchCondition.msg" />'>
            <ul>
              <li>
                <label for="">휴가년도 : </label>
                <select name="searchKeyword" title="휴가년도" v-model="selectedYear" v-on:change="onChangeYear">
                  <option value="">전체</option>
                  <option v-for="year in yearArr" key="year" v-bind:value="year"
                          v-bind:selected="year === selectedYear">{{ year }}
                  </option>
                </select>년

                <span class="btn_b">
					<a href="<c:url value='/dty/hnr/yrc/YrycRegist.do'/>"
                       onclick="fncYrycRegist(); return false;" title="">등록</a>
			  </span>
              </li>
            </ul>
          </div>`,
        emits: ["changeYear"],
        props: ["yearArr", "selectedYear"],
        setup(props, ctx) {

            const yearArr = ref(props.yearArr)
            const selectedYear = ref(props.selectedYear)

            const onChangeYear = () => {
                console.log("searchBox.onChangeYear.selectedYear -> ", selectedYear.value);
                ctx.emit('changeYear', selectedYear.value)
            }

            return {
                onChangeYear,
                selectedYear,
            }
        },
    })

    app.component("TableBody",
            {
                template: `
                  <tbody>
                  <tr v-for="item in yrycList" key="item.occrrncYear+'|'+item.userId">
                    <td>{{ item.occrrncYear }}</td>
                    <td>{{ item.occrncYrycCo }}</td>
                    <td>{{ item.useYrycCo }}</td>
                    <td>{{ item.remndrYrycCo }}</td>
                    <td>{{ item.mberNm }}</td>
                  </tr>
                  <tr v-if="yrycList.length < 1">
                    <td colspan="5"> {{ message }} </td>
                  </tr>
                  </tbody> `
                ,
                props: ['yrycList'],
                setup(props) {

                    const yrycList = ref(props.yrycList)

                    const message = ref('<spring:message code="info.nodata.msg" />')

                    return {
                        message,
                    }
                },
            }
    )

    app.mount('#app')
</script>
</body>
</html>
