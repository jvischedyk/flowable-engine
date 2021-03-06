package org.activiti.engine.impl.history.handler;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.flowable.engine.delegate.DelegateTask;
import org.flowable.engine.delegate.TaskListener;


/**
 * Called when a task is created for a user-task activity. Allows recording task-id in
 * historic activity.
 * 
 * @author Frederik Heremans
 */
public class UserTaskIdHandler implements TaskListener {

  public void notify(DelegateTask task) {
    Context.getCommandContext().getHistoryManager()
      .recordTaskId((TaskEntity) task);
  }
  
}
