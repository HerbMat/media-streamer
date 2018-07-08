<#include "../base_template.ftl">
<#macro page_title>
    <title><@spring.messageText "page.title.upload" "S.M.P Upload"/></title>
</#macro>
<#macro page_body>
    <div class="container text-center">
        <h1>Upload file</h1>
        <form action="<@spring.url '/upload'/>" method="post" name="uploadForm" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="custom-file">
                <input name="mediaFile" type="file" class="custom-file-input" id="mediaFile">
                <label class="custom-file-label" for="mediaFile"><@spring.messageText "upload.media.label" "Choose file"/></label>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</#macro>
<@page_template/>