<#import "partsOfCode/common.ftl" as c>
<@c.page>
<center>
    <h5>
        <br>Don't have enough time to learning? Piece of cake!
        <br>"It's plan time!" - simple program for optimization your personal time.
        <br>With it you will stop waste your precious time and begin get new information everyday.
        <br>Let's start!
        <br>
        <br>
        <#if user??>
            <a class="btn btn-outline-primary" href="/schedule" role="button">Schedule</a>
        <#else>
            <a class="btn btn-outline-primary" href="/login" role="button">Sign in</a>
        </#if>
    </h5>
</center>

</@c.page>