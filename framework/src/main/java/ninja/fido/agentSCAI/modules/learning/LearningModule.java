/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.learning;

import ninja.fido.agentSCAI.modules.decisionStorage.DecisionStorageModule;
import ninja.fido.agentSCAI.GameResult;
import ninja.fido.agentSCAI.UnitDecisionSetting;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapParameter;
import ninja.fido.agentSCAI.modules.decisionStorage.StorableDecisionModuleActivity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import ninja.fido.agentSCAI.base.GameAPIModule;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModule;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author F.I.D.O.
 */
public class LearningModule implements GameAPIModule{
	
	private static final String RESULTS_FILE = "results.xml";
	
	public static final String LEARNED_DECISIONS_FILE = "leaarned.xml";
	
	
	private final DecisionModule decisionModule;
	
	private final DecisionStorageModule decisionStorageModule;
	
	private final LearningEngine learningEngine;
	
	private final ArrayList<Class<? extends Agent>> agentClassesTrackedForLearning;
	
	private final GameAPI gameAPI;
	
	private LearningScenario learningScenario;

	
	
	
	public LearningScenario getLearningScenario() {
		return learningScenario;
	}

	public void setLearningScenario(LearningScenario learningScenario) {
		this.learningScenario = learningScenario;
	}
	
	
	

	public LearningModule(GameAPI gameAPI, DecisionModule decisionModule, DecisionStorageModule decisionStorageModule) {
		this.decisionModule = decisionModule;
		this.decisionStorageModule = decisionStorageModule;
//		learningEngine = new TestEngine();
		learningEngine = new SimpleEngine();
		agentClassesTrackedForLearning = new ArrayList<>();
		agentClassesTrackedForLearning.add(SquadCommander.class);
		this.gameAPI = gameAPI;
	}
	
	
	
	
	
	public void processResults() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, 
			TransformerException, TransformerConfigurationException, XPathExpressionException{
		ArrayList<GameResult> gameResults = getResultsFromXml();
		
		if(!gameResults.isEmpty()){
			List<UnitDecisionSetting> learnedUnitDecisionSettings = learningEngine.learnFromResults(gameResults);
			saveLearnedSettings(learnedUnitDecisionSettings);
		}
	}
	
	private void saveLearnedSettings(List<UnitDecisionSetting> learntUnitDecisionSettings) throws 
			ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, 
			TransformerException, XPathExpressionException {
		File file = new File(LEARNED_DECISIONS_FILE);
		boolean newFile = !file.exists();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document;
		if(newFile){
			DOMImplementation dOMImplementation = documentBuilder.getDOMImplementation();
			document = dOMImplementation.createDocument(null, "decisionSettings", null);
		}
		else{
			document = documentBuilder.parse(LEARNED_DECISIONS_FILE);
		}
		Element decisionSettingsNode = document.getDocumentElement();
		
		for (UnitDecisionSetting unitDecisionSetting : learntUnitDecisionSettings) {
			removeOldUnitDecisionSettingElement(document, unitDecisionSetting.getAgentClass().getName());
			Element unitDecisionSettingElement = createDecisionTablesMapNode(document, 
					unitDecisionSetting.getAgentClass().getName(), unitDecisionSetting.getDecisionTablesMap());
			decisionSettingsNode.appendChild(unitDecisionSettingElement);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(new DOMSource(document), new StreamResult(file));
	}
	
	private ArrayList<GameResult> getResultsFromXml() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
		ArrayList<GameResult> gameResults = new ArrayList<>();
		File file = new File(RESULTS_FILE);
		if(file.exists()){
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(RESULTS_FILE);

			Element resultsNode = document.getDocumentElement();

			for (int resultIndex = 0; resultIndex < resultsNode.getChildNodes().getLength(); resultIndex++) {
				Node childNode = resultsNode.getChildNodes().item(resultIndex);
				if(childNode.getNodeName().equals("result")){
					Element resultElement = (Element) childNode;
					gameResults.add(parseResult(resultElement));
				}
			}
		}
		return gameResults;
	}

	private GameResult parseResult(Element resultElement) throws ClassNotFoundException {
		boolean victory = Boolean.parseBoolean(resultElement.getAttribute("victory"));
		int score = Integer.parseInt(resultElement.getAttribute("score"));

		ArrayList<UnitDecisionSetting> unitDecisionSettings = new ArrayList<>();

		for (int decisionTablesMapIndex = 0; decisionTablesMapIndex < resultElement.getChildNodes().getLength(); 
				decisionTablesMapIndex++) {
			Node childNode = resultElement.getChildNodes().item(decisionTablesMapIndex);
			if(childNode.getNodeName().equals("decisionTablesMap")){
				Element decisionTablesMapElement = (Element) childNode;				
				unitDecisionSettings.add(decisionStorageModule.parseDecisionTablesMap(decisionTablesMapElement));
			}
		}
		return new GameResult(victory, score, unitDecisionSettings);
	}
	
