package org.jbatchtool.monitor.view;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.batch.runtime.JobExecution;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.jbatchtool.core.javax.batch.runtime.JobExecutionStub;

import lombok.Data;
import lombok.extern.java.Log;

@Named @RequestScoped @Data @Log// @Transactional
public class JobManagerManagedBean {
	
	String curTime =Calendar.getInstance().getTime().toString();
	
	private Set<String> jobSet;
	private List<JobExecution> jobExecutions =new LinkedList<>();
	
	public void 表示する(){
		this.jobExecutions.clear();// = new LinkedList<>();

		String contextRoot = "jbatchtool-server";
		
		Client client = ClientBuilder.newClient();
		String uri = "http://localhost:8080/" + contextRoot + "/rest/jbatch/jobExecutions";
//		String uri = "http://localhost:8080/" + contextRoot + "/rest/jbatch/job/simplebatch";
		try {
			WebTarget webTarget = client.target(uri);
//			while (true){
				Optional<List<JobExecutionStub>> result2Opt = Optional.ofNullable(webTarget.request().get(new GenericType<List<JobExecutionStub>>(){}));
				if (result2Opt.isPresent()) {
					List<JobExecutionStub> jobExecutionsStub = result2Opt.get();
					List<JobExecution> jobExecutions = new LinkedList<>();
					for (JobExecution e : jobExecutionsStub) {
						jobExecutions.add(e);
					}
					this.jobExecutions = jobExecutions;
				} else {
					log.info("fail to get job status");
				}
//			}
		} finally {
			client.close();
		}

//		log.info("Job List: "+jobSet.toString());
	}

}