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
package org.flowable.engine.impl.event.logger;

import java.util.List;

import org.flowable.engine.impl.event.logger.handler.EventLoggerEventHandler;
import org.flowable.engine.impl.interceptor.CommandContextCloseListener;

/**
 * @author Joram Barrez
 */
public interface EventFlusher extends CommandContextCloseListener {

  List<EventLoggerEventHandler> getEventHandlers();

  void setEventHandlers(List<EventLoggerEventHandler> eventHandlers);

  void addEventHandler(EventLoggerEventHandler databaseEventLoggerEventHandler);

}
