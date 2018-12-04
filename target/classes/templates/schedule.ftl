<#import "partsOfCode/common.ftl" as common>
<#import "partsOfCode/dayForm.ftl" as dayForm>

<@common.page>
    <#if alert??>
    <div class="alert alert-danger" role="alert">
        ${alert}
    </div>
    </#if>
<div class="my-row">
    <#list 0..2 as i>
        <div class="item${i}">
            <#if files[dates[i]]?has_content>
                <@dayForm.toDayForm "${daysNames[i]}" "${dates[i]}" "${messages[i].time}"
                "${messages[i].subject}" "${messages[i].text}" "${messages[i].alreadySent?c}" files[dates[i]] />
            <#else>
                <@dayForm.toDayForm "${daysNames[i]}" "${dates[i]}" "${messages[i].time}"
                "${messages[i].subject}" "${messages[i].text}" "${messages[i].alreadySent?c}" />
            </#if>
        </div>
    </#list>
</div>
<br>
<div class="my-row">
    <#list 3..5 as i>
        <div class="item${i}">
            <@dayForm.toDayForm "${daysNames[i]}" "${dates[i]}" "${messages[i].time}"
            "${messages[i].subject}" "${messages[i].text}" "${messages[i].alreadySent?c}" />
        </div>
    </#list>
</div>
<br>
<div class="my-row">
    <#list 6..7 as i>
        <div class="item${i}">
            <@dayForm.toDayForm "${daysNames[i]}" "${dates[i]}" "${messages[i].time}"
            "${messages[i].subject}" "${messages[i].text}" "${messages[i].alreadySent?c}" />
        </div>
    </#list>
</div>
</@common.page>