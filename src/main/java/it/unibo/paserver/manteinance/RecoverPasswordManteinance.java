package it.unibo.paserver.manteinance;

import it.unibo.paserver.domain.RecoverPassword;
import it.unibo.paserver.service.RecoverPasswordService;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecoverPasswordManteinance {

	private static final Logger logger = LoggerFactory
			.getLogger(RecoverPasswordManteinance.class);

	@Autowired
	private RecoverPasswordService recoverPasswordService;

	@Scheduled(fixedDelay = 3600000)
	public void purgeOldRequests() {
		logger.info("Purging expired password recovery requests");
		List<RecoverPassword> oldRequests = recoverPasswordService
				.findExpiredAt(new DateTime());
		for (RecoverPassword rp : oldRequests) {
			recoverPasswordService.delete(rp.getId());
		}
	}
}
