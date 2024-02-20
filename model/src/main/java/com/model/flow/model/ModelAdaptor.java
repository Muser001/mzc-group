package com.model.flow.model;

import com.model.flownode.FlowConf;
import com.model.flownode.FlowNode;
import com.model.flownode.FlowTransactionConf;
import com.model.flownode.nodeclass.*;
import com.model.flownode.properties.FlowProperties;
import com.model.mapper.DataMapping;
import com.model.mapper.DataMappingDetail;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.naming.Name;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.*;

public class ModelAdaptor {

    public ModelAdaptor() {
    }

    public static FlowTransactionConf idModel2Corebanking(InputStream inputStream, String fileName) throws ParserConfigurationException, IOException, SAXException {
        Map<String, FlowNode> nodeMap = new HashMap<>();
        Map<String, String> idMap = new HashMap<>();
        Map<String, String> complexMap = new HashMap<>();
        Set<String> flowForkSet = new HashSet<>();
        Set<String> flowDynamicForkSet = new HashSet<>();
        Set<String> flowCaseSet = new HashSet<>();
        Set<String> flowLoopSet = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document parse = builder.parse(inputStream);
        FlowTransactionConf flowTransactionConf = new FlowTransactionConf();
        FlowConf conf = new FlowConf();
        putFlowConf(parse, flowTransactionConf);
        NodeList lists = parse.getElementsByTagName("nodes");
        Map<String, Node> docNodeMap = new HashMap<>();

        for (int i = 0; i < lists.getLength(); ++i) {
            Node node = lists.item(i);
            NamedNodeMap namedNodeMap = node.getAttributes();
            if (!namedNodeMap.getNamedItem("xsi:type").getNodeValue().equalsIgnoreCase("core:CBFlowMapping") && !namedNodeMap.getNamedItem("xsi:type").getNodeValue().equalsIgnoreCase("model:NodeElementLabel")) {
                Node idNode = namedNodeMap.getNamedItem("id");
                if (null == idNode || idNode.getNodeValue().trim().equalsIgnoreCase("")) {
                    throw new RuntimeException("文件名为" + fileName + "的图元id属性不能为空");
                }

                String nodeId = namedNodeMap.getNamedItem("id").getNodeValue();
                if (docNodeMap.get(nodeId) != null) {
                    throw new RuntimeException("模型解析失败，已有id为" + nodeId + "的图元");
                }

                docNodeMap.put(nodeId, node);
            }
        }

        Map<String, Map<String, String>> nextMap = new HashMap<>();
        String rootNodeId = putComplexMap(docNodeMap, complexMap, idMap, flowForkSet, flowDynamicForkSet, flowCaseSet, nodeMap, nextMap, flowLoopSet);
        Iterator var26 = flowForkSet.iterator();

        String nodeid;
        while (var26.hasNext()) {
            nodeid = (String) var26.next();
            FlowForkNode node = (FlowForkNode) nodeMap.get(nodeid);
            calFlowForkNode(node, nodeMap, nextMap, complexMap, docNodeMap);
        }

        var26 = flowDynamicForkSet.iterator();

        while (var26.hasNext()) {
            nodeid = (String) var26.next();
            FlowDynamicForkNode node = (FlowDynamicForkNode) nodeMap.get(nodeid);
            calDynamicForkNode(node, nodeMap, nextMap, complexMap, docNodeMap);
        }

        var26 = flowCaseSet.iterator();

        while (var26.hasNext()) {
            nodeid = (String)var26.next();
            FlowCaseNode node = (FlowCaseNode) nodeMap.get(nodeid);
            calFlowCaseNode(node, nodeMap, nextMap, complexMap, docNodeMap);
        }

        var26 = flowLoopSet.iterator();

        while (var26.hasNext()) {
            nodeid = (String)var26.next();
            FlowLoopNode node = (FlowLoopNode) nodeMap.get(nodeid);
            calLoopForkNode(node, nodeMap, nextMap, complexMap, docNodeMap);
        }

        calFlowNode(nodeMap, rootNodeId, conf, complexMap, docNodeMap);
        Set<Map.Entry<String, String>> idset = idMap.entrySet();
        Iterator var31 = idset.iterator();

        while (var31.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var31.next();
            ((FlowNode)nodeMap.get(entry.getKey())).setId((String)entry.getValue());
        }

        flowTransactionConf.setFlow(conf);
        return flowTransactionConf;
    }

