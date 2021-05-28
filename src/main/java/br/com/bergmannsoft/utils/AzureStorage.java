package br.com.bergmannsoft.utils;

import java.io.File;

//MIT License
//Copyright (c) Microsoft Corporation. All rights reserved.
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import br.com.bergmannsoft.config.Config;

/* *************************************************************************************************************************
 * Summary: This application demonstrates how to use the Blob Storage service.
 * It does so by creating a container, creating a file, then uploading that file, listing all files in a container, 
 * and downloading the file. Then it deletes all the resources it created
 * 
 * Documentation References:
 * Associated Article - https://docs.microsoft.com/en-us/azure/storage/blobs/storage-quickstart-blobs-java
 * What is a Storage Account - http://azure.microsoft.com/en-us/documentation/articles/storage-whatis-account/
 * Getting Started with Blobs - http://azure.microsoft.com/en-us/documentation/articles/storage-dotnet-how-to-use-blobs/
 * Blob Service Concepts - http://msdn.microsoft.com/en-us/library/dd179376.aspx 
 * Blob Service REST API - http://msdn.microsoft.com/en-us/library/dd135733.aspx
 * *************************************************************************************************************************
 */
public class AzureStorage {
	/*
	 * *****************************************************************************
	 * ******************************************** Instructions: Update the
	 * storageConnectionString variable with your AccountName and Key and then run
	 * the sample.
	 * *****************************************************************************
	 * ********************************************
	 */
	private static final String storageConnectionString = Config.PRODUCTION_STORAGE_ENDPOINTCONNECTION;

	public static void uploadBlod(File sourceFile, String contentType) {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		try {

			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(Config.PRODUCTION_STORAGE_ENDPOINTCONNECTION);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(Config.PRODUCTION_STORAGE_CONTAINER);

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
			if (contentType != null) {
				blob.getProperties().setContentType(contentType);
			}
			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob.uploadFromFile(sourceFile.getAbsolutePath());
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s", ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println("Deleting the source, and downloaded files");
			if (sourceFile != null)
				sourceFile.deleteOnExit();
		}
	}
}
