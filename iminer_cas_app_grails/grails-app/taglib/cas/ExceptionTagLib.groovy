package cas

import org.grails.encoder.CodecLookup
import org.grails.encoder.Encoder
import org.grails.exceptions.ExceptionUtils
import org.grails.web.errors.ErrorsViewStackTracePrinter
import org.grails.web.util.WebUtils
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils

/**
 * @Description: ${todo}
 * @author fangjie
 * @date 2018/7/2 11:38
 */
class ExceptionTagLib {

    ErrorsViewStackTracePrinter errorsViewStackTracePrinter
    CodecLookup codecLookup

    static namespace = "cas"

    def exception_resolver = {Map attrs ->

        Throwable exception = (Throwable)attrs.exception
        def root = ExceptionUtils.getRootCause(exception)
        def exceptionClassName = root?.getClass()?.name ?: exception.getClass().name
        if (!(attrs?.exception instanceof Throwable)) {
            return
        }
        //权限异常处理
        if("org.apache.shiro.authz.AuthorizationException".equals(exceptionClassName)){
            out << "权限异常"
            return
        }

        Encoder htmlEncoder = codecLookup.lookupEncoder('HTML')

        def currentOut = out
        int statusCode = request.getAttribute('javax.servlet.error.status_code') as int
        currentOut << """<h1>Error ${prettyPrintStatus(statusCode)}</h1>
<dl class="error-details">
<dt>URI</dt><dd>${htmlEncoder.encode(WebUtils.getForwardURI(request) ?: request.getAttribute('javax.servlet.error.request_uri'))}</dd>
"""

        currentOut << "<dt>Class</dt><dd>${root?.getClass()?.name ?: exception.getClass().name}</dd>"
        currentOut << "<dt>Message</dt><dd>${htmlEncoder.encode(exception.message)}</dd>"
        if (root != null && root != exception && root.message != exception.message) {
            currentOut << "<dt>Caused by</dt><dd>${htmlEncoder.encode(root.message)}</dd>"
        }
        currentOut << "</dl>"

        currentOut << errorsViewStackTracePrinter.prettyPrintCodeSnippet(exception)

        def trace = errorsViewStackTracePrinter.prettyPrint(exception.cause ?: exception)
        if (StringUtils.hasText(trace.trim())) {
            currentOut << "<h2>Trace</h2>"
            currentOut << '<pre class="stack">'
            currentOut << htmlEncoder.encode(trace)
            currentOut << '</pre>'
        }
    }


    private String prettyPrintStatus(int statusCode) {
        String httpStatusReason = HttpStatus.valueOf(statusCode).getReasonPhrase()
        "$statusCode: ${httpStatusReason}"
    }
}
