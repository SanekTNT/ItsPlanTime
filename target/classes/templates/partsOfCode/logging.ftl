<#macro login path>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Username </label>
        <div class="col-sm-5">
            <input type="text" name="username" class="form-control"/>
        </div>
    </div>
    <#if path = "/registration">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Email </label>
            <div class="col-sm-5">
                <input type="email" name="email" class="form-control"/>
            </div>
        </div>
    </#if>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Password </label>
        <div class="col-sm-5">
            <input type="password" name="password" class="form-control"/>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">
    <#if path = "/registration">
        Create user
        </button>
    <#else>
        Sign In
        </button>
    <a class="btn btn-primary" href="/registration" role="button">Create new user</a>
    </#if>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="submit" value="Sign Out" class="btn btn-primary"/>
</form>
</#macro>