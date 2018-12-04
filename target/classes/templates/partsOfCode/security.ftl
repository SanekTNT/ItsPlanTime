<#assign
isUser = Session.SPRING_SECURITY_CONTEXT??
>

<#if isUser>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    >
<#else>
    <#assign
    name = "unknown"
    >
</#if>