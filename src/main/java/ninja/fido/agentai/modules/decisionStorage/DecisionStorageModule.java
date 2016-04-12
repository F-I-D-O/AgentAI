/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.modules.decisionStorage;

import ninja.fido.agentai.UnitDecisionSetting;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.modules.decisionMaking.DecisionModule;
import ninja.fido.agentai.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.modules.decisionMaking.DecisionTable;
import ninja.fido.agentai.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.modules.decisionMaking.DecisionTablesMapParameter;
import ninja.fido.agentai.modules.learning.LearningModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import ninja.fido.agentai.base.GameApiModule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionStorageModule implements GameApiModule{
	private final Map<String,StorableDecisionModuleActivity> registeredActivities;
	
	private final Map<String,DecisionTablesMapParameter> registeredDecisionParameters;
	
	private final DecisionModule decisionModule;

	
	
	
	public DecisionStorageModule(DecisionModule decisionModule) {
		registeredActivities = new HashMap<>();
		registeredDecisionParameters = new HashMap<>();
		this.decisionModule = decisionModule;
	}
	
	
	
	
	public void registerActivity(StorableDecisionModuleActivity activity){
		registeredActivities.put(activity.getId(), activity);
	}
	
	public void registerParameter(DecisionTablesMapParameter parameter){
		registeredDecisionParameters.put(parameter.getId(), parameter);
	}
	
	public DecisionTablesMapParameter getParameter(String parameterId){
		return registeredDecisionParameters.get(parameterId);
	}
	
	public StorableDecisionModuleActivity getActivity(String activityId){
		return registeredActivities.get(activityId);
	}
	
	private void loadSettings() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException{
		File file = new File(LearningModule.LEARNED_DECISIONS_FILE);
		if(file.exists()){
			ArrayList<UnitDecisionSetting> unitDecisionSettings = new ArrayList<>();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(LearningModule.LEARNED_DECISIONS_FILE);

			Element decisionSettingsNode = document.getDocumentElement();

			for (int resultIndex = 0; resultIndex < decisionSettingsNode.getChildNodes().getLength(); resultIndex++) {
				Node childNode = decisionSettingsNode.getChildNodes().item(resultIndex);
				if(childNode.getNodeName().equals("decisionTablesMap")){
					Element decisionTablesMapElement = (Element) childNode;
					unitDecisionSettings.add(parseDecisionTablesMap(decisionTablesMapElement));
				}
			}

			for (UnitDecisionSetting unitDecisionSetting : unitDecisionSettings) {
				decisionModule.addDecisionTablesMap(unitDecisionSetting);
			}
		}
	}
	
	public UnitDecisionSetting parseDecisionTablesMap(Element decisionTablesMapElement) throws ClassNotFoundException {
		Class<? extends Agent> agentClass
						= (Class<? extends Agent>) Class.forName(decisionTablesMapElement.getAttribute("agent"));

		HashMap<DecisionTablesMapKey,DecisionTable> decisionTablesMap = new HashMap<>();

		for (int entryIndex = 0; entryIndex < decisionTablesMapElement.getChildNodes().getLength(); entryIndex++) {
			Node childNode = decisionTablesMapElement.getChildNodes().item(entryIndex);
			if(childNode.getNodeName().equals("entry")){
				Element entryElement = (Element) childNode;
				DecisionTablesMapKey decisionTablesMapKey = null;
				DecisionTable decisionTable = null;
				for (int keyValueIndex = 0; keyValueIndex < entryElement.getChildNodes().getLength(); keyValueIndex++) {
					childNode = entryElement.getChildNodes().item(keyValueIndex);
					if(childNode.getNodeName().equals("key")){
						Element keyNode = (Element) childNode;
						decisionTablesMapKey = parseKey(keyNode);
					}
					if(childNode.getNodeName().equals("table")){
						Element decisionTableNode = (Element) childNode;
						decisionTable = parseDecisionTable(decisionTableNode);
					}
				}
				
				
				decisionTablesMap.put(decisionTablesMapKey, decisionTable);
			}	
		}
		return new UnitDecisionSetting(agentClass, decisionTablesMap);
	}
	
	private DecisionTable parseDecisionTable(Element decisionTableNode) {
		TreeMap<Double,DecisionModuleActivity> probabilities = new TreeMap<>();
		for (int rowIndex = 0; rowIndex < decisionTableNode.getChildNodes().getLength(); rowIndex++) {
			Node childNode = decisionTableNode.getChildNodes().item(rowIndex);
			if(childNode.getNodeName().equals("row")){
				Element rowElement = (Element) childNode;
				double probability = Double.parseDouble(rowElement.getAttribute("probability"));
				StorableDecisionModuleActivity activity = null;
				for (int actionIndex = 0; actionIndex < rowElement.getChildNodes().getLength(); actionIndex++) {
					childNode = rowElement.getChildNodes().item(actionIndex);
					if(childNode.getNodeType() == Node.ELEMENT_NODE){
						Element activityElement = (Element) childNode;
						activity = getActivity(activityElement.getNodeName());
						activity = activity.getFromXml(activityElement);
					}
				}
				probabilities.put(probability, activity);
			}
		}
		return new DecisionTable(probabilities);
	}
	
	private DecisionTablesMapKey parseKey(Element keyNode) {
		DecisionTablesMapKey decisionTablesMapKey = new DecisionTablesMapKey();
		for (int parameterIndex = 0; parameterIndex < keyNode.getChildNodes().getLength(); parameterIndex++) {
			Node childNode = keyNode.getChildNodes().item(parameterIndex);
			if(childNode.getNodeType() == Node.ELEMENT_NODE){
				Element parameterElement = (Element) childNode;
				decisionTablesMapKey.addParameter(parseParameter(parameterElement));
			}
		}
		return decisionTablesMapKey;
	}
	
	private DecisionTablesMapParameter parseParameter(Element parameterElement) {
		String parameterName = parameterElement.getNodeName();
		return getParameter(parameterName).createFromXml(parameterElement);
	}

	@Override
	public void onRun() {
		try {
			loadSettings();
		} catch (ParserConfigurationException | SAXException | IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onEnd(boolean winner, int score) {
		
	}

	@Override
	public void onStart(int gameCount) {
		
	}
}
