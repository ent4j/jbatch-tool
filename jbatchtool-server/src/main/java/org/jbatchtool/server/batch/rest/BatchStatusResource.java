package org.jbatchtool.server.batch.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.Optional;
import java.util.function.Supplier;

//import javax.ws.rs.core.Response;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/batchstatus")
public class BatchStatusResource {


	/**
	 * 使用例<br>
	 * <pre>
	 * curl -X POST http://localhost:8080/jameba-server/rest/jbatch/job/SimpleBatch
	 * </pre>
	 * @param jobId
	 * @return
	 */
	@GET
	@Path("/{jobid}")
	@Produces("text/plain")
	public BatchStatus doGet( @PathParam("jobid") long jobId) {
		JobOperator jo = BatchRuntime.getJobOperator();
		Optional<JobExecution> jobExecutionOpt = Optional.ofNullable(jo.getJobExecution(jobId));
		if (jobExecutionOpt.isPresent() == false){
			throw new NotFoundException();
		}
		return jobExecutionOpt.get().getBatchStatus();

	}
}
