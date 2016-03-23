package org.jbatchtool.server.batch.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.core.Response;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

//@Path("/job")
public class Job {


	/**
	 * 使用例<br>
	 * <pre>
	 * curl -X POST http://localhost:8080/jbatch/rest/job/SimpleBatch
	 * </pre>
	 * @param jobId
	 * @return
	 */
//	@GET
//	@Path("/{jobid}")
//	@Produces("text/plain")
	public Response doPost( @PathParam("jobid") String jobId) {
		JobOperator jo = BatchRuntime.getJobOperator();
		long id = jo.start(jobId , null);
		return Response.ok("method doGet invoked. id: "+id).build();
	}
}
