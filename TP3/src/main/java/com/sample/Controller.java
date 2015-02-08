package com.sample;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;

public class Controller implements Initializable {

	@FXML private ListView<WorkflowProcessInstance> lvInstance;
	@FXML private Label labelInfo;
	@FXML private HBox paneButton;
	@FXML private Button startButton;

	private ObservableList<WorkflowProcessInstance> lvData = FXCollections.observableArrayList();
	private WorkflowProcessInstance processInstance;
	private TaskItemHandler itemHandler;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		lvInstance.setItems(lvData);
		
		//Recupere la session
		KieSession ksession = Manager.getSession();

		itemHandler = new TaskItemHandler();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", itemHandler);
		ksession.getWorkItemManager().registerWorkItemHandler("Manual Task", itemHandler);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
			public void handle(ActionEvent e) {
    	    	processInstance = Manager.getProcessInstance(true);
    	    	lvData.add(processInstance);
    	    	nextState();
    	    }
    	});
		
		for (final Node btn : paneButton.getChildren()) {
			((Button) btn).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					itemHandler.completeWorkItem(null);
					nextState();
				}
			});
		}
	}
	
	private void nextState() {
		// reset button
		disableAllButton();
		
		if (processInstance.getNodeInstances().size() == 0) {
			// finish state
			labelInfo.setText("Finish");
		} else {
			String info = "";
			Iterator<NodeInstance> nodes = processInstance.getNodeInstances().iterator();
			while(nodes.hasNext()){
				NodeInstance node = (NodeInstance) nodes.next();
				info = info+" ou "+node.getNodeName();
				enableButton(node.getNodeName());
			}
			labelInfo.setText(info.substring(4));
		}
	}

	private void disableAllButton() {
		for (Node btn : paneButton.getChildren()) {
			btn.setDisable(true);
		}
	}
	
	private void enableButton(String nodeName) {
		for (Node btn : paneButton.getChildren()) {
			if(((Button) btn).getText().equals(nodeName)){
				btn.setDisable(false);
			}
		}
	}
}