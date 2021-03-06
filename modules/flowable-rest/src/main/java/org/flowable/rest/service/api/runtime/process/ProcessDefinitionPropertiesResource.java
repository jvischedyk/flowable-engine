/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flowable.rest.service.api.runtime.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.flowable.engine.FormService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.impl.form.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Tijs Rademakers
 */
@RestController
@Api(tags = { "Process Definitions" }, description = "Manage Process Definitions")
public class ProcessDefinitionPropertiesResource {

  @Autowired
  protected FormService formService;

  @Autowired
  protected ObjectMapper objectMapper;

  //FIXME
 /* @ApiOperation(value = "Get start form properties", tags = {"Process Definitions"})
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Indicates request was successful and the properties are returned"),
          @ApiResponse(code = 404, message = "Indicates the requested process definition was not found.")
  })
  @RequestMapping(value = "/process-definition/{processDefinitionId}/properties", method = RequestMethod.GET, produces = "application/json")*/
  public ObjectNode getStartFormProperties(@ApiParam(name = "processDefinitionId") @PathVariable String processDefinitionId) {
    StartFormData startFormData = formService.getStartFormData(processDefinitionId);

    ObjectNode responseJSON = objectMapper.createObjectNode();

    ArrayNode propertiesJSON = objectMapper.createArrayNode();

    if (startFormData != null) {

      List<FormProperty> properties = startFormData.getFormProperties();

      for (FormProperty property : properties) {
        ObjectNode propertyJSON = objectMapper.createObjectNode();
        propertyJSON.put("id", property.getId());
        propertyJSON.put("name", property.getName());

        if (property.getValue() != null) {
          propertyJSON.put("value", property.getValue());
        } else {
          propertyJSON.putNull("value");
        }

        if (property.getType() != null) {
          propertyJSON.put("type", property.getType().getName());

          if (property.getType() instanceof EnumFormType) {
            @SuppressWarnings("unchecked")
            Map<String, String> valuesMap = (Map<String, String>) property.getType().getInformation("values");
            if (valuesMap != null) {
              ArrayNode valuesArray = objectMapper.createArrayNode();
              propertyJSON.set("enumValues", valuesArray);

              for (String key : valuesMap.keySet()) {
                ObjectNode valueJSON = objectMapper.createObjectNode();
                valueJSON.put("id", key);
                valueJSON.put("name", valuesMap.get(key));
                valuesArray.add(valueJSON);
              }
            }
          }

        } else {
          propertyJSON.put("type", "String");
        }

        propertyJSON.put("required", property.isRequired());
        propertyJSON.put("readable", property.isReadable());
        propertyJSON.put("writable", property.isWritable());

        propertiesJSON.add(propertyJSON);
      }
    }

    responseJSON.set("data", propertiesJSON);
    return responseJSON;
  }
}
