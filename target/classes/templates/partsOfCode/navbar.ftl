<#include "security.ftl">
<#import "logging.ftl" as login>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">It's plan time!</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
        <#if user??>
            <li class="nav-item">
                <a class="nav-link" href="/schedule">Schedule</a>
            </li>
        </#if>
        </ul>
        <div class="navbar-text mr-3">${name}</div>
    <#if user??>
        <@login.logout />
    </#if>
    </div>
</nav>
