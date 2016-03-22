package org.jbatchtool.monitor.view;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
//import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
import javax.inject.Named;
//import javax.transaction.Transactional;

import lombok.Data;
import lombok.extern.java.Log;

@Named @RequestScoped @Data @Log// @Transactional
public class JobManagerManagedBean {
	
	String curTime =Calendar.getInstance().getTime().toString();
	
//	@Inject
//	private I会員情報Dao 会員情報Dao;
	private Set<String> jobSet;
	private List<JobExecution> jobExecutions =new LinkedList<>();
	
	public void 表示する(){
		this.jobExecutions.clear();// = new LinkedList<>();
		JobOperator jo = BatchRuntime.getJobOperator();
		//jo.getJobInstances(arg0, arg1, arg2)
		Set<String> jobSet = jo.getJobNames();
		for (String e :jobSet) {
			String jobName = e;
			List<JobInstance> instanceList = jo.getJobInstances(jobName,0,jo.getJobInstanceCount(jobName));
			for (JobInstance instance : instanceList){
//				List<JobExecution> executions =jo.getJobExecutions(instance);
//				for (JobExecution execution : executions) {
//					log.info("JobExecution: "+ execution.getExecutionId()+" "+execution.getJobName()+" "+execution.getExitStatus()+" "+execution.getEndTime());
//				}
//				jobExecutions.addAll(executions);
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
		log.info("Job List: "+jobSet.toString());
	}

}