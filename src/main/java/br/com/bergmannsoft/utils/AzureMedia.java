package br.com.bergmannsoft.utils;

import java.io.*;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.blob.models.CreateBlobOptions;
import com.microsoft.windowsazure.services.media.MediaConfiguration;
import com.microsoft.windowsazure.services.media.MediaContract;
import com.microsoft.windowsazure.services.media.MediaService;
import com.microsoft.windowsazure.services.media.WritableBlobContainerContract;
import com.microsoft.windowsazure.services.media.authentication.AzureAdClientSymmetricKey;
import com.microsoft.windowsazure.services.media.authentication.AzureAdTokenCredentials;
import com.microsoft.windowsazure.services.media.authentication.AzureAdTokenProvider;
import com.microsoft.windowsazure.services.media.authentication.AzureEnvironments;
import com.microsoft.windowsazure.services.media.models.AccessPolicy;
import com.microsoft.windowsazure.services.media.models.AccessPolicyInfo;
import com.microsoft.windowsazure.services.media.models.AccessPolicyPermission;
import com.microsoft.windowsazure.services.media.models.Asset;
import com.microsoft.windowsazure.services.media.models.AssetFile;
import com.microsoft.windowsazure.services.media.models.AssetInfo;
import com.microsoft.windowsazure.services.media.models.Locator;
import com.microsoft.windowsazure.services.media.models.LocatorInfo;
import com.microsoft.windowsazure.services.media.models.LocatorType;

import br.com.bergmannsoft.config.Config;

/**
 * 
 * 
 * IMPORTANTE: CLASSE DE TESTE, NAO UTILIZAR
 * 
 * @author Claudio
 *
 */
public class AzureMedia {

	// Media Services API
	private static MediaContract mediaService;

	public static String uploadMediaToStream(String mediaName, String endFile, String contentType, File sourceFile) {
		ExecutorService executorService = Executors.newFixedThreadPool(1);

		try {
			// Setup Azure AD Service Principal Symmetric Key Credentials
			AzureAdTokenCredentials credentials = new AzureAdTokenCredentials(Config.PRODUCTION_MEDIA_TENANT, new AzureAdClientSymmetricKey(Config.PRODUCTION_MEDIA_CLIENT_ID, Config.PRODUCTION_MEDIA_CLIENT_KEY),
					AzureEnvironments.AZURE_CLOUD_ENVIRONMENT);

			AzureAdTokenProvider provider = new AzureAdTokenProvider(credentials, executorService);

			// Create a new configuration with the credentials
			Configuration configuration = MediaConfiguration.configureWithAzureAdTokenProvider(new URI(Config.PRODUCTION_MEDIA_ENDPOINT), provider);

			// Create the media service provisioned with the new configuration
			mediaService = MediaService.create(configuration);

			// Upload a local file to an Asset
			AssetInfo uploadAsset = uploadFileAndCreateAsset(mediaName, sourceFile.getAbsolutePath(), contentType);
			System.out.println("Uploaded Asset Id: " + uploadAsset.getId());
			System.out.println("Uploaded Asset Id: " + uploadAsset.getUri());

			// Transform the Asset
			// AssetInfo encodedAsset = encode(uploadAsset);
			// System.out.println("Encoded Asset Id: " + encodedAsset.getId());
			// Create the Streaming Origin Locator
			// String url = getStreamingOriginLocator(uploadAsset);
			// System.out.println("Origin Locator URL: " + url);
			String url = getLocatorUrl(uploadAsset);
			int at = url.indexOf('?');
			return url.substring(0, at) + "/" + mediaName + endFile + url.substring(at);
		} catch (Exception e) {
			System.out.println("Exception encountered.");
			System.out.println(e.toString());
		} finally {
			executorService.shutdown();
		}
		return null;
	}

	/**
	 * 
	 * @param assetName
	 * @param fileName
	 * @return
	 * @throws ServiceException
	 * @throws FileNotFoundException
	 * @throws NoSuchAlgorithmException
	 */
	private static AssetInfo uploadFileAndCreateAsset(String assetName, String fileName, String contentType) throws ServiceException, FileNotFoundException, NoSuchAlgorithmException {

		WritableBlobContainerContract uploader;
		AssetInfo resultAsset;
		AccessPolicyInfo uploadAccessPolicy;
		LocatorInfo uploadLocator = null;
		// Create an Asset
		resultAsset = mediaService.create(Asset.create().setName(assetName).setAlternateId("altId"));
		System.out.println("Created Asset " + fileName);

		// Create an AccessPolicy that provides Write access for 15 minutes
		double durationInMinutes = 60 * 24 * 30;
		uploadAccessPolicy = mediaService.create(AccessPolicy.create("uploadAccessPolicy", durationInMinutes, EnumSet.of(AccessPolicyPermission.WRITE)));
		// Create a Locator using the AccessPolicy and Asset
		uploadLocator = mediaService.create(Locator.create(uploadAccessPolicy.getId(), resultAsset.getId(), LocatorType.SAS));

		// Create the Blob Writer using the Locator
		uploader = mediaService.createBlobWriter(uploadLocator);

		File file = new File(fileName);

		// The local file that will be uploaded to your Media Services account
		InputStream input = new FileInputStream(file);
		System.out.println("Uploading " + fileName);

		// Upload the local file to the media asset
		CreateBlobOptions blobOptions = new CreateBlobOptions();
		blobOptions.setContentType(contentType);
		blobOptions.setBlobContentType(contentType);
		uploader.createBlockBlob(file.getName(), input, blobOptions);
		// Inform Media Services about the uploaded files
		mediaService.action(AssetFile.createFileInfos(resultAsset.getId()));
		System.out.println("Uploaded Asset File " + fileName);

		mediaService.delete(Locator.delete(uploadLocator.getId()));
		mediaService.delete(AccessPolicy.delete(uploadAccessPolicy.getId()));

		return resultAsset;
	}

	/**
	 * URL Publicada
	 * 
	 * @param asset
	 * @return
	 * @throws ServiceException
	 */
	public static String getLocatorUrl(AssetInfo asset) throws ServiceException {
		double durationInMinutes = (1440 * 365) * 4;
		AccessPolicyInfo videoAccessPolicy = mediaService.create(AccessPolicy.create("Play Video", durationInMinutes, EnumSet.of(AccessPolicyPermission.READ)));
		LocatorInfo videoLocator = mediaService.create(Locator.create(videoAccessPolicy.getId(), asset.getId(), LocatorType.SAS));
		System.out.println(String.format("GetId: %s ", videoLocator.getId()));
		System.out.println(String.format("getStartTime: %s ", videoLocator.getStartTime()));
		System.out.println(String.format("getLocatorType: %s ", videoLocator.getLocatorType()));
		System.out.println(String.format("getStartTime: %s ", videoLocator.getStartTime()));
		System.out.println(String.format("setBaseUri: %s ", videoLocator.getBaseUri()));
		System.out.println(String.format("path: %s", videoLocator.getPath()));

		return videoLocator.getPath();
	}
}
