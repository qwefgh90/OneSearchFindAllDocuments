package com.qwefgh90.io.jsearch.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qwefgh90.io.jsearch.extractor.PlainTextExtractor;
public class PlainTextTest {
	
	public static Logger LOG = LoggerFactory.getLogger(PlainTextTest.class);

	@Test
	public void extract() throws IOException
	{
		PlainTextExtractor e = new PlainTextExtractor();
		assertTrue(e.extract(new File(getClass().getResource("/1234.txt").getFile())));
		assertTrue(e.getText().length()>0);
		LOG.debug(String.valueOf(e.getText().length()));
		LOG.info("[평문 엔진 테스트 성공!]");
	}
}