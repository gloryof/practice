var ScriptVars = Java.type('org.zaproxy.zap.extension.script.ScriptVars');

function extractWebSession(sessionWrapper) {
    print("start extractWebSession")
    var csrfToken = ScriptVars.getGlobalVar("csrf_token");
    var accessToken = ScriptVars.getGlobalVar("access_token");
    sessionWrapper.getSession().setValue("csrf_token", csrfToken);
    sessionWrapper.getSession().setValue("access_token", accessToken);
}

function clearWebSessionIdentifiers(sessionWrapper) {
}

function processMessageToMatchSession(sessionWrapper) {
    var csrfToken = sessionWrapper.getSession().getValue("csrf_token");
    var accessToken = sessionWrapper.getSession().getValue("access_token");
    var msg = sessionWrapper.getHttpMessage();
    msg.getRequestHeader().setHeader("X-CSRF-TOKEN", csrfToken);
    msg.getRequestHeader().setHeader("Authorization", "Bearer " + accessToken);
}

function getRequiredParamsNames() {
    return [];
}

function getOptionalParamsNames() {
    return [];
}
