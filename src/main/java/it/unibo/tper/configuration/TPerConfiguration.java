package it.unibo.tper.configuration;

import it.unibo.tper.TPerProxyImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
/**
 * Importa dos dados de transporte da Italia
 * Desabilitado
 * @author Claudio
 */
@Configuration
public class TPerConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
//		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		marshaller.setPackagesToScan(new String[] { "it.unibo.tper.ws.domain" });
//		return marshaller;
		return null;
	}

	@Bean
	public TPerProxyImpl proxy(Jaxb2Marshaller marshaller) {
//		TPerProxyImpl client = new TPerProxyImpl();
//		client.setDefaultUri("https://solweb.tper.it/tperit/webservices/opendata.asmx");
//		client.setMarshaller(marshaller);
//		client.setUnmarshaller(marshaller);
//		return client;
		return null;
	}

}