    private static Map<String, String> getIdAndConnections(Node node) {
        NodeList list = node.getChildNodes();
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < list.getLength(); ++i) {
            String nodeName = list.item(i).getNodeName();
            if ("sourceConnections".equalsIgnoreCase(nodeName)) {
                String con = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
                NodeList childNodes = list.item(i).getChildNodes();

                for (int j = 0; j < childNodes.getLength(); ++j) {
                    String sNode = childNodes.item(j).getNodeName();
                    if ("targetNode".equalsIgnoreCase(sNode)) {
                        NodeList childNodes2 = childNodes.item(j).getChildNodes();

                        for (int t = 0; t < childNodes2.getLength(); ++t) {
                            Node targetNode = childNodes2.item(t);
                            if (null != targetNode && targetNode.getNodeValue() != null && !"".equalsIgnoreCase(targetNode.getNodeValue())) {
                                String targetNodeValue = targetNode.getNodeValue();
                                map.put(con, targetNodeValue);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    private static void putFlowConf(Document parse, FlowTransactionConf conf) {
        NodeList list = parse.getElementsByTagName("process:otsflow");
        Node item = list.item(0);
        NamedNodeMap namedNodeMap = item.getAttributes();
        Node nodeId = namedNodeMap.getNamedItem("id");
        if (null != nodeId && !"".equalsIgnoreCase(nodeId.getNodeValue()) && nodeId.getChildNodes() != null) {
            Node nodeInput = namedNodeMap.getNamedItem("input");
            if (null != nodeInput && !"".equalsIgnoreCase(nodeInput.getNodeValue()) && nodeInput.getChildNodes() != null) {
                Node nodeOutput = namedNodeMap.getNamedItem("output");
                if (null != nodeOutput && !"".equalsIgnoreCase(nodeOutput.getNodeValue()) && nodeOutput.getChildNodes() != null) {
                    Node nodeExchange = namedNodeMap.getNamedItem("exchange");
                    if (null != nodeExchange && !"".equalsIgnoreCase(nodeExchange.getNodeValue()) && nodeExchange.getChildNodes() != null) {
                        conf.setDescription(namedNodeMap.getNamedItem("description") == null ? "" : namedNodeMap.getNamedItem("description").getNodeValue());
                        conf.setExchange(nodeExchange.getNodeValue());
                        if (nodeId.getNodeValue().length() == 4) {
                            // TODO: 2024/1/5 系统编码后期改造
                            String systemCode = "";
                            if (systemCode == null || systemCode.equals("") || systemCode.length() != 7) {
                                conf.setId(systemCode + nodeId.getNodeValue());
                            }else {
                                conf.setId(nodeId.getNodeValue());
                            }

                            conf.setInput(nodeInput.getNodeValue());
                            conf.setLongname(namedNodeMap.getNamedItem("longname") == null ? "" : namedNodeMap.getNamedItem("longname").getNodeValue());
                            conf.setMapper(namedNodeMap.getNamedItem("mapper") == null ? "" : namedNodeMap.getNamedItem("mapper").getNodeValue());
                            conf.setOutput(nodeOutput.getNodeValue());
                            DataMapping dataMapping = new DataMapping();
                            NodeList nodes = parse.getElementsByTagName("nodes");

                            for (int i = 0; i < nodes.getLength(); ++i) {
                                Node nodess = nodes.item(i);
                                NamedNodeMap namedNodeMap1 = nodess.getAttributes();
                                Node xsiTypeNode = namedNodeMap1.getNamedItem("xsi:type");
                                String xsiType = xsiTypeNode.getNodeValue();
                                if (xsiType.equalsIgnoreCase("core:CBFlowMapping")) {
                                    if (namedNodeMap1 != null) {
                                        FlowProperties properties = new FlowProperties();
                                        String chkMacFlg = namedNodeMap1.getNamedItem("chkMacFlg") == null ? "N" : namedNodeMap1.getNamedItem("chkMacFlg").getNodeValue();
                                        if (chkMacFlg.equalsIgnoreCase("Y")) {
                                            properties.setChkMacFlg(true);
                                        }else {
                                            properties.setChkMacFlg(false);
                                        }
                                        properties.setInTxStep(namedNodeMap1.getNamedItem("inTxStep") == null ? "" : namedNodeMap1.getNamedItem("inTxStep").getNodeValue());
                                        properties.setMacGroupId(namedNodeMap1.getNamedItem("macGroupId") == null ? "" : namedNodeMap1.getNamedItem("macGroupId").getNodeValue());
                                        properties.setOprAtt(namedNodeMap1.getNamedItem("oprAtt") == null ? "" : namedNodeMap1.getNamedItem("oprAtt").getNodeValue());
                                        properties.setOutTxStep(namedNodeMap1.getNamedItem("outTxStep") == null ? "" : namedNodeMap1.getNamedItem("outTxStep").getNodeValue());
                                        String txnFlag = namedNodeMap1.getNamedItem("txnFlag") == null ? "" : namedNodeMap1.getNamedItem("txnFlag").getNodeValue();
                                        if (txnFlag.equals("1")) {
                                            properties.setTxnFlag(true);
                                        } else if (txnFlag.equals("0")) {
                                            properties.setTxnFlag(false);
                                        }else {
                                            properties.setTxnFlag(true);
                                        }

                                        properties.setSafSupportType(namedNodeMap1.getNamedItem("safSupportType") == null ? 0 : Integer.valueOf(namedNodeMap1.getNamedItem("safSupportType").getNodeValue()));
                                        properties.setSafCompensatorCode(namedNodeMap1.getNamedItem("safCompensatorCode") == null ? "" : namedNodeMap1.getNamedItem("safCompensatorCode").getNodeValue());
                                        String checkFlag = namedNodeMap1.getNamedItem("checkFlag") == null ? "0" : namedNodeMap1.getNamedItem("checkFlag").getNodeValue();
                                        if (checkFlag.equals("1")) {
                                            properties.setCheckFlag(true);
                                        }else {
                                            properties.setCheckFlag(false);
                                        }

                                        properties.setCancelServNo(namedNodeMap1.getNamedItem("cancelServNo") == null ? "" : namedNodeMap1.getNamedItem("cancelServNo").getNodeValue());
                                        properties.setReverseSevrNo(namedNodeMap1.getNamedItem("reverseSerNo") == null ? "" : namedNodeMap1.getNamedItem("reverseSerNo").getNodeValue());
                                        Node transProp = namedNodeMap1.getNamedItem("transProp");
                                        if (transProp != null) {
                                            String nodeValue = transProp.getNodeValue();
                                            if (nodeValue != null && !nodeValue.isEmpty()) {
                                                properties.setTransProp(transProp.getNodeValue());
                                            }else {
                                                properties.setTransProp("0");
                                            }
                                        }else {
                                            properties.setTransProp("0");
                                        }
                                        conf.setFlowProps(properties);
                                        Node transactionMode = namedNodeMap1.getNamedItem("transactionMode");
                                        if (transactionMode != null) {
                                            String nodeValue = transactionMode.getNodeValue();
                                            if (nodeValue != null && !nodeValue.isEmpty()) {
                                                properties.setTransactionMode(new Integer(nodeValue));
                                            }else {
                                                properties.setTransactionMode(1);
                                            }
                                        }else {
                                            properties.setTransactionMode(1);
                                        }

                                        conf.setFlowProps(properties);
                                    }

                                    putConfigMapping(namedNodeMap1, conf, nodess, dataMapping);
                                }
                            }
                        }else {
                            throw new RuntimeException("id为:" + nodeId + "服务流程交换区接口不能为空");
                        }
                    }else {
                        throw new RuntimeException("id为:" + nodeId + "服务输出接口不能为空");
                    }
                }else {
                    throw new RuntimeException("id为:" + nodeId + "服务输入接口不能为空");
                }
            }else {
                throw new RuntimeException("服务基础属性id不能为空");
            }
        }
    }

    private static void calLoopForkNode(FlowLoopNode node, Map<String, FlowNode> nodeMap, Map<String, Map<String, String>> nextMap, Map<String, String> complexMap, Map<String, Node> docNodeMap) {

    }

    private static void putConfigMapping(NamedNodeMap namedNodeMap, FlowTransactionConf conf, Node nodess, DataMapping dataMapping) {
        String cbid = namedNodeMap.getNamedItem("cbId") == null ? "" : namedNodeMap.getNamedItem("cbId").getNodeValue();
        if (cbid != null && !cbid.equalsIgnoreCase("") && "false".equalsIgnoreCase(cbid)) {
            NodeList childNodes = nodess.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); ++j) {
                Node childNode = childNodes.item(j);
                String childBNodeMap = childNode.getNodeName();
                if (childBNodeMap.equalsIgnoreCase("core:outMappings")) {
                    String byInterface = childNode.getAttributes().getNamedItem("byInterface").getNodeValue();
                    dataMapping.setByInterface(Boolean.valueOf(byInterface));
                    List<DataMappingDetail> mappingList = new ArrayList<>();
                    NodeList childNodes2 = childNode.getChildNodes();

                    for (int g = 0; g <childNodes2.getLength(); ++g) {
                        Node item2 = childNodes2.item(g);
                        if (item2.getNodeName().equalsIgnoreCase("core:mappings")) {
                            DataMappingDetail dataMappingDetail = new DataMappingDetail();
                            Node desNamedItem = item2.getAttributes().getNamedItem("des");
                            if (desNamedItem != null) {
                                String des = desNamedItem.getNodeValue();
                                dataMappingDetail.setDest(des);
                            }

                            Node srcNamedItem = item2.getAttributes().getNamedItem("src");
                            if (srcNamedItem != null) {
                                String src = srcNamedItem.getNodeValue();
                                dataMappingDetail.setSrc(src);
                            }

                            mappingList.add(dataMappingDetail);
                            dataMapping.setMappingList(mappingList);
                            conf.setOutMapping(dataMapping);
                        }
                    }
                }
            }
        }
    }

    private static void calDynamicForkNode(FlowDynamicForkNode node, Map<String, FlowNode> nodeMap, Map<String, Map<String, String>> nextMap, Map<String, String> complexMap, Map<String, Node> docNodeMap) {
        String start = node.getId();
        Map<String, String> map = (Map)nextMap.get(start);
        if (map != null && map.size() > 0) {
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator var8 = set.iterator();
            label157:
            while (var8.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var8.next();
                List<FlowNode> list = node.getNodes();
                String nodeid = (String)entry.getValue();
                String endId = (String)complexMap.get(start);

                while (true) {
                    while (true) {
                        if (endId == nodeid || endId.equalsIgnoreCase(nodeid)) {
                            continue label157;
                        }

                        FlowNode nextNode = (FlowNode)nodeMap.get(nodeid);
                        if (nextNode == null) {
                            continue label157;
                        }

                        list.add(nextNode);
                        String nextNodeId = nextNode.getId();
                        if (complexMap.containsKey(nextNodeId)) {
                            String realNodeId = (String)complexMap.get(nextNodeId);
                            Node endNode = (Node) docNodeMap.get(realNodeId);
                            String nextNodeid = getNextId(endNode);
                            if (nextNodeid == null || nextNodeid.equalsIgnoreCase("")) {
                                throw new RuntimeException("id为:" + realNodeId + "的结束图元没有连接");
                            }

                            nodeid = nextNodeid;
                        }else {
                            Map<String, String> s = (Map)nextMap.get(nextNodeId);
                            if (s.isEmpty()) {
                                throw new RuntimeException("id为:" + nextNodeId + "的图元的后续节点没有连接");
                            }

                            Set<String> keySet = s.keySet();
                            String id;
                            for (Iterator var17 = keySet.iterator(); var17.hasNext(); nodeid = id) {
                                String string = (String)var17.next();
                                id = (String)s.get(string);
                            }
                        }
                    }
                }
            }
        }
    }

    private static String putComplexMap(Map<String, Node> docNodeMap, Map<String, String> complexMap, Map<String, String> idMap, Set<String> flowForkSet, Set<String> flowDynamicForkSet, Set<String> flowCaseSet, Map<String, FlowNode> nodeMap, Map<String,Map<String, String>> nextMap, Set<String> flowLoopSet) {
        String rootNodeId = "";
        Set<Map.Entry<String, Node>> entrySet = docNodeMap.entrySet();
        Iterator var11 = entrySet.iterator();
        while (var11.hasNext()) {
            Map.Entry<String, Node> entry = (Map.Entry)var11.next();
            Node node = (Node) docNodeMap.get(entry.getKey());
            NamedNodeMap namedNodeMap = node.getAttributes();
            Node xsiTypeNode = namedNodeMap.getNamedItem("xsi:type");
            String xsiType = xsiTypeNode.getNodeValue();
            String id;
            if (xsiType.equalsIgnoreCase("core:tForkBegin")) {
                id = namedNodeMap.getNamedItem("id").getNodeValue();
                flowForkSet.add(id);
                dealComplexMap(namedNodeMap, complexMap, idMap);
            }else if (xsiType.equalsIgnoreCase("core:tCaseWhenBegin")) {
                id = namedNodeMap.getNamedItem("id").getNodeValue();
                flowCaseSet.add(id);
                dealComplexMap(namedNodeMap, complexMap, idMap);
            } else if (xsiType.equalsIgnoreCase("core:tDynamicForkBegin")) {
                id = namedNodeMap.getNamedItem("id").getNodeValue();
                flowDynamicForkSet.add(id);
                dealComplexMap(namedNodeMap, complexMap, idMap);
            } else if (xsiType.equalsIgnoreCase("core:tbLoopBegin")) {
                id = namedNodeMap.getNamedItem("id").getNodeValue();
                flowLoopSet.add(id);
                dealComplexMap(namedNodeMap, complexMap, idMap);
            } else if (xsiType.equalsIgnoreCase("process:tStart")) {
                if (!"".equals(rootNodeId)) {
                    throw new RuntimeException("图元不允许有两个开始节点");
                }

                rootNodeId = getNextId(node);
            }

            putNodeMap(nodeMap, node);
            id = (String)entry.getKey();
            Map<String, String> map = getIdAndConnections(node);
            nextMap.put(id, map);
        }
        return rootNodeId;
    }

    private static void dealComplexMap(NamedNodeMap namedNodeMap, Map<String, String> complexMap, Map<String, String> idMap) {
        String id = namedNodeMap.getNamedItem("id").getNodeValue();
        String endId = namedNodeMap.getNamedItem("matchedName").getNodeValue();
        complexMap.put(id, endId);
        Node nameItem = namedNodeMap.getNamedItem("editId");
        if (nameItem != null && !nameItem.getNodeValue().equals("")) {
            Set<String> keySet = idMap.keySet();
            Iterator var7 = keySet.iterator();

            while (var7.hasNext()) {
                String complexId = (String)var7.next();
                String editid = (String) idMap.get(complexId);
                if (editid.equalsIgnoreCase(namedNodeMap.getNamedItem("editId").getNodeValue())) {
                    throw new RuntimeException("id为:" + id + "的复杂图元的EditId不能重复");
                }
            }
            idMap.put(id,namedNodeMap.getNamedItem("editId").getNodeValue());
        }
    }

    private static void calFlowForkNode(FlowForkNode node, Map<String, FlowNode> nodeMap, Map<String, Map<String, String>> nextMap, Map<String, String> complexMap, Map<String, Node> docNodeMap) {
        String start = node.getId();
        Map<String, String> map = (Map)nextMap.get(start);
        if (map != null && map.size() > 0) {
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator var8 = set.iterator();

            while (var8.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var8.next();
                List<FlowBlockNode> list = node.getNodes();
                Iterator var11 = list.iterator();

                label66:
                while (var11.hasNext()) {
                    FlowBlockNode flowWhenNode = (FlowBlockNode)var11.next();
                    if (flowWhenNode.getId().equalsIgnoreCase((String)entry.getKey())) {
                        String nodeid = (String)entry.getValue();
                        String endId = (String)complexMap.get(start);
                        FlowBlockNode temp = flowWhenNode;

                        while (true) {
                            while (true) {
                                if (endId == nodeid || endId.equalsIgnoreCase(nodeid)) {
                                    continue label66;
                                }

                                FlowNode nextNode = (FlowNode) nodeMap.get(nodeid);
                                if (nextNode == null) {
                                    continue label66;
                                }

                                temp.getNodes().add(nextNode);
                                String nextNodeId = nextNode.getId();
                                if (complexMap.containsKey(nextNodeId)) {
                                    String realNodeId = (String)complexMap.get(nextNodeId);
                                    Node endNode = (Node) docNodeMap.get(realNodeId);
                                    String nextNodeid = getNextId(endNode);
                                    if (nextNodeid == null || nextNodeid.equalsIgnoreCase("")) {
                                        throw new RuntimeException("id为:" + realNodeId + "的结束图元没有连接");
                                    }

                                    nodeid = nextNodeid;
                                }else {
                                    Map<String, String> s = (Map)nextMap.get(nextNodeId);
                                    if (s.isEmpty()) {
                                        throw new RuntimeException("id为:" + nextNodeId + "的图元的后续节点没有连接");
                                    }

                                    Set<String> keySet = s.keySet();
                                    String id;
                                    for (Iterator var17 = keySet.iterator(); var17.hasNext(); nodeid = id) {
                                        String string = (String)var17.next();
                                        id = (String)s.get(string);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void calFlowCaseNode(FlowCaseNode node, Map<String, FlowNode> nodeMap, Map<String, Map<String, String>> nextMap, Map<String, String> complexMap, Map<String, Node> docNodeMap) {
        String start = node.getId();
        Map<String, String> map = (Map)nextMap.get(start);
        if (map != null && map.size() > 0) {
            Set<Map.Entry<String, String>> set = map.entrySet();
            List<FlowWhenNode> list = node.getNodes();
            Iterator var9 = set.iterator();

            while (var9.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var9.next();
                Iterator var11 = list.iterator();


                label66:
                while (var11.hasNext()) {
                    FlowWhenNode flowWhenNode = (FlowWhenNode) var11.next();
                    if (flowWhenNode.getId().equalsIgnoreCase((String) entry.getKey())) {
                        String nodeid = (String) entry.getValue();
                        String endId = (String) complexMap.get(start);
                        FlowWhenNode temp = flowWhenNode;
                        while (true) {
                            while (true) {
                                if (endId == nodeid || endId.equalsIgnoreCase(nodeid)) {
                                    continue label66;
                                }

                                FlowNode nextNode = (FlowNode) nodeMap.get(nodeid);
                                if (nextNode == null) {
                                    continue label66;
                                }

                                temp.getNodes().add(nextNode);
                                String nextNodeId = nextNode.getId();
                                if (complexMap.containsKey(nextNodeId)) {
                                    String realNodeId = (String) complexMap.get(nextNodeId);
                                    Node endNode = (Node) docNodeMap.get(realNodeId);
                                    String nextNodeid = getNextId(endNode);
                                    if (nextNodeid == null || nextNodeid.equalsIgnoreCase("")) {
                                        throw new RuntimeException("id为:" + realNodeId + "的结束图元没有连接");
                                    }

                                    nodeid = nextNodeid;
                                } else {
                                    Map<String, String> s = (Map) nextMap.get(nextNodeId);
                                    if (s.isEmpty()) {
                                        throw new RuntimeException("id为:" + nextNodeId + "的图元的后续节点没有连接");
                                    }

                                    Set<String> keySet = s.keySet();
                                    String id;
                                    for (Iterator var17 = keySet.iterator(); var17.hasNext(); nodeid = id) {
                                        String string = (String) var17.next();
                                        id = (String) s.get(string);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private static void calFlowNode(Map<String, FlowNode> nodeMap, String rootNodeName, FlowConf conf, Map<String, String> complexMap, Map<String, Node> docNodeMap) {
        List<FlowNode> nodes = conf.getNodes();
        if (null != rootNodeName && !rootNodeName.equals("")) {
            Node docnode = (Node)docNodeMap.get(rootNodeName);
            String endType = docnode.getAttributes().getNamedItem("xsi:type").getNodeValue();

            while (!endType.equalsIgnoreCase("process:tEnd")) {
                FlowNode node = (FlowNode) nodeMap.get(rootNodeName);
                if (node == null) {
                    break;
                }

                String realNodeId;
                if (complexMap.containsKey(rootNodeName)) {
                    realNodeId = (String)complexMap.get(rootNodeName);
                    String nextid = getNextId((Node)docNodeMap.get(realNodeId));
                    rootNodeName = nextid;
                    nodes.add(node);
                }else {
                    realNodeId = getNextId((Node)docNodeMap.get(node.getId()));
                    docnode = (Node) docNodeMap.get(realNodeId);
                    endType = docnode.getAttributes().getNamedItem("xsi:type").getNodeValue();
                    rootNodeName = realNodeId;
                    nodes.add(node);
                }
            }
        }else {
            throw new RuntimeException("没有找到开始节点");
        }
    }

    private static void putNodeMap(Map<String, FlowNode> nodeMap, Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Node xsiTypeNode = namedNodeMap.getNamedItem("xsi:type");
        switch (xsiTypeNode.getNodeValue()) {
            case "core:CBBean":
                putCBBeanNode(nodeMap, node);
                break;
            case "core:CBMethod":
                putCBMethodNode(nodeMap, node);
                break;
            case "core:CBService":
                putCBService(nodeMap, node);
                break;
            case "core:CBExpr":
                putCBExprNode(nodeMap, node);
                break;
            case "core:CBSubflow":
                putCBSubflowNode(nodeMap, node);
                break;
            case "core:tCaseWhenBegin":
                putCaseWhenBeginNode(nodeMap, node);
                break;
            case "core:tDynamicForkBegin":
                putTWhileStartNode(nodeMap, node);
                break;
            case "core:tForkBegin":
                putTForEachStartNode(nodeMap, node);
                break;
            case "core:cbLoopBegin":
                putLoopBeginNode(nodeMap, node);
                break;
        }
    }
    private static void putLoopBeginNode(Map<String, FlowNode> nodeMap, Node node) {

    }

    private static void putTForEachStartNode(Map<String, FlowNode> nodeMap, Node node) {
        FlowForkNode forkNode = new FlowForkNode();
        NamedNodeMap forkNodeattributes = node.getAttributes();
        if (forkNodeattributes != null) {
            Node idNode = forkNodeattributes.getNamedItem("id");
            if (null == idNode || "".equalsIgnoreCase(idNode.getNodeValue()) || null == idNode.getNodeValue()) {
                throw new RuntimeException("静态并发图元id不能为空");
            }
            forkNode.setCondition(forkNodeattributes.getNamedItem("condition") == null ? "" : forkNodeattributes.getNamedItem("condition").getNodeValue());
            forkNode.setDescription(forkNodeattributes.getNamedItem("description") == null ? "" : forkNodeattributes.getNamedItem("description").getNodeValue());
            forkNode.setId(idNode.getNodeValue());
            forkNode.setLongname(forkNodeattributes.getNamedItem("name") == null ? "" : forkNodeattributes.getNamedItem("name").getNodeValue());
            String editid = forkNodeattributes.getNamedItem("ediId") == null ? "" : forkNodeattributes.getNamedItem("ediId").getNodeValue();
            if (null == editid || "".equalsIgnoreCase(editid)) {
                throw new RuntimeException("静态并发图元editid不能为空");
            }

            List<FlowBlockNode> list = forkNode.getNodes();
            NodeList childList = node.getChildNodes();
            putFlowBlockNode(childList, list, editid);
            List<String> targetConections = getTargetConnection(node);
            if (targetConections.isEmpty()) {
                throw new RuntimeException("id为:" + editid + "的静态并发图元，没有源节点");
            }
            if (targetConections.size() >=2) {
                throw new RuntimeException("id为:" + editid + "的静态并发图元，源节点连接数大于2");
            }
            List<String> sourceLists = getSourceLists(node);
            if (sourceLists.isEmpty()) {
                throw new RuntimeException("id为:" + editid + "的静态并发图元，没有目标节点");
            }
            nodeMap.put(forkNodeattributes.getNamedItem("id").getNodeValue(), forkNode);
        }
    }

    private static void putFlowBlockNode(NodeList childList, List<FlowBlockNode> list, String editid) {
        for (int d = 0; d< childList.getLength(); ++d) {
            String nodeName = childList.item(d).getNodeName();
            FlowBlockNode flowBlockNode = null;
            if ("sourceConnections".equalsIgnoreCase(nodeName)) {
                flowBlockNode = new FlowBlockNode();
                String id;
                if (childList.item(d).getAttributes().getNamedItem("condition") != null) {
                    id = childList.item(d).getAttributes().getNamedItem("condition").getNodeValue();
                    if (null != id && !"".equalsIgnoreCase(id.trim())) {
                        flowBlockNode.setCondition(id);
                    }else {
                        flowBlockNode.setCondition("");
                    }

                }else {
                    flowBlockNode.setCondition("");
                }

                if (childList.item(d).getAttributes().getNamedItem("id") != null) {
                    id = childList.item(d).getAttributes().getNamedItem("id").getNodeValue();
                    if (null == id || "".equalsIgnoreCase(id.trim())) {
                        throw new RuntimeException("id为" + editid + "的静态并发图元连接线的id不能为空");
                    }

                    for (int m = 0; m < list.size(); ++m) {
                        String thisid = ((FlowBlockNode)list.get(m)).getId();
                        if (id.equalsIgnoreCase(thisid)) {
                            throw new RuntimeException("id为" + editid + "的静态并发图元连接线的id不能重复");
                        }
                    }
                    flowBlockNode.setId(id);
                }
                if (childList.item(d).getAttributes().getNamedItem("name") != null) {
                    id = childList.item(d).getAttributes().getNamedItem("name").getNodeValue();
                    flowBlockNode.setLongname(id);
                }
                if (childList.item(d).getAttributes().getNamedItem("description") != null) {
                    id = childList.item(d).getAttributes().getNamedItem("description").getNodeValue();
                    flowBlockNode.setDescription(id == null ? "" : id);
                }
                list.add(flowBlockNode);
            }
        }
    }

    private static void putTWhileStartNode(Map<String, FlowNode> nodeMap, Node node) {
        FlowDynamicForkNode dynamic = new FlowDynamicForkNode();
        NamedNodeMap dynamicattributes = node.getAttributes();
        if (dynamicattributes != null) {
            Node idNode = dynamicattributes.getNamedItem("id");
            if (null == idNode || "".equalsIgnoreCase(idNode.getNodeValue()) || null == idNode.getNodeValue()) {
                throw new RuntimeException("动态并发图元id不能为空");
            }

            Node parasNode = dynamicattributes.getNamedItem("forkParam");
            if (null == parasNode || "".equalsIgnoreCase(parasNode.getNodeValue()) || null == parasNode.getNodeValue()) {
                throw new RuntimeException("id为" + idNode.getNodeValue() + "的动态并发图元,并发参数不能为空");
            }

            dynamic.setCondition(dynamicattributes.getNamedItem("condition") == null ? "" : dynamicattributes.getNamedItem("condition").getNodeValue());
            dynamic.setDescription(dynamicattributes.getNamedItem("description") == null ? "" : dynamicattributes.getNamedItem("description").getNodeValue());
            dynamic.setId(idNode.getNodeValue());
            dynamic.setLongname(dynamicattributes.getNamedItem("name") == null ? "" : dynamicattributes.getNamedItem("name").getNodeValue());
            dynamic.setParas(parasNode.getNodeValue());
            List<String> targetConnections = getTargetConnection(node);
            if (targetConnections.isEmpty()) {
                throw new RuntimeException("id为:" + idNode.getNodeValue() + "的静态并发图元，没有源节点");
            }
            if (targetConnections.size() >=2) {
                throw new RuntimeException("id为:" + idNode.getNodeValue() + "的静态并发图元，源节点连接数大于2");
            }
            List<String> sourceLists = getSourceLists(node);
            if (sourceLists.isEmpty()) {
                throw new RuntimeException("id为:" + idNode.getNodeValue() + "的静态并发图元，没有目标节点");
            }
            if (sourceLists.size() >=2) {
                throw new RuntimeException("id为:" + idNode.getNodeValue() + "的静态并发图元，目标节点连接数大于2");
            }

            nodeMap.put(dynamicattributes.getNamedItem("id").getNodeValue(), dynamic);
        }
    }

    private static void putCaseWhenBeginNode(Map<String, FlowNode> nodeMap, Node node) {
        FlowCaseNode flowCaseNode = new FlowCaseNode();
        NamedNodeMap caseNodeattributes = node.getAttributes();
        if (caseNodeattributes != null) {
            Node idNode = caseNodeattributes.getNamedItem("id");
            if (null == idNode || "".equalsIgnoreCase(idNode.getNodeValue()) || null == idNode.getNodeValue()) {
                throw new RuntimeException("case图元id不能为空");
            }
            String editid = caseNodeattributes.getNamedItem("editId") == null ? "" : caseNodeattributes.getNamedItem("editId").getNodeValue();
            if (null == editid || "".equalsIgnoreCase(editid)) {
                throw new RuntimeException("case图元ediId不能为空");
            }
            flowCaseNode.setCondition(caseNodeattributes.getNamedItem("condition") == null ? "" : caseNodeattributes.getNamedItem("condition").getNodeValue());
            flowCaseNode.setDescription(caseNodeattributes.getNamedItem("description") == null ? "" : caseNodeattributes.getNamedItem("description").getNodeValue());
            flowCaseNode.setId(idNode.getNodeValue());
            flowCaseNode.setLongname(caseNodeattributes.getNamedItem("name") == null ? "" : caseNodeattributes.getNamedItem("name").getNodeValue());
            List<FlowWhenNode> list = flowCaseNode.getNodes();
            NodeList childList = node.getChildNodes();
            putFlowWhenNode(childList, list, editid);

            List<String> targetConnections = getTargetConnection(node);
            if (targetConnections.isEmpty()) {
                throw new RuntimeException("id为:" + editid + "的case发图元，没有源节点");
            }
            if (targetConnections.size() >=2) {
                throw new RuntimeException("id为:" + editid + "的case发图元，源节点连接数不能大于2");
            }
            List<String> sourceLists = getSourceLists(node);
            if (sourceLists.isEmpty()) {
                throw new RuntimeException("id为:" + editid + "的case图元，没有目标节点");
            }


            nodeMap.put(caseNodeattributes.getNamedItem("id").getNodeValue(), flowCaseNode);
        }
    }

    private static void putFlowWhenNode(NodeList childList, List<FlowWhenNode> list, String editid) {
        for (int d = 0; d< childList.getLength(); ++d) {
            String nodeName = childList.item(d).getNodeName();
            FlowWhenNode flowWhenNode = null;
            if ("sourceConnections".equalsIgnoreCase(nodeName)) {
                flowWhenNode = new FlowWhenNode();
                if (childList.item(d).getAttributes().getNamedItem("condition") == null) {
                    throw new RuntimeException("id为" + editid + "的case图元连接线的条件不能为空");
                }
                String description;

                if (childList.item(d).getAttributes().getNamedItem("id") != null) {
                    description = childList.item(d).getAttributes().getNamedItem("id").getNodeValue();
                    if (null == description || "".equalsIgnoreCase(description.trim())) {
                        throw new RuntimeException("id为" + editid + "的case图元连接线的id不能为空");
                    }

                    for (int m = 0; m < list.size(); ++m) {
                        String thisid = ((FlowWhenNode)list.get(m)).getId();
                        if (description.equalsIgnoreCase(thisid)) {
                            throw new RuntimeException("id为" + editid + "的静态并发图元连接线的id不能重复");
                        }
                    }
                    flowWhenNode.setId(description);
                }

                if (childList.item(d).getAttributes().getNamedItem("condition") != null) {
                    description = childList.item(d).getAttributes().getNamedItem("condition").getNodeValue();
                    if (null != description && !"".equalsIgnoreCase(description.trim())) {
                        flowWhenNode.setCondition(description);
                    }else {
                        flowWhenNode.setCondition("");
                    }

                }else {
                    flowWhenNode.setCondition("");
                }

                if (childList.item(d).getAttributes().getNamedItem("name") != null) {
                    description = childList.item(d).getAttributes().getNamedItem("name").getNodeValue();
                    flowWhenNode.setLongname(description);
                }
                if (childList.item(d).getAttributes().getNamedItem("description") != null) {
                    description = childList.item(d).getAttributes().getNamedItem("description").getNodeValue();
                    flowWhenNode.setDescription(description == null ? "" : description);
                }
                list.add(flowWhenNode);
            }
        }
    }

    private static void putCBSubflowNode(Map<String, FlowNode> nodeMap, Node node) {

    }
    private static void putCBSubflowMapping(Map<String, FlowNode> nodeMap, Node node) {

    }
    private static void putCBExprNode(Map<String, FlowNode> nodeMap, Node node) {

    }

    private static void putCBService(Map<String, FlowNode> nodeMap, Node node) {
        FlowServiceNode flowServiceNode = new FlowServiceNode();
        NamedNodeMap serviceattributes = node.getAttributes();
        if (serviceattributes != null) {
            Node idNode = serviceattributes.getNamedItem("id");
            if (idNode == null || "".equalsIgnoreCase(idNode.getNodeValue()) || null == idNode.getNodeValue()) {
                throw new RuntimeException("服务图元的id不能为空");
            }
            Node serviceCodeNode = serviceattributes.getNamedItem("serviceCode");
            if (serviceCodeNode == null || "".equalsIgnoreCase(serviceCodeNode.getNodeValue()) || null == serviceCodeNode.getNodeValue()) {
                throw new RuntimeException("服务图元的id:" + idNode.getNodeValue() + ",服务码不能为空");
            }
            Node inputNode = serviceattributes.getNamedItem("input");
            if (inputNode == null || "".equalsIgnoreCase(inputNode.getNodeValue()) || null == inputNode.getNodeValue()) {
                throw new RuntimeException("服务图元的id:" + idNode.getNodeValue() + ",输入DTO不能为空");
            }
            Node outputNode = serviceattributes.getNamedItem("output");
            if (outputNode == null || "".equalsIgnoreCase(outputNode.getNodeValue()) || null == outputNode.getNodeValue()) {
                throw new RuntimeException("服务图元的id:" + idNode.getNodeValue() + ",输出DTO不能为空");
            }

            flowServiceNode.setCondition(serviceattributes.getNamedItem("cbCondition") == null ? "" : serviceattributes.getNamedItem("cbCondition").getNodeValue());
            flowServiceNode.setDescription(serviceattributes.getNamedItem("desc") == null ? "" : serviceattributes.getNamedItem("desc").getNodeValue());
            flowServiceNode.setId(idNode.getNodeValue());
            flowServiceNode.setInput(inputNode.getNodeValue());
            flowServiceNode.setLongname(serviceattributes.getNamedItem("longname") == null ? "" : serviceattributes.getNamedItem("longname").getNodeValue());
            flowServiceNode.setMapper(serviceattributes.getNamedItem("mapper") == null ? "" : serviceattributes.getNamedItem("mapper").getNodeValue());
            flowServiceNode.setServiceCode(serviceattributes.getNamedItem("serviceCode") == null ? "" : serviceattributes.getNamedItem("serviceCode").getNodeValue());
            flowServiceNode.setOutput(outputNode.getNodeValue());
            flowServiceNode.setDusType(serviceattributes.getNamedItem("dusType") == null ? "8" : serviceattributes.getNamedItem("dusType").getNodeValue());
            String keyNodeFlag = serviceattributes.getNamedItem("keyNodeFlag") == null ? "0" : serviceattributes.getNamedItem("keyNodeFlag").getNodeValue();
            if (keyNodeFlag.equals("1")) {
                flowServiceNode.setKeyNodeFlag(true);
            }else {
                flowServiceNode.setKeyNodeFlag(false);
            }

            putCBServiceMapping(serviceattributes, flowServiceNode, node);
            List<String> sourceLists = getSourceLists(node);
            if (sourceLists.isEmpty()) {
                throw new RuntimeException("id为:" + idNode.getNodeValue() + "的服务图元，没有目标节点");
            }
            if (sourceLists.size() >=2) {
                throw new RuntimeException("id为:" + idNode.getNodeValue() + "的服务图元，目标节点连接数大于2");
            }
            nodeMap.put(serviceattributes.getNamedItem("id").getNodeValue(), flowServiceNode);

        }
    }

    private static void putCBServiceMapping(NamedNodeMap serviceattributes, FlowServiceNode flowServiceNode, Node node) {
        String cdid = serviceattributes.getNamedItem("cbId") == null ? "" : serviceattributes.getNamedItem("cbId").getNodeValue();
        if (cdid != null && !cdid.equalsIgnoreCase("") && "false".equalsIgnoreCase(cdid)) {
            NodeList childNodes = node.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); ++j) {
                Node childNode = childNodes.item(j);
                String childBNodeMap = childNode.getNodeName();
                DataMapping dataMapping;
                String byInterface;
                ArrayList mappingList;
                NodeList childNodes2;
                int q;
                Node item2;
                DataMappingDetail dataMappingDetail;
                Node desNameItem;
                String des;
                String src;
                Node srcNameItem;
                if (childBNodeMap.equalsIgnoreCase("core:outMappings")) {
                    dataMapping = new DataMapping();
                    byInterface = childNode.getAttributes().getNamedItem("byInterface").getNodeValue();
                    dataMapping.setByInterface(Boolean.valueOf(byInterface));
                    mappingList = new ArrayList();
                    childNodes2 = childNode.getChildNodes();

                    for (int g = 0; g < childNodes2.getLength(); ++g) {
                        item2 = childNodes2.item(g);
                        if (item2.getNodeName().equalsIgnoreCase("core:mappings")) {
                            dataMappingDetail = new DataMappingDetail();
                            desNameItem = item2.getAttributes().getNamedItem("des");
                            if (desNameItem != null) {
                                des = desNameItem.getNodeValue();
                                dataMappingDetail.setDest(des);
                            }

                            srcNameItem = item2.getAttributes().getNamedItem("src");
                            if (srcNameItem != null) {
                                src = srcNameItem.getNodeValue();
                                dataMappingDetail.setSrc(src);
                            }

                            mappingList.add(dataMappingDetail);
                        }
                    }
                    dataMapping.setMappingList(mappingList);
                    flowServiceNode.setOutMapping(dataMapping);
                }

                if (childBNodeMap.equalsIgnoreCase("core:inMappings")) {
                    dataMapping = new DataMapping();
                    byInterface = childNode.getAttributes().getNamedItem("byInterface").getNodeValue();
                    dataMapping.setByInterface(Boolean.valueOf(byInterface));
                    mappingList = new ArrayList();
                    childNodes2 = childNode.getChildNodes();

                    for (int g = 0; g < childNodes2.getLength(); ++g) {
                        item2 = childNodes2.item(g);
                        if (item2.getNodeName().equalsIgnoreCase("core:mappings")) {
                            dataMappingDetail = new DataMappingDetail();
                            desNameItem = item2.getAttributes().getNamedItem("des");
                            if (desNameItem != null) {
                                des = desNameItem.getNodeValue();
                                dataMappingDetail.setDest(des);
                            }

                            srcNameItem = item2.getAttributes().getNamedItem("src");
                            if (srcNameItem != null) {
                                src = srcNameItem.getNodeValue();
                                dataMappingDetail.setSrc(src);
                            }

                            mappingList.add(dataMappingDetail);
                        }
                    }
                    dataMapping.setMappingList(mappingList);
                    flowServiceNode.setInMapping(dataMapping);
                }
            }
        }
    }

    private static void putCBMethodNode(Map<String, FlowNode> nodeMap, Node node) {

    }

    private static void putCBMethodMapping(Map<String, FlowNode> nodeMap, Node node) {

    }

    private static void putCBBeanNode(Map<String, FlowNode> nodeMap, Node node) {

    }

    private static void putCBBeanMapping(Map<String, FlowNode> nodeMap, Node node) {

    }

    public static String getNextId(Node transNode) {
        NodeList list = transNode.getChildNodes();

        for (int i = 0; i < list.getLength(); ++i) {
            String nodeName = list.item(i).getNodeName();
            if ("sourceConnections".equalsIgnoreCase(nodeName)) {
                NodeList childNodes = list.item(i).getChildNodes();

                for (int j = 0; j < childNodes.getLength(); ++j) {
                    String sNode = childNodes.item(j).getNodeName();
                    if ("targetNode".equalsIgnoreCase(sNode)) {
                        NodeList childNodes2 = childNodes.item(j).getChildNodes();
                        if (childNodes2 != null && childNodes2.item(0) != null) {
                            return childNodes.item(j).getChildNodes().item(0).getNodeValue();
                        }

                        throw new RuntimeException("id为:" + transNode.getAttributes().getNamedItem("id") + "节点的targetNode属性不能为空");
                    }
                }
            }
        }
        return null;
    }

    public static List<String> getSourceLists(Node transNode) {
        List<String> ids = new ArrayList<>();
        NodeList list = transNode.getChildNodes();

        for (int i = 0; i < list.getLength(); ++i) {
            String nodeName = list.item(i).getNodeName();
            if ("sourceConnections".equalsIgnoreCase(nodeName)) {
                NodeList childNodes = list.item(i).getChildNodes();
                for (int j = 0; j < childNodes.getLength(); ++j) {
                    String sNode = childNodes.item(j).getNodeName();
                    if ("targetNode".equalsIgnoreCase(sNode)) {
                        NodeList childNodes2 = childNodes.item(j).getChildNodes();
                        for (int t = 0; t < childNodes2.getLength(); ++t) {
                            Node targetNode = childNodes2.item(t);
                            if (childNodes2 == null && childNodes2.item(0) == null) {
                                throw new RuntimeException("id为:" + transNode.getAttributes().getNamedItem("id") + "节点的targetNode属性不能为空");
                            }
                            String targetNodeValue = targetNode.getNodeValue();
                            ids.add(targetNodeValue);
                        }
                   }
                }
            }
        }
        return ids;
    }

    public static List<String> getTargetConnection(Node transNode) {
        List<String> ids = new ArrayList<>();
        NodeList list = transNode.getChildNodes();

        for (int i = 0; i < list.getLength(); ++i) {
            String nodeName = list.item(i).getNodeName();
            if ("targetConnections".equalsIgnoreCase(nodeName)) {
                NodeList childNodes = list.item(i).getChildNodes();
                for (int j = 0; j < childNodes.getLength(); ++j) {
                    Node item = childNodes.item(j);
                    if (item == null && item.getNodeValue() == null) {
                        throw new RuntimeException("id为:" + transNode.getAttributes().getNamedItem("id") + "节点的targetConnections属性不能为空");
                    }
                    String targetNodeValue = childNodes.item(j).getNodeValue();
                    ids.add(targetNodeValue);
                }
            }
        }
        return ids;
    }
}
