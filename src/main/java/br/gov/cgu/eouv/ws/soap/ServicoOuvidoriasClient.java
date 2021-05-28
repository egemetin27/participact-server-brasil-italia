package br.gov.cgu.eouv.ws.soap;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.Ouvidoria;
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
 * @date 09/04/2019
 **/
public class ServicoOuvidoriasClient extends EOuvWebService {

    /**
     * GetOuvidorias
     *
     * @return
     */
    public List<Ouvidoria> getOuvidorias() {
        String soapAction = String.format(Config.GCU_SOAP_ACTION_OUVIDORIAs, "GetOuvidorias");
        String soapBody = String.format(Config.GCU_SOAP_ENVELOPE, Config.GCU_SOAP_SERV_OUVIDORIAS, "<ser:GetOuvidorias>" + Config.CGU_SOAP_CREDENTIALS + "</ser:GetOuvidorias>");
        List<Ouvidoria> listOuvidoria = new ArrayList<>();
        try {
            Document document = request(Config.GCU_SOAP_URL_OUVIDORIAS, soapAction, soapBody);
            if (document != null) {
                XPath xPath = XPathFactory.newInstance().newXPath();
                Node getOuvidoriasResult = (Node) xPath.compile("//GetOuvidoriasResponse//GetOuvidoriasResult//Ouvidorias").evaluate(document, XPathConstants.NODE);
                if (getOuvidoriasResult.hasChildNodes()) {
                    NodeList nodeList = getOuvidoriasResult.getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node n = nodeList.item(i);
                        NodeList nl = n.getChildNodes();
                        Long idOrgaoOuvidoria = Long.parseLong(nl.item(0).getTextContent());
                        Long idOuvidoria = Long.parseLong(nl.item(1).getTextContent());
                        String nomeOrgaoOuvidoria = nl.item(2).getTextContent();
                        //SubAssuntosOuvidoriaWS
                        String subAssuntosOuvidoria = "";
                        NodeList subAssuntosOuvidoriaWS = nl.item(3).getChildNodes();
                        for (int x = 0; x < subAssuntosOuvidoriaWS.getLength(); x++) {
                            NodeList z = subAssuntosOuvidoriaWS.item(x).getChildNodes();
                            String descSubAssunto = z.item(0).getTextContent();
                            String idSubAssunto = z.item(1).getTextContent();
                            subAssuntosOuvidoria += String.format("%s | %s \n ", idSubAssunto, descSubAssunto);
                        }
                        //Boolean
                        boolean indEnviaDenunciasCGUPAD = Boolean.parseBoolean(nl.item(4).getTextContent());
                        boolean indEnviaDenunciasCGUPJ = Boolean.parseBoolean(nl.item(5).getTextContent());
                        String descEsfera = nl.item(6).getTextContent();
                        String descMunicipio = nl.item(7).getTextContent();
                        Long idEsfera = Long.parseLong(nl.item(8).getTextContent());
                        Long idMunicipio = Long.parseLong(nl.item(9).getTextContent());
                        String dataAdesaoEOuvPadrao = nl.item(10).getTextContent();
                        String dataAdesaoSimplifique = nl.item(11).getTextContent();
                        String dataInativacao = nl.item(12).getTextContent();

                        if (!Validator.isEmptyString(nomeOrgaoOuvidoria)) {
                            listOuvidoria.add(new Ouvidoria(idOuvidoria, idOrgaoOuvidoria, nomeOrgaoOuvidoria, subAssuntosOuvidoria, indEnviaDenunciasCGUPAD, indEnviaDenunciasCGUPJ, idEsfera, descEsfera, idMunicipio, descMunicipio, dataAdesaoEOuvPadrao, dataAdesaoSimplifique, dataInativacao));
                        }
                        //break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return listOuvidoria;
    }
}
