package com.cyn.commons.security;

import static com.cyn.commons.exception.ErrorCode.ATH_ERR000004;
import static com.cyn.commons.exception.ErrorCode.ATH_ERR000006;

import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.exception.BaseUnauthorizedException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

/** Payload decryptor for decrypting payload */
@Slf4j
@ControllerAdvice
public class CynBodyAuthorizer extends RequestBodyAdviceAdapter {

  /** The roles allowed to call the endpoint without matching cui. */
  @Value("${roles.bypass.cui.check:ROLE_Application/cyn-authentication}")
  private List<String> serviceRoles;

  /**
   * Invoked first to determine if this interceptor applies.
   *
   * @param methodParameter the method parameter
   * @param targetType the target type, not necessarily the same as the method parameter type, e.g.
   *     for {@code HttpEntity<String>}.
   * @param converterType the selected converter type
   * @return whether this interceptor should be invoked or not
   */
  @Override
  public boolean supports(
      MethodParameter methodParameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return methodParameter.getMethodAnnotation(CynBodyAuthorization.class) != null;
  }

  /**
   * The implementation for checking the body content.
   *
   * @param body request body as decrypted.
   * @param inputMessage HttpInputMessage belongs to the request.
   * @param parameter Method parameters.
   * @param targetType target object type.
   * @param converterType body converted used.
   * @return the evaluated and authorized request body.
   */
  @Override
  public Object afterBodyRead(
      Object body,
      HttpInputMessage inputMessage,
      MethodParameter parameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    List<String> authorities;
    String customerIdBody;
    try {
      CynBodyAuthorization methodAnnotation =
          parameter.getMethodAnnotation(CynBodyAuthorization.class);
      if (methodAnnotation == null) {
        return body;
      }
      String customerUniqueIdPath = methodAnnotation.customerUniqueIdPath();
      PropertyUtilsBean pub = new PropertyUtilsBean();

      customerIdBody = (String) pub.getProperty(body, customerUniqueIdPath);
      authorities =
          SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
              .map(this::getAuthority)
              .collect(Collectors.toList());

    } catch (Exception e) {
      throw new BaseServiceException(ATH_ERR000006.toString(), Collections.emptyList(), e);
    }
    String customerId =
        (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (CollectionUtils.containsAny(authorities, serviceRoles)
        || (customerId.equals(customerIdBody))) {
      return body;
    } else {
      throw new BaseUnauthorizedException(ATH_ERR000004.toString(), Collections.emptyList());
    }
  }

  /**
   * Get authority from spring granted authority.
   *
   * @param grantedAuthority Spring granted authority.
   * @return plain authority.
   */
  private String getAuthority(org.springframework.security.core.GrantedAuthority grantedAuthority) {
    return grantedAuthority.getAuthority();
  }
}
