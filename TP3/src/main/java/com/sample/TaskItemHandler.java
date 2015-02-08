package com.sample;

import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class TaskItemHandler implements WorkItemHandler {
	private WorkItemManager workItemManager;
    private long workItemId;
	
    @Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		this.workItemId = workItem.getId();
		this.workItemManager = workItemManager;
		System.out.println("UserTask:Execute: Work process instance = " + workItem.getProcessInstanceId());
		System.out.println("UserTask:Execute: Map of Parameters = " + workItem.getParameters());
	}

    @Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
	}

    public void completeWorkItem(Map<String, Object> parameters) {
		System.out.println("UserTask:Completed");
		this.workItemManager.completeWorkItem(this.workItemId, parameters);
	}
}
