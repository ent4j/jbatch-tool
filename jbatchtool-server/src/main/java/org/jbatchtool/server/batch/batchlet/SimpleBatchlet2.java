package org.jbatchtool.server.batch.batchlet;

import javax.batch.api.Batchlet;
import javax.batch.runtime.BatchStatus;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import lombok.extern.java.Log;

@Dependent
@Named
@Log
public class SimpleBatchlet2 implements Batchlet {

	@Override
	public String process() throws Exception {
		log.info("SimpleBatchlet2 started...");
		Thread.sleep(60000);
		log.info("SimpleBatchlet2 ending...");
		return BatchStatus.COMPLETED.toString();
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}

}
