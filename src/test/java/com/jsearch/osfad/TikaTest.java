package com.jsearch.osfad;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

import org.apache.log4j.spi.LoggerFactory;
import org.junit.Test;

import com.googlecode.mp4parser.util.Logger;
import com.jsearch.osfad.extractor.TikaTextExtractor;


public class TikaTest {

	public static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TikaTest.class);
	
	@Test
	public void extract() throws IOException
	{
		TikaTextExtractor tika = new TikaTextExtractor();
		File f = new File(getClass().getResource("/1234.ppt").getFile());
		assertTrue(tika.extract(f));
		assertTrue(tika.getExtractText().length() > 100);
		LOG.debug(String.valueOf(tika.getExtractText().length()));
		
		tika = new TikaTextExtractor();
		f = new File(getClass().getResource("/1234.doc").getFile());
		assertTrue(tika.extract(f));
		assertTrue(tika.getExtractText().length() > 100);
		LOG.debug(String.valueOf(tika.getExtractText().length()));
		
		tika = new TikaTextExtractor();
		f = new File(getClass().getResource("/1234.xlsx").getFile());
		assertTrue(tika.extract(f));
		assertTrue(tika.getExtractText().length() > 100);
		LOG.debug(tika.getExtractText().length()+"");
	}
}
