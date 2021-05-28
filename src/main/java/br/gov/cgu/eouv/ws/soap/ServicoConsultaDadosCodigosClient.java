package br.gov.cgu.eouv.ws.soap;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Claudio
 * @project participact-server
 * @date 08/04/2019
 **/
public class ServicoConsultaDadosCodigosClient extends EOuvWebService {

    /**
     * 1.1.3.   GetListaOrgaosSiorg
     * @return
     */
    public List<OrgaoSiorgOuvidoria> getListaOrgaosSiorg() {
        String soapAction = String.format(Config.GCU_SOAP_ACTION_CONSULTA_DADOS_CODIGOS, "GetListaOrgaosSiorg");
        String soapBody = String.format(Config.GCU_SOAP_ENVELOPE, Config.GCU_SOAP_SERV_CONSULTA_DADOS_CODIGOS,"<ser:GetListaOrgaosSiorg>" + Config.CGU_SOAP_CREDENTIALS + "</ser:GetListaOrgaosSiorg>");
        List<OrgaoSiorgOuvidoria> listOrgao = new ArrayList<>();
        try {
            Document document = request(Config.GCU_SOAP_URL_CONSULTA_DADOS_CODIGOS, soapAction, soapBody);
            if (document != null) {
                XPath xPath = XPathFactory.newInstance().newXPath();
                Node getListaOrgaosSiorgResult = (Node) xPath.compile("//GetListaOrgaosSiorgResponse//GetListaOrgaosSiorgResult//OrgaosSiorgOuvidoria").evaluate(document, XPathConstants.NODE);
                if (getListaOrgaosSiorgResult.hasChildNodes()) {
                    NodeList nodeList = getListaOrgaosSiorgResult.getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node n = nodeList.item(i);
                        NodeList nl = n.getChildNodes();
                        Long codOrgao = Long.parseLong(nl.item(0).getTextContent());
                        String nomOrgao = nl.item(1).getTextContent();
                        if (!Validator.isEmptyString(nomOrgao)) {
                            listOrgao.add(new OrgaoSiorgOuvidoria(codOrgao, nomOrgao));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return listOrgao;
    }
}
