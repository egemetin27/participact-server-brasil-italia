package it.unibo.paserver.rest.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.ClientSWVersion;
import it.unibo.paserver.rest.controller.v1.UserRestController;
import it.unibo.paserver.service.ClientSWVersionService;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		WebComponentsConfig.class, TestDataContextConfiguration.class })
@Transactional
public class ClientSWVersionTest {

	@Autowired
	ClientSWVersionService clientSWVersionService;

	@Autowired
	UserRestController userRestController;

	@Test
	public void defaultResponse() {
		ClientSWVersion csv = clientSWVersionService.getLatestVersion();
		assertNull(csv);
	}

	@Test
	public void defaultResponseController() {
		assertEquals(new Integer(1), userRestController.clientVersion());
	}

	@Test
	public void singleVersion() {
		ClientSWVersion csv = new ClientSWVersion();
		csv.setCreationDate(new DateTime());
		csv.setVersion(2);
		clientSWVersionService.save(csv);
		assertEquals(new Integer(2), userRestController.clientVersion());
	}

	@Test
	public void twoVersions() {
		ClientSWVersion csv = new ClientSWVersion();
		csv.setCreationDate(new DateTime());
		csv.setVersion(2);
		clientSWVersionService.save(csv);
		csv = new ClientSWVersion();
		csv.setCreationDate(new DateTime().minusDays(1));
		csv.setVersion(3);
		clientSWVersionService.save(csv);
		assertEquals(new Integer(2), userRestController.clientVersion());
	}
}
