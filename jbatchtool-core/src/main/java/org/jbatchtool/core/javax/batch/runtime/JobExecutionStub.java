package org.jbatchtool.core.javax.batch.runtime;

import java.util.Date;
import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.Value;

//@Value
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class JobExecutionStub implements JobExecution {

	private long executionId;
	private String jobName;
	private BatchStatus batchStatus;
	private Date startTime;
	private Date endTime;
	
	private String exitStatus;
	private Date createTime;
	private Date lastUpdatedTime;
	private Properties jobParameters;
	
	public JobExecutionStub(JobExecution jobExecution) {
		super();
		this.executionId = jobExecution.getExecutionId();
		this.jobName = jobExecution.getJobName();
		this.batchStatus = jobExecution.getBatchStatus();
		this.startTime = jobExecution.getStartTime();
		this.endTime = jobExecution.getEndTime();
		this.exitStatus = jobExecution.getExitStatus();
		this.createTime = jobExecution.getCreateTime();
		this.lastUpdatedTime = jobExecution.getLastUpdatedTime();
		this.jobParameters = jobExecution.getJobParameters();
	}
	
/*	
	@Override
	public long getExecutionId() {
		return 0;
	}

	@Override
	public String getJobName() {
		return null;
	}

	@Override
	public BatchStatus getBatchStatus() {
		return null;
	}

	@Override
	public Date getStartTime() {
		return null;
	}

	@Override
	public Date getEndTime() {
		return null;
	}

	@Override
	public String getExitStatus() {
		return null;
	}

	@Override
	public Date getCreateTime() {
		return null;
	}

	@Override
	public Date getLastUpdatedTime() {
		return null;
	}

	@Override
	public Properties getJobParameters() {
		return null;
	}
*/
}