	public void saveResult(boolean winner, int score) throws ParserConfigurationException, SAXException, IOException, 
			TransformerConfigurationException, TransformerException {

		// file existence chceck
		File file = new File(RESULTS_FILE);
		boolean newFile = !file.exists();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document;
		if(newFile){
			DOMImplementation dOMImplementation = documentBuilder.getDOMImplementation();
			document = dOMImplementation.createDocument(null, "results", null);
		}
		else{
			document = documentBuilder.parse(RESULTS_FILE);
		}
		Element resultsNode = document.getDocumentElement();
		Element resultNode = document.createElement("result");
		resultNode.setAttribute("victory", Boolean.toString(winner));
		resultNode.setAttribute("score", Integer.toString(score));

		for(Class agentClass : agentClassesTrackedForLearning){
			Agent agent = gameAPI.getAgent(agentClass);
			Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap = agent.getDecisionTablesMap();
			resultNode.appendChild(createDecisionTablesMapNode(document, agentClass.getName(), agent.getDecisionTablesMap()));		
		}

		resultsNode.appendChild(resultNode);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.transform(new DOMSource(document), new StreamResult(file));
	}

	private Element createDecisionTablesMapNode(Document document, String agentClassName, 
			Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap) {
		Element decisionTablesMapNode = document.createElement("decisionTablesMap");
		decisionTablesMapNode.setAttribute("agent", agentClassName);

		for (Map.Entry<DecisionTablesMapKey, DecisionTable> decisionTablesMapEntry 
				: decisionTablesMap.entrySet()) {
			DecisionTablesMapKey decisionTablesMapKey = decisionTablesMapEntry.getKey();
			DecisionTable decisionTablesMapValue = decisionTablesMapEntry.getValue();

			Element decisionTablesMapEntryNode = document.createElement("entry");

			Element decisionTablesMapKeyNode = document.createElement("key");

			HashMap<Class<DecisionTablesMapParameter>,DecisionTablesMapParameter> keyParametrs
					= decisionTablesMapKey.getKeyParametrs();
			for (Map.Entry<Class<DecisionTablesMapParameter>, DecisionTablesMapParameter> entry
					: keyParametrs.entrySet()) {
				Class<DecisionTablesMapParameter> decisionTablesMapParameterType = entry.getKey();
				DecisionTablesMapParameter decisionTablesMapParameter = entry.getValue();

				Element decisionTablesMapParameterElement = decisionTablesMapParameter.getXml(document);
				decisionTablesMapKeyNode.appendChild(decisionTablesMapParameterElement);
			}

			Element decisionTableNode = document.createElement("table");

			TreeMap<Double,DecisionModuleActivity> probabilities = decisionTablesMapValue.getProbabilities();
			for (Map.Entry<Double, DecisionModuleActivity> entry : probabilities.entrySet()) {
				Double probability = entry.getKey();
				StorableDecisionModuleActivity activity = (StorableDecisionModuleActivity) entry.getValue();

				Element decisionTableRowNode = document.createElement("row");
				decisionTableRowNode.setAttribute("probability", Double.toString(probability));

				Element actionNode = activity.getXml(document);
				decisionTableRowNode.appendChild(actionNode);

				decisionTableNode.appendChild(decisionTableRowNode);
			}


			decisionTablesMapEntryNode.appendChild(decisionTablesMapKeyNode);
			decisionTablesMapEntryNode.appendChild(decisionTableNode);
			decisionTablesMapNode.appendChild(decisionTablesMapEntryNode);		
		}
		return decisionTablesMapNode;
	}

	private void removeOldUnitDecisionSettingElement(Document document, String agentClass) throws XPathExpressionException {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("//decisionTablesMap[@agent=\"" + agentClass + "\"]");
		NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		
		for (int i = 0; i < nodeList.getLength(); i++) {			
			document.getDocumentElement().removeChild(nodeList.item(i));
		}
	}

	@Override
	public void onRun() {
		
	}

	@Override
	public void onEnd(boolean winner, int score) {
		if(learningScenario != null){
			try {
				saveResult(winner, score);
			} catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void onStart(int gameCount) {
		
		// revrite decision tables
		if(learningScenario != null){
			Map<Class<? extends Agent>,Map<DecisionTablesMapKey,DecisionTable>> decisionSettings
					= learningScenario.getDecisionSettings(gameCount);
			for (Map.Entry<Class<? extends Agent>, Map<DecisionTablesMapKey, DecisionTable>> entry
					: decisionSettings.entrySet()) {
				Class<? extends Agent> key = entry.getKey();
				Map<DecisionTablesMapKey, DecisionTable> value = entry.getValue();
				
				decisionModule.rewriteDecisionTablesMap(key, value);
			}
		}
	}
	
	@Override
	public List<Class<? extends GameAPIModule>> getDependencies() {
		List<Class<? extends GameAPIModule>> dependencies = new ArrayList<>();
		dependencies.add(DecisionModule.class);
		dependencies.add(DecisionStorageModule.class);
		
		return dependencies;
	}
}
