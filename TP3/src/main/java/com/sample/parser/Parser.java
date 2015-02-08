package com.sample.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

	private Boolean disableStartEvent = false;
	private Map<String,String> listFxmlButton = new HashMap<String,String>();

	public void parseBPMN() throws Exception {
		File bpmnFile = new File(getClass().getClassLoader().getResource("sample.bpmn").getPath());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(bpmnFile);
		doc.getDocumentElement().normalize();
		
		disableStartEvent = (doc.getElementsByTagName("bpmn2:startEvent") == null);
		
		NodeList nList = doc.getElementsByTagName("bpmn2:userTask");
		for (int i = 0; i < nList.getLength(); i++) {
			Node userNode = nList.item(i);

			String id = userNode.getAttributes().getNamedItem("id").getNodeValue();
			String name = userNode.getAttributes().getNamedItem("name").getNodeValue();
			listFxmlButton.put(id, name);
		}
		
		nList = doc.getElementsByTagName("bpmn2:manualTask");
		for (int i = 0; i < nList.getLength(); i++) {
			Node userNode = nList.item(i);

			String id = userNode.getAttributes().getNamedItem("id").getNodeValue();
			String name = userNode.getAttributes().getNamedItem("name").getNodeValue();
			listFxmlButton.put(id, name);
		}
	}

	public void parseFXML() throws Exception {
		File fxmlFile = new File(getClass().getClassLoader().getResource("gui.fxml").getPath());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fxmlFile);
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("Button");
		for (int i = 0; i < nList.getLength(); i++) {
			Element buttonNode = (Element) nList.item(i);
			if (buttonNode.getAttributes().getNamedItem("fx:id").getNodeValue().equals("startButton")) {
				buttonNode.setAttribute("disable", disableStartEvent.toString());
			}
		}
		
		nList = doc.getElementsByTagName("HBox");
		for (int i = 0; i < nList.getLength(); i++) {
			Node paneNode = nList.item(i);
			if (paneNode.getAttributes().getNamedItem("fx:id").getNodeValue().equals("paneButton")) {
				appendFxmlButton(doc, paneNode);
			}
		}

		saveDocument(doc);
	}

	private void appendFxmlButton(Document doc, Node parent) {
		Element child = doc.createElement("children");
		for (Entry<String, String> entry : listFxmlButton.entrySet()) {
			String id = entry.getKey();
			String name = entry.getValue();

			Element button = doc.createElement("Button");
			button.setAttribute("fx:id", id);
			button.setAttribute("text", name);
			button.setAttribute("disable", "true");

			child.appendChild(button);
		}
		parent.appendChild(child);
	}

	private void saveDocument(Document doc) {
		try {
			File f = new File("guiImpl.fxml");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
//			StreamResult result2 = new StreamResult(System.out);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
//			transformer.transform(source, result2);

			
			System.out.println("File saved!");
		} catch (Exception e) {
			System.err.println("File not saved!");
			e.printStackTrace();
		}
	}
}
