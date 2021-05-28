package it.unibo.paserver.rest.controller.v1;

import it.unibo.paserver.domain.support.TwitterStatus;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Controller
public class TwitterController {

	private static final String OAUTH_CONSUMER_KEY = "rQ30JEYp75rnP2vJHsSfUbyTW";
	private static final String OAUTH_CONSUMER_SECRET = "WyZpsSridET7aEDhqFGsgB1tIkYWq2Z7m2sITNGmw78roe9hWp";
	private static final String OAUTH_ACCESS_TOKEN = "4373743198-E1w3c1ln7mp2ecVHR32YFKR4Aa2pynEQrj5CxTH";
	private static final String OAUTH_ACCESS_TOKEN_SECRET = "BfheUcSURp8e5GOaH5gtu8qL3MPwORkC01MJGVVbJmwoQ";
	private static final int MAX_TWEETS = 3;
	private static final int TWITTER_CACHE_IN_SECONDS = 600;

	private static final Logger logger = LoggerFactory
			.getLogger(TwitterController.class);

	private DateTime lastUpdate;
	private List<TwitterStatus> lastStatuses;

	@RequestMapping(value = "/rest/twitter", method = RequestMethod.GET)
	public @ResponseBody List<TwitterStatus> getTwitter() {
		logger.info("Requested latest tweets");
		if (lastStatuses == null
				|| Seconds.secondsBetween(lastUpdate, new DateTime())
						.getSeconds() > TWITTER_CACHE_IN_SECONDS) {
			lastUpdate = new DateTime();
			lastStatuses = getRecentStatus();
		}
		return lastStatuses;
	}

	private List<TwitterStatus> getRecentStatus() {
		List<TwitterStatus> result = new ArrayList<TwitterStatus>();
		logger.info("Retrieving tweet feed from @participact");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
				.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
				.setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {

			ResponseList<Status> homeTweets = twitter.getUserTimeline();
			int counter = 0;
			for (Status status : homeTweets) {
				TwitterStatus ts = new TwitterStatus();
				ts.setCreatedAt(new DateTime(status.getCreatedAt()));
				ts.setText(status.getText());
				result.add(ts);
				counter++;
				if (counter > MAX_TWEETS) {
					break;
				}
			}

		} catch (TwitterException te) {
			logger.error("Exception while retrieving tweets", te);
		}
		return result;
	}

}
