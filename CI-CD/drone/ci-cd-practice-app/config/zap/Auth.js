
var HttpRequestHeader = Java.type('org.parosproxy.paros.network.HttpRequestHeader');
var HttpRequestBody = Java.type('org.zaproxy.zap.network.HttpRequestBody');
var URI = Java.type('org.apache.commons.httpclient.URI');
var HttpHeader = Java.type('org.parosproxy.paros.network.HttpHeader');
var ScriptVars = Java.type('org.zaproxy.zap.extension.script.ScriptVars');

function authenticate(helper, paramsValues, credentials) {
    print("start authenticate");
    var targetUrl = getTargetUrl(paramsValues);
    var csrfToken = getCsrfToken(helper, targetUrl);
    var msg = getAuthTokenMsg(helper, targetUrl, credentials, csrfToken);
    var result = JSON.parse(msg.getResponseBody().toString());
    ScriptVars.setGlobalVar("csrf_token", csrfToken);
    ScriptVars.setGlobalVar("access_token", result.tokenValue);
    print("end authenticate");
    return msg;
}

function getCsrfToken(helper, targetUrl) {
    var msg = helper.prepareMessage();
    var header = new HttpRequestHeader(HttpRequestHeader.POST, getCsrfUrl(targetUrl), HttpHeader.HTTP11);
    msg.setRequestHeader(header);
    helper.sendAndReceive(msg);
    return  msg.getResponseBody().toString();
}

function getAuthTokenMsg(helper, targetUrl, credentials, csrfToken) {
    var msg = helper.prepareMessage();
    var header = new HttpRequestHeader(HttpRequestHeader.POST, getAuthUrl(targetUrl), HttpHeader.HTTP11);
    header.setHeader("X-CSRF-TOKEN", csrfToken);
    header.setHeader("Content-Type", "application/json");
    var request = {
        "userId": credentials.getParam("username"),
        "password": credentials.getParam("password"),
    }
    var body = new HttpRequestBody(JSON.stringify(request));

    msg.setRequestHeader(header);
    msg.setRequestBody(body);
    helper.sendAndReceive(msg);
    return msg;
}

function getRequiredParamsNames(){
    return ["target-host", "target-port"];
}

function getOptionalParamsNames(){
    return [""];
}

function getCredentialsParamsNames(){
    return ["username", "password"];
}

function getTargetUrl(paramsValues) {
    return "http://" + paramsValues.get("target-host") + ":" + paramsValues.get("target-port");
}

function getCsrfUrl(targetUrl) {
    var url = targetUrl + "/csrf/token";
    return new URI(url);
}

function getAuthUrl(targetUrl) {
    var url = targetUrl + "/authenticate";
    return new URI(url);
}