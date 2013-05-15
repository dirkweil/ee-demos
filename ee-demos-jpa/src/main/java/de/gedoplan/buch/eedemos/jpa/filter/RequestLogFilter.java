package de.gedoplan.buch.eedemos.jpa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet-Filter zum Loggen von Requests.
 * 
 * @author dw
 */
@WebFilter(urlPatterns = "/*")
public class RequestLogFilter implements Filter
{
  private static final Log LOGGER = LogFactory.getLog(RequestLogFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    if (LOGGER.isTraceEnabled())
    {
      LOGGER.trace("--------------------------------------------------------------------------------");
    }

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
