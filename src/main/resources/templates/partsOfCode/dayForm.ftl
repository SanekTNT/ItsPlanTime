<#macro toDayForm dayName date time subject text alreadySent files=[""]>
    <#if time == "--:--">
        <@dayFormWithoutValues "${dayName}" "${date}" "${time}" "${subject}" "${text}"/>
    <#else>
        <@dayFormWithValues "${dayName}" "${date}" "${time}" "${subject}" "${text}" "${alreadySent}" files />
    </#if>
</#macro>

<#macro dayFormWithoutValues dayName date time subject text>
<form name="${dayName}" method="post" enctype="multipart/form-data">
    <input type="hidden" name="_method" value="put">
    <center><label><h4>${dayName}</h4></label></center>
    <div class="my-row">
        <label> Date <input type="date" name="date" required readonly value="${date}"> </label>
        <label> Time <input type="time" name="time" required value="${time}" </label>
    </div>
    <label>Subject
        <br><input type="text" name="subject" size="53" required value="${subject}">
    </label>
    <br><label>Message
    <br><textarea rows="12" cols="55" name="message" required>${text}</textarea>
</label>
    <br>
    <center><label>
        <input type="file" name="file" multiple>
    </label></center>
    <center>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="reset" class="btn btn-outline-primary">Reset</button>
        <button type="submit" class="btn btn-outline-primary">Submit</button>
        <br><br>
    </center>
</form>
</#macro>

<#macro dayFormWithValues dayName date time subject text alreadySent files=[""]>
<form name="${dayName}" method="post">
    <input type="hidden" name="_method" value="delete">
    <center><label><h4>${dayName}</h4></label></center>
    <div class="my-row">
        <label> Date <input type="date" name="date" readonly value="${date}"> </label>
        <label> Time <input type="time" name="time" value="${time}" </label>
    </div>
    <label>Subject
        <br><input type="text" name="subject" size="53" readonly value="${subject}">
    </label>
    <br><label>Message
    <br><textarea rows="10" cols="55" name="message" readonly>${text}</textarea>
</label>
    <br>Files
    <br><textarea rows="2" cols="55" name="files" readonly><#if files?first = "">None<#else>${files?join(", ")}</#if>
</textarea>
    <center>
        <#if alreadySent == "false">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit" class="btn btn-outline-primary">Revoke</button>
        <#else>
            <label><h6>The message has already been sent.</h6></label>
        </#if>
        <br><br>
    </center>
</form>
</#macro>