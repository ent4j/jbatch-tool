package org.jbatchtool.server.batch.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//import javax.ws.rs.core.Response;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jbatchtool.core.javax.batch.runtime.JobExecutionStub;

import lombok.extern.java.Log;

@Log
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

	/**
	 * 使用例<br>
	 * <pre>
	 * curl -X POST http://localhost:8080/jameba-server/rest/jbatch/jobExecutions
	 * </pre>
	 * @param jobName
	 * @return
	 */
	@GET
	@Path("/jobExecutions")
//	@Produces("text/plain")
	@Produces("application/json")
	public List<JobExecutionStub> getJobExecutions() {
		List<JobExecution> jobExecutions =new LinkedList<>();
		List<JobExecutionStub> jobExecutionsStub =new LinkedList<>();
		
		JobOperator jo = BatchRuntime.getJobOperator();
		
		Set<String> jobSet = jo.getJobNames();
		for (String e :jobSet) {
			String jobName = e;
			List<JobInstance> instanceList = jo.getJobInstances(jobName,0,jo.getJobInstanceCount(jobName));
			for (JobInstance instance : instanceList){
				Optional<List<JobExecution>> executionsOpt = Optional.ofNullable(jo.getJobExecutions(instance)); // 値をラップする
				executionsOpt.ifPresent(executions -> jobExecutions.addAll(executions)); // 値が存在する場合のみ実行
				executionsOpt.ifPresent(executions -> executions.stream().forEach(
					execution ->{
						log.info("JobExecution: "+ execution.getExecutionId()+" "+execution.getJobName()+" "+execution.getExitStatus()+" "+execution.getEndTime());
					}
				)
				
				); // 値が存在する場合のみ実行
			}
		}
		
		for (JobExecution e : jobExecutions){
			jobExecutionsStub.add(new JobExecutionStub(e));
		}
		return jobExecutionsStub;
	}
}
