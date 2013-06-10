package de.gedoplan.buch.eedemos.provs.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet-Filter zum Setzen des Encodings von Request und Response.
 * 
 * Diese Anwendung nutzt UTF-8 für die Kodierung der Webseiten. Dies ist im globalen Template auch so eingetragen. Trotzdem werden
 * Eingabewerte nicht mit UTF-8 angenommen, so dass bswl. Umlaute verfälscht werden. Dieser Filter setzt das Encoding nochmals
 * explizit.
 * 
 * @author dw
 */
@WebFilter(urlPatterns = "/*")
public class Utf8Filter implements Filter
{
  // private static final Log LOGGER = LogFactory.getLog(Utf8Filter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    // if (LOGGER.isTraceEnabled())
    // {
    // LOGGER.trace("Request-Encoding:" + request.getCharacterEncoding() + ", Response-Encoding:" +
    // response.getCharacterEncoding());
    // }

    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException
  {
  }

  @Override
  public void destroy()
  {
  }
}
