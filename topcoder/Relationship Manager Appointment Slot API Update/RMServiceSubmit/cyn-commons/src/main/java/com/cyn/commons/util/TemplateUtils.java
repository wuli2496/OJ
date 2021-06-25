package com.cyn.commons.util;

import static com.cyn.commons.exception.ErrorCode.TMP_ERR000001;

import com.cyn.commons.exception.BaseServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/** Utility for processing message template files */
@ConditionalOnClass(freemarker.template.Template.class)
@Component
public class TemplateUtils {

  /** The root key for templates in JSON * */
  private static final String JSON_TEMPLATES_KEY = "pushTemplates";

  /** Template id field in JSON * */
  private static final String JSON_TEMPLATE_ID = "template_id";

  /** Freemaker template name * */
  private static final String DEFAULT_TEMPLATE_NAME = "name";

  /** Message field for a message in JSON * */
  private static final String JSON_MESSAGE_FIELD = "message";

  /**
   * Process a template and return formatted message
   *
   * @param gcsTemplate the template resource, can be gcp or any type of resource
   * @param message the message template to be processed
   * @param params the parameters for message template
   * @return
   */
  public String processTemplate(Resource gcsTemplate, String message, Map<String, String> params) {
    try (var is = gcsTemplate.getInputStream()) {
      var root = JsonUtils.OBJECT_MAPPER.readTree(is);
      for (var templateNode : root.get(JSON_TEMPLATES_KEY)) {
        if (message.equalsIgnoreCase(templateNode.get(JSON_TEMPLATE_ID).textValue())) {
          Template template =
              new Template(
                  DEFAULT_TEMPLATE_NAME,
                  new StringReader(templateNode.get(JSON_MESSAGE_FIELD).textValue()),
                  new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
          return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        }
      }
      return message;
    } catch (IOException | TemplateException e) {
      throw new BaseServiceException(TMP_ERR000001.toString(), Collections.emptyList(), e);
    }
  }
}
