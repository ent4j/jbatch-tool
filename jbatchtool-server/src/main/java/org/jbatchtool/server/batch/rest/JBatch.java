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

@Path("/jbatch")
public class JBatch {

	@POST
	@Path("start")
	@Produces("text/plain")
	public String startBatch() {
		JobOperator jo = BatchRuntime.getJobOperator();
		long id = jo.start("SimpleBatch" , null);//long id=2001L;
		return "job id = " + id;
	}
	
//	/**
//	 * 使用例<br>
//	 * <pre>
//	 * curl -X GET http://localhost:8080/jameba-server/rest/jbatch/job --data 'jobid=SimpleBatch'
//	 * 短縮名の候補
//	 * curl -X GET http://localhost:8080/jbatch/job --data 'jobid=SimpleBatch'
//	 * </pre>
//	 * @param jobId
//	 * @return
//	 */
////	@GET
//	@POST
//	@Path("/job")
////	@Consumes("application/x-www-form-urlencoded")
//	@Produces("text/plain")
//	public Response doGet(/*@DefaultValue("SimpleBatch")*/ @QueryParam("jobid") String jobId) {
//		JobOperator jo = BatchRuntime.getJobOperator();
//		long id = jo.start(jobId , null);
//		return Response.ok("method doGet invoked. id: "+id).build();
//	}

	/**
	 * 使用例<br>
	 * <pre>
	 * curl -X POST http://localhost:8080/jameba-server/rest/jbatch/job/SimpleBatch
	 * </pre>
	 * @param jobName
	 * @return
	 */
	@GET
	@Path("/job/{jobName}")
	@Produces("text/plain")
	public Response doPost( @PathParam("jobName") String jobName) {
		JobOperator jo = BatchRuntime.getJobOperator();
		long id = jo.start(jobName , null);
		return Response.ok(/*"method doGet invoked. id: "+*/id).build();
	}
}
