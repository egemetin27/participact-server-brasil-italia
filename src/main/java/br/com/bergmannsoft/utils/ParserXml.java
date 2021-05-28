package br.com.bergmannsoft.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Claudio
 * @project participact-server
 * @date 08/04/2019
 **/
public class ParserXml {
//    public ObjectNode parseXml(String xml){
//        Parsing parsing = ParsingFactory.getInstance(namespaces()).create();
//        Document document = parsing.xml().document(xml);
//
//        Parser<ObjectNode> parser = parsing.obj("//m:GetStockPriceResponse")
//                .attribute("price", "m:Price", parsing.number())
//                .build();
//
//        ObjectNode result = parser.apply(document);
//        return result;
//    }

    private Map<String, String> namespaces(){
        return new HashMap<String, String>(){{
            put("m", "http://www.example.org/stock");
        }};
    }
}
