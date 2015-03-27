package com.jsearch.osfad;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jsearch.osfad.exception.AlreadyRunThreadsException;
import com.jsearch.osfad.executor.ExternalExcutor;
import com.jsearch.osfad.executor.IExternalExecutor;
import com.jsearch.osfad.job.DocumentFile;
import com.jsearch.osfad.main.ICallBack;
import com.jsearch.osfad.main.javahwp_parse;

public class HwpTest {

	/** Countdown latch */
	private CountDownLatch lock = new CountDownLatch(1);
	
	@Test
	public void hwptest(){
		// TODO Auto-generated method stub
		// 파일 객체 생성 =
		ICallBack callback = new Callback();
		IExternalExecutor docexecutor = new ExternalExcutor();
		try {
			docexecutor.findKeywordfromOneDocument(
					getClass().getResource("/HTTP.hwp").getFile().substring(1),
					"파이프라이닝", callback);

			lock.await(4000,TimeUnit.MILLISECONDS);
			docexecutor.findKeywordfromOneDocument(
					getClass().getResource("/malware_info.hwp").getFile().substring(1),
					"스파이웨어", callback);

			lock.await(4000,TimeUnit.MILLISECONDS);
			docexecutor.findKeywordfromOneDocument(
					getClass().getResource("/VHD.hwp").getFile().substring(1),
					"자식VHD", callback);
			lock.await(4000,TimeUnit.MILLISECONDS);

		} catch (AlreadyRunThreadsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class Callback implements ICallBack{

		public void callback() {

		}

		public void callbackResultList(Map<DocumentFile, List<Integer>> result) {
			System.out.println("실행 결과 : "+result.keySet());
			assertTrue(result.size()>0);
		}

	}
}

