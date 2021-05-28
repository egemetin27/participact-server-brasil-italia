package br.gov.cgu.eouv.ws.soap;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.result.soap.*;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.IssueReport;
import it.unibo.paserver.domain.StorageFile;
import it.unibo.paserver.domain.User;
import org.joda.time.DateTime;
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
 * @date 03/05/2019
 **/
@SuppressWarnings("Duplicates")
public class ServicoManterManifestacaoClient extends EOuvWebService {
    /**
     * Registro de um relato no e-OUV/Fala.BR
     *
     * @param issue
     * @return
     */
    public RegistrarManifestacaoResult registrarManifestacaoTerceiro(IssueReport issue) {
        /**
         * 2019-05-03 Claudionor
         * A ordem dos elementos influecia na resposta, não alterar a ORDEM do elementos/atributos do XML
         */
        //Tratanto antes de tudo os arquivos que serao inseridos nos comentarios
        List<String> appendix = new ArrayList<>();
        String soapFileContent = "";
        //Anexos
        List<StorageFile> fileList = issue.getFiles();
        if (fileList.size() > 0) {
            for (StorageFile f : fileList) {
                String conteudoZipadoEBase64 = Useful.encodeUrlFileBase64(f.getAssetUrl());
                if (Validator.isValidBase64(conteudoZipadoEBase64)) {
                    String nomeArquivo = f.getOriginalFilename();
                    soapFileContent += String.format("<ser:AnexoManifestacao><ser:ConteudoZipadoEBase64>%s</ser:ConteudoZipadoEBase64><ser:NomeArquivo>%s</ser:NomeArquivo></ser:AnexoManifestacao>", conteudoZipadoEBase64, nomeArquivo);
                }
                appendix.add(f.getAssetUrl());
            }
        }
        //Parte Inicial do Pacote
        String soapContent = "<ser:idTipoManifestacao>4</ser:idTipoManifestacao>";
        soapContent += String.format("<ser:idOrgaoDestinatario>%d</ser:idOrgaoDestinatario>", issue.getSiOrgId());
        String comment = issue.getComment();
        if (appendix.size() > 0) {
            for (String s : appendix) {
                comment += String.format("\n {%s}", s);
            }
        }
        soapContent += String.format("<ser:textoManifestacao>%s</ser:textoManifestacao>", comment);
        //Pacote Soap
        if (appendix.size() > 0) {
            soapContent += String.format("<ser:anexosManifestacao>%s</ser:anexosManifestacao>", soapFileContent);
        }
        //User
        User u = issue.getUser();
        soapContent += String.format("<ser:nomeManifestante>%s</ser:nomeManifestante>", u.getName());
        soapContent += "<ser:idTipoIdentificacaoManifestante>6</ser:idTipoIdentificacaoManifestante>";
        String email = issue.getPublicEmail();
        String secondary = u.getSecondaryEmail();
        if (!Validator.isValidEmail(email) && Validator.isValidEmail(secondary)) {
            email = secondary;
        }
        soapContent += String.format("<ser:email>%s</ser:email>", email);
        Gender g = u.getGender();
        if (g == Gender.MALE || g == Gender.FEMALE) {
            soapContent += String.format("<ser:sexo>%s</ser:sexo>", (g == Gender.MALE ? "M" : "F"));
        }
        //soapContent += "<ser:ddd>%d</ser:ddd>";
        String phone = u.getHomePhoneNumber();
        if (!Validator.isEmptyString(phone)) {
            soapContent += String.format("<ser:numeroTelefone>%s</ser:numeroTelefone>", phone);
        }
        soapContent += "<ser:idTipoFormulario>1</ser:idTipoFormulario>";
        String address = issue.getFormattedAddress();
        if (!Validator.isEmptyString(address)) {
            soapContent += String.format("<ser:localFato>%s</ser:localFato>", address);
        }
        soapContent += "<ser:enviarEmailCidadao>0</ser:enviarEmailCidadao>";
        soapContent += "<ser:enviarEmailOuvidoria>0</ser:enviarEmailOuvidoria>";
        //soapContent += "<ser:bairro>%s</ser:bairro>";
        //Soap
        String soapAction = String.format(Config.GCU_SOAP_ACTION_MANIFESTACAO, "RegistrarManifestacaoTerceiro");
        String soapBody = String.format(Config.GCU_SOAP_ENVELOPE, Config.GCU_SOAP_SERV_MANIFESTACAO, "<ser:RegistrarManifestacaoTerceiro>" + Config.CGU_SOAP_CREDENTIALS + soapContent + "</ser:RegistrarManifestacaoTerceiro>");
        System.out.println("----------------------------------------- \n");
        System.out.println(soapBody.toString());
        System.out.println("----------------------------------------- \n");
        //Result
        RegistrarManifestacaoResult rs = new RegistrarManifestacaoResult();
        rs.setCodigoErro(0);
        rs.setDescricaoErro("Error");
        try {
            Document document = request(Config.GCU_SOAP_URL_MANIFESTACAO, soapAction, soapBody);
            if (document != null) {
                XPath xPath = XPathFactory.newInstance().newXPath();
                Node result = (Node) xPath.compile("//RegistrarManifestacaoTerceiroResponse//RegistrarManifestacaoTerceiroResult").evaluate(document, XPathConstants.NODE);
                if (result != null && result.hasChildNodes()) {
                    NodeList nodeList = result.getChildNodes();
                    int codigoErro = Integer.parseInt(nodeList.item(0).getTextContent());
                    String descricaoErro = nodeList.item(1).getTextContent();
                    String protocolo = nodeList.item(2).getTextContent();
                    String url = nodeList.item(3).getTextContent();

                    rs.setCodigoErro(codigoErro);
                    rs.setDescricaoErro(descricaoErro);
                    rs.setProtocolo(protocolo);
                    rs.setUrl(url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        //Return
        //System.out.println(rs.toString());
        return rs;
    }


    /**
     * Dados das manifestacoes cadastradas
     *
     * @param dataAtualizacaoInicio
     * @param dataAtualizacaoFim
     * @param numProtocolo
     * @return
     */
    public ListaManifestacaoOuvidoriaResult getListaManifestacaoOuvidoria(String dataAtualizacaoInicio, String dataAtualizacaoFim, String numProtocolo) {
        //Res
        ListaManifestacaoOuvidoriaResult rs = new ListaManifestacaoOuvidoriaResult();
        rs.setCodigoErro(0);
        rs.setDescricaoErro("Error");
        //Soap Body
        String soapContent = "";
        if (!Validator.isEmptyString(dataAtualizacaoInicio)) {
            soapContent += String.format("<ser:dataAtualizacaoInicio>%s</ser:dataAtualizacaoInicio>", dataAtualizacaoInicio);
        }
        if (!Validator.isEmptyString(dataAtualizacaoFim)) {
            soapContent += String.format("<ser:dataAtualizacaoFim>%s</ser:dataAtualizacaoFim>", dataAtualizacaoFim);
        }
        if (!Validator.isEmptyString(numProtocolo)) {
            soapContent += String.format("<ser:numProtocolo>%s</ser:numProtocolo>", numProtocolo);
        }
        //Soap
        String soapAction = String.format(Config.GCU_SOAP_ACTION_CONSULTA_MANIFESTACAO, "GetListaManifestacaoOuvidoria");
        String soapBody = String.format(Config.GCU_SOAP_ENVELOPE, Config.GCU_SOAP_SERV_CONSULTA_MANIFESTACAO, "<ser:GetListaManifestacaoOuvidoria>" + Config.CGU_SOAP_CREDENTIALS + soapContent + "</ser:GetListaManifestacaoOuvidoria>");
        System.out.println("----------------------------------------- \n");
        System.out.println(soapBody.toString());
        System.out.println("----------------------------------------- \n");
        try {
            Document document = request(Config.GCU_SOAP_URL_CONSULTA_MANIFESTACAO, soapAction, soapBody);
            if (document != null) {
                XPath xPath = XPathFactory.newInstance().newXPath();
                Node result = (Node) xPath.compile("//GetListaManifestacaoOuvidoriaResponse//GetListaManifestacaoOuvidoriaResult").evaluate(document, XPathConstants.NODE);
                if (result != null && result.hasChildNodes()) {
                    NodeList nodeList = result.getChildNodes();
                    int codigoErro = Integer.parseInt(nodeList.item(0).getTextContent());
                    String descricaoErro = nodeList.item(1).getTextContent();
                    if (codigoErro == 0) {
                        NodeList nanifestacoesOuvidoria = nodeList.item(2).getChildNodes();
                        for (int x = 0; x < nanifestacoesOuvidoria.getLength(); x++) {
                            //ManifestacaoOuvidoria
                            ManifestacaoOuvidoriaResult manifestacaoOuvidoriaResult = new ManifestacaoOuvidoriaResult();
                            NodeList manifestacaoOuvidoria = nanifestacoesOuvidoria.item(x).getChildNodes();
                            for (int z = 0; z < manifestacaoOuvidoria.getLength(); z++) {
                                String nn = manifestacaoOuvidoria.item(z).getNodeName();
                                //String vv = manifestacaoOuvidoria.item(z).getTextContent();
                                //System.out.println(nn);
                                if (nn != null) {
                                    if (nn.equals("ListaHistoricosManifestacao")) {
                                        NodeList listaHistoricosManifestacao = manifestacaoOuvidoria.item(z).getChildNodes();
                                        List<HistoricoAcaoManifestacaoResult> historico = new ArrayList<>();
                                        //System.out.println(manifestacaoOuvidoria.item(z).getNodeName());
                                        //HistoricoAcaoManifestacao
                                        for (int y = 0; y < listaHistoricosManifestacao.getLength(); y++) {
                                            NodeList historicoAcaoManifestacao = listaHistoricosManifestacao.item(y).getChildNodes();
                                            String acao = historicoAcaoManifestacao.item(0).getTextContent();
                                            DateTime dataAcao = Useful.converteStringToDate(Useful.datetimeSystemToDb(historicoAcaoManifestacao.item(1).getTextContent()));
                                            String infoAdicionais = historicoAcaoManifestacao.item(2).getTextContent();
                                            historico.add(new HistoricoAcaoManifestacaoResult(acao, dataAcao, infoAdicionais, null));
                                            //System.out.println(listaHistoricosManifestacao.item(y).getNodeName());
                                        }
                                        manifestacaoOuvidoriaResult.setListaHistoricosManifestacao(historico);
                                    } else if (nn.equals("a:ListaRespostasManifestacao")) {
                                        NodeList listaRespostasManifestacao = manifestacaoOuvidoria.item(z).getChildNodes();
                                        //System.out.println(manifestacaoOuvidoria.item(z).toString());
                                        List<RespostasManifestacaoResult> respostas = new ArrayList<>();
                                        //ListaRespostasManifestacao
                                        for (int y = 0; y < listaRespostasManifestacao.getLength(); y++) {
                                            NodeList respostasManifestacao = listaRespostasManifestacao.item(y).getChildNodes();
                                            String dataPublicacao = respostasManifestacao.item(0).getTextContent();
                                            String respondente = respostasManifestacao.item(2).getTextContent();
                                            String texto = respostasManifestacao.item(3).getTextContent();
                                            String tipo = respostasManifestacao.item(4).getTextContent();
                                            String dataCompromisso = respostasManifestacao.item(5).getTextContent();
                                            String decisao = respostasManifestacao.item(6).getTextContent();
                                            DateTime dataResposta = Useful.converteStringToDate(Useful.datetimeSystemToDb(respostasManifestacao.item(0).getTextContent()));

                                            respostas.add(new RespostasManifestacaoResult(tipo, texto, respondente, dataPublicacao, dataCompromisso, decisao, dataResposta));
                                        }
                                        manifestacaoOuvidoriaResult.setListaRespostasManifestacao(respostas);
                                    } else if (nn.equals("NumProtocolo")) {
                                        manifestacaoOuvidoriaResult.setNumProtocolo(manifestacaoOuvidoria.item(z).getTextContent());
                                    } else if (nn.equals("CodSiorgOrgaoAssociadoOuvDestinataria")) {
                                        manifestacaoOuvidoriaResult.setCodSiorgOrgaoAssociadoOuvDestinataria(Integer.parseInt(manifestacaoOuvidoria.item(z).getTextContent()));
                                    } else if (nn.equals("DescSituacaoManifestacao")) {
                                        manifestacaoOuvidoriaResult.setDescSituacaoManifestacao(manifestacaoOuvidoria.item(z).getTextContent());
                                    } else if (nn.equals("DescSubAssunto")) {
                                        manifestacaoOuvidoriaResult.setDescSubAssunto(manifestacaoOuvidoria.item(z).getTextContent());
                                    } else if (nn.equals("DescTipoManifestacao")) {
                                        manifestacaoOuvidoriaResult.setDescTipoManifestacao(manifestacaoOuvidoria.item(z).getTextContent());
                                    } else if (nn.equals("Observacao")) {
                                        manifestacaoOuvidoriaResult.setObservacao(manifestacaoOuvidoria.item(z).getTextContent());
                                    }
                                }
                            }
                            rs.addManifestacoesOuvidoria(manifestacaoOuvidoriaResult);
                        }
                    }
                    //Resultado
                    rs.setCodigoErro(codigoErro);
                    rs.setDescricaoErro(descricaoErro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return rs;
    }
}
