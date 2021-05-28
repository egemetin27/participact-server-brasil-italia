package it.unibo.tper;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import it.unibo.tper.ws.domain.extensions.FermateResponse;

/**
 * Desabilitando
 * 
 * @author Claudio
 *
 */

public class TPerProxyImpl extends WebServiceGatewaySupport implements TPerProxy {

	@Override
	public FermateResponse getOpenDataLineeFermateResponse() {
		// OpenDataLineeFermate request = new OpenDataLineeFermate();
		// OpenDataLineeFermateResponse response =
		// (OpenDataLineeFermateResponse)
		// getWebServiceTemplate().marshalSendAndReceive(request,
		// new SoapActionCallback(
		// "https://solweb.tper.it/tperit/webservices/opendata.asmx/OpenDataLineeFermate"));
		//
		// try {
		// JAXBContext payloadContext =
		// JAXBContext.newInstance(FermateResponse.class);
		// FermateResponse fermateResponse = (FermateResponse)
		// payloadContext.createUnmarshaller().unmarshal(((ElementNSImpl)
		// response.getOpenDataLineeFermateResult().getAny()).getFirstChild());
		// return fermateResponse;
		// } catch (JAXBException e) {
		// return null;
		// }
		return null;
	}
}
