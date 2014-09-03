package  com.ibm.softlayer.ticket.service;

import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.softlayer.common.client.SoftLayerServiceClient;
import com.ibm.softlayer.common.service.AbstractService;
import com.ibm.softlayer.common.util.URIGenerator;
import com.ibm.softlayer.util.APIConstants;

public class ListSubjectsService extends AbstractService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ListSubjectsService.class);

	/**
	 * Instantiates a new creates the queue service.
	 * 
	 * @param username
	 *            the username
	 * @param apikey
	 *            the apikey
	 * @param accountId
	 *            the account id
	 */

	public ListSubjectsService(String username, String apikey, String accountId) {
		super(username, apikey);
	}

//	public JSONArray list_subjects() throws Exception {
//		logger.info("Executing createQueue for Account: " + getAccountId()
//				+ ", username: " + getUsername());
//
//		// authenticate the user and retrieve the token
//		//String token = getAuthToken();
//
//		// generate the get queues URL
//		String url = URIGenerator.getRestURL(APIConstants.TICKET_SUBJECT_API);
//
//		SoftLayerServiceClient client = new SoftLayerServiceClient();
//		ClientResponse clientResponse = client.executeGET(url+"/getAllObjects", null,getCredentialsColonSeperated());
//
//		String response = clientResponse.getEntity(String.class);
//		
//		logger.info("Executed list Subject for ticket service for Account: "
//				+ getAccountId() + "clientResponse: "
//				+ clientResponse.getStatusCode());
//
//		if (clientResponse.getStatusCode() == 201
//				|| clientResponse.getStatusCode() == 200) {
//			JSONArray json = new JSONArray(response);
//			logger.debug("Create Queue: JSON Response: " + response);
//			return json;
//		}
//
//		throw new Exception("Could not create Queue: Code: "
//				+ clientResponse.getStatusCode() + ", Reason: " + response);
//
//	}

}
