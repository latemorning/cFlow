<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title><spring:message code="comUssIonYrc.indvdlYrycRegist.title"/></title><!-- 개인연차등록 -->
    <link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
    <validator:javascript formName="indvdlYrycManage" staticJavascript="false" xhtml="true" cdata="false"/>
    <script type="text/javaScript" language="javascript">
        <%--function fnIndvdYrycMangeList() {--%>
        <%--    location.href = "<c:url value='/uss/ion/yrc/EgovIndvdlYrycManageList.do'/>";--%>
        <%--}--%>

        <%--function fnRegistIndvdYrycMange() {--%>
        <%--    var varForm = document.getElementById("indvdlYrycManage");--%>

        <%--    var occrncYrycCo = varForm.occrncYrycCo.value;--%>
        <%--    var useYrycCo = varForm.useYrycCo.value;--%>
        <%--    var diffValue = occrncYrycCo - useYrycCo;--%>
        <%--    if (diffValue < 0) {--%>
        <%--        alert("<spring:message code="comUssIonYrc.indvdlYrycRegist.diffValue" />"); //잔여연차가 음수일 수 없습니다.--%>
        <%--        return;--%>
        <%--    }--%>

        <%--    if (!validateIndvdlYrycManage(varForm)) {--%>
        <%--        return;--%>
        <%--    } else {--%>
        <%--        varForm.action = "<c:url value='/uss/ion/yrc/EgovIndvdlYrycRegist.do'/>";--%>
        <%--        varForm.submit();--%>
        <%--    }--%>
        <%--}--%>

        <%--function fnDeleteIndvdYrycMange() {--%>
        <%--    var varForm = document.getElementById("indvdlYrycManage");--%>
        <%--    varForm.action = "<c:url value='/uss/ion/yrc/deleteIndvdlYryc.do'/>";--%>
        <%--    if (confirm("<spring:message code='common.delete.msg' />")) {--%>
        <%--        varForm.submit();--%>
        <%--    }--%>
        <%--}--%>

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
    import {
        createApp, ref, computed,
        onMounted,
        onUnmounted,
        onUpdated,
        onBeforeMount,
        onBeforeUpdate,
        onBeforeUnmount,
        onErrorCaptured,
        onActivated,
        onDeactivated,
    } from 'vue'

    const app = createApp({
        template: `
          <h1>개인 연차 등록</h1>

          <SearchBox v-bind:yearArr="yearArr" v-bind:occrrncYear="occrrncYear" v-on:changeYear="onChangeYear"/>

          <table class="board_list">
            <caption></caption>
            <colgroup>
              <col style="width:15%"/>
              <col style="width:15%"/>
              <col style="width:15%"/>
              <col style="width:15%"/>
              <col style="width:15%"/>
              <col style="width:25%"/>
            </colgroup>
            <thead>
            <tr>
              <th scope="col">발생 연도</th>
              <th scope="col">사용자</th>
              <th scope="col">발생 연차</th>
              <th scope="col">사용 연차</th>
              <th scope="col">잔여 연차</th>
              <th scope="col">저장/삭제</th>
            </tr>
            </thead>

            <TableBody v-bind:yrycList="yrycList" v-bind:selectedYear="occrrncYear"/>

          </table>`
        ,
        setup() {
            let yrycList = ref([])
            const yearArr = ref([])
            let occrrncYear = ref("")

            onBeforeMount(
                    async () => {
                        const date = new Date()
                        occrrncYear.value = ""

                        for (let i = 0; i < 5; i++) {
                            yearArr.value.push((date.getFullYear() - i).toString())
                        }

                        yrycList.value = await selectYrycList(occrrncYear.value)
                    }
            )

            const onChangeYear = async (selectedYear) => {
                occrrncYear.value = selectedYear
                yrycList.value = []
                yrycList.value = await selectYrycList(occrrncYear.value)
            }

            const selectYrycList = async (occrrncYear) => {

                let uri = "?";
                const url = "/dty/hnr/yrc/yryc-maps"

                const form = new FormData();
                form.append("occrrncYear", occrrncYear);

                const entries = form.entries()
                for (const pair of entries) {
                    uri = uri + pair[0] + '=' + pair[1] + '&'
                }

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
                occrrncYear,
                onChangeYear,
            }
        },
    })

    app.component("SearchBox", {
        template: `
          <div class="search_box" title="<spring:message code="common.searchCondition.msg"/>">
            <ul>
              <li>
                <label for="">발생년도 : </label>
                <select name="searchKeyword" title="발생년도" v-model="selectedYear" v-on:change="onChangeYear">
                  <option value="">전체</option>
                  <option v-for="year in yearArr" key="year" v-bind:value="year">{{ year }}</option>
                </select>년&nbsp;
                <input class="s_btn" type="button" value='목록' title='목록'/>&nbsp;
              </li>
            </ul>
          </div>`
        ,
        emits: ["changeYear"],
        props: ["yearArr", "occrrncYear"],
        setup(props, ctx) {

            const yearArr = ref(props.yearArr)
            const selectedYear = ref(props.occrrncYear)

            const onChangeYear = () => {
                ctx.emit('changeYear', selectedYear.value)
            }

            return {
                yearArr,
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
                    <TableItem v-bind:yrycItem="item" v-bind:selectedYear="selectedYear"/>
                  </tr>
                  <tr v-if="yrycList.length < 1">
                    <td colspan="6"><spring:message code="info.nodata.msg" /></td>
                  </tr>
                  </tbody>`
                ,
                props: ["yrycList", "selectedYear"],
                setup(props) {
                    return {}
                },
            }
    )

    app.component("TableItem",
            {
                template: `
                  <td>
                    {{ workingYear }}
                    <input type="hidden" name="occrrncYear" v-bind:value="workingYear" title="발생 연도"/>
                  </td>
                  <td>{{ yrycItem.mberNm }}</td>
                  <td>
                    <input v-if="workingYear" type="text" name="occrncYrycCo" size="10" maxlength="10" title="발생 연차"
                           v-on:keyup.enter="preventSubmit" v-model:.number="occrncYrycCo"/>
                  </td>
                  <td>
                    <input v-if="workingYear" type="text" name="uYrycCo" size="10" maxlength="10" title="사용 연차"
                           v-on:keyup.enter="preventSubmit" v-model:.number="uYrycCo"/>
                  </td>
                  <td><span v-if="workingYear">{{ remndrYrycCo }}</span></td>
                  <td>
                    <button v-if="workingYear" v-on:click.prevent="insertYrycManage">저장</button>
                    <button v-if="workingYear" v-on:click.prevent="deleteYrycManage">삭제</button>
                  </td>`
                ,
                props: ["yrycItem", "selectedYear"],
                setup(props) {

                    let uYrycCo = ref("")
                    let occrncYrycCo = ref("")
                    let remndrYrycCo = ref("")

                    const workingYear = computed(() => {
                        return props.yrycItem.occrrncYear ?? props.selectedYear
                    })

                    const preventSubmit = (event) => {
                        if (event.key === 'Enter') {
                            alert("enter");
                            event.preventDefault()
                        }
                    }

                    onMounted(
                            () => {
                                uYrycCo.value = props.yrycItem.useYrycCo
                                occrncYrycCo.value = props.yrycItem.occrncYrycCo
                                remndrYrycCo.value = props.yrycItem.remndrYrycCo
                            }
                    )

                    // 연차 등록/수정
                    const insertYrycManage = async () => {

                        const url = "/dty/hnr/yrc/yryc-manage"

                        const form = new FormData()
                        form.append("useYrycCo", uYrycCo.value)
                        form.append("mberId", props.yrycItem.usid)
                        // form.append("occrrncYear", workingYear.value)
                        form.append("occrrncYear", "")
                        form.append("occrncYrycCo", occrncYrycCo.value)

                        const entries = form.entries()
                        for (const pair of entries) {
                            console.log(pair[0] + '=' + pair[1])
                            // if (!pair[1]) {
                            //     if (pair[0] === 'useYrycCo') {
                            //         alert("사용 연차를 입력해 주세요")
                            //     } else if (pair[0] === 'occrrncYear') {
                            //         alert("발생 연도를 입력해 주세요")
                            //     } else if (pair[0] === 'occrncYrycCo') {
                            //         alert("발생 연차를 입력해 주세요")
                            //     } else if (pair[0] === 'mberId') {
                            //         alert("사용자 정보가 없습니다.")
                            //     }
                            //     return
                            // }
                        }

                        // if ((occrncYrycCo.value - uYrycCo.value) < 0) {
                        //     alert("잔여 연차가 음수일 수 없습니다.")
                        //     return
                        // }

                        try {
                            const data = await axios.post(url, form)
                            alert(data.data.msg)
                            remndrYrycCo.value = data.data.datas[0].remndrYrycCo
                        } catch (error) {
                            console.log('error -> ', error)
                            alert(data.data.msg)
                        }
                    }

                    // 연차 삭제
                    const deleteYrycManage = async () => {

                        const form = new FormData()
                        form.append("mberId", props.yrycItem.usid)
                        form.append("occrrncYear", workingYear.value)

                        const entries = form.entries()
                        for (const pair of entries) {
                            console.log(pair[0] + '=' + pair[1])
                            if (!pair[1]) {
                                if (pair[0] === 'occrrncYear') {
                                    alert("발생 연도를 입력해 주세요")
                                } else if (pair[0] === 'mberId') {
                                    alert("사용자 정보가 없습니다.")
                                }
                                return
                            }
                        }

                        try {
                            const url = "/dty/hnr/yrc/yryc-manage/delete"
                            const data = await axios.post(url, form)
                            remndrYrycCo.value = data.data.datas[0].remndrYrycCo
                            occrncYrycCo.value = data.data.datas[0].occrncYrycCo
                            uYrycCo.value = data.data.datas[0].useYrycCo
                            alert(data.data.msg)
                        } catch (error) {
                            console.log('error -> ', error)
                            alert(data.data.msg)
                        }
                    }

                    return {
                        uYrycCo,
                        workingYear,
                        occrncYrycCo,
                        remndrYrycCo,
                        preventSubmit,
                        insertYrycManage,
                        deleteYrycManage,
                    }
                },
            }
    )

    app.mount('#app')

</script>

</body>
</html>
