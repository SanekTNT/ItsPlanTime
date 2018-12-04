<#import "partsOfCode/common.ftl" as common>
<#import "partsOfCode/logging.ftl" as login>

<@common.page>
    <#if message??>
    <div class="alert alert-danger" role="alert">
        ${message}
    </div>
    </#if>
    <@login.login "/registration" />
</@common.page>