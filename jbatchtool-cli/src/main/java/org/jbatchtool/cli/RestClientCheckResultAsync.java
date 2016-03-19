/**
 * 
 */
package org.jbatchtool.cli;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * @author banz
 *
 */
public class RestClientCheckResultAsync {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("usage: args[0]: jobname");
			System.exit(1);
		}
		
		String jobName = args[0];
		String contextRoot = "jbatchtool-server";
		
		Client client = ClientBuilder.newClient();
		String uri = "http://localhost:8080/" + contextRoot + "/rest/jbatch/job/" + jobName;
		//String uri = "http://localhost:8080/jameba-server/rest/jbatch/job/SimpleBatch2";
		try {
			//Console con = System.console();
			WebTarget webTarget = client.target(uri);
//			String result = webTarget.request(MediaType.APPLICATION_XML).get(new GenericType<String>(){});
			String jobExecutionId/*result*/ = webTarget.request().get(new GenericType<String>(){});
			System.out.println(jobExecutionId);

//			String jobExecutionId
//			Optional<String> jobExecutionIdOpt = Optional.ofNullable(webTarget.request().get(new GenericType<String>(){})/*jobExecutionId*/);
			String uri2 = "http://localhost:8080/" + contextRoot + "/rest/batchstatus/" + jobExecutionId;
			WebTarget webTarget2 = client.target(uri2);
			while (true){
				Optional<String> result2Opt = Optional.ofNullable(webTarget2.request().get(new GenericType<String>(){}));
				if (result2Opt.isPresent()) {
					if ( result2Opt.get().endsWith("COMPLETED")) {
						System.out.println(result2Opt.get());
						break;
					} else if ( result2Opt.get().endsWith("FAILED")) {
						System.out.println(result2Opt.get());
						break;
					} else
						System.out.println(result2Opt.get());
						try {
							Thread.sleep(10_000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				} else {
					System.out.println("fail to get job status");
				}
			}
		
		} finally {
			client.close();
		}

	}

}
