package com.qwefgh90.io.jsearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qwefgh90.io.jsearch.algorithm.QS;
import com.qwefgh90.io.jsearch.extractor.HwpTextExtractorWrapper;
import com.qwefgh90.io.jsearch.extractor.PlainTextExtractor;
import com.qwefgh90.io.jsearch.extractor.TikaTextExtractor;

/**
 * 
 * @author cheochangwon
 * support various types of documents with open source engines
 * hwp, pdf, office
 */
public class JSearch {

	public static Logger LOG = LoggerFactory.getLogger(JSearch.class);

	/**
	 * extract string
	 * @param filePath - a file path where you want to extract string.
	 * @return String - a extracted string
	 * @throws IOException - a problem of file. refer to a message.
	 */
	public static String extractContentsFromFile(String filePath) throws IOException
	{
		if(filePath == null)
			throw new NullPointerException("Please input file name.");

		File target = new File(filePath);
		return extractContentsFromFile(target);
	}
	
	/**
	 * extract string
	 * @param target - a file where you want to extract string.
	 * @return String - a extracted string
	 * @throws IOException - a problem of file. refer to a message.
	 */
	public static String extractContentsFromFile(File target) throws IOException
	{
		if(target == null)
			throw new NullPointerException("Please input file name.");

		if(target.isFile() == false)
			throw new RuntimeException("The path which you input isn't File.");

		FileExtension fileExt = FileExtension.getFileFormatbyExtension(target.getAbsolutePath());

		try {
			switch(fileExt){
			case HWP:{
				HwpTextExtractorWrapper ext = new HwpTextExtractorWrapper();
				ext.extract(target);
				return ext.getText();
			}
			case PPT:{
				TikaTextExtractor ext = new TikaTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			case DOC:{
				TikaTextExtractor ext = new TikaTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			case EXCEL:{
				TikaTextExtractor ext = new TikaTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			case PDF:{
				TikaTextExtractor ext = new TikaTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			case TEXT:{
				PlainTextExtractor ext = new PlainTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			/**
			 * DEFAULT & UNKNOWN VERSION operates like TEXT VERSION
			 */
			case UNKNOWN:{
				PlainTextExtractor ext = new PlainTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			default:{
				PlainTextExtractor ext = new PlainTextExtractor();
				ext.extract(target);
				return ext.getText();
			}
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("This file doesn't exists. "+e.fillInStackTrace().toString());
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * get true or false about containing keyword.
	 * @param filePath
	 * @param keyword
	 * @return
	 * @throws IOException
	 */
	public static boolean isContainsKeywordFromFile(String filePath, String keyword) throws IOException
	{
		String text = extractContentsFromFile(filePath);
		QS qs = QS.compile(keyword);
		return qs.isExist(text);
	}

	/**
	 * get true or false about containing keyword.
	 * @param filePath
	 * @param keyword
	 * @return
	 * @throws IOException
	 */
	public static boolean isContainsKeywordFromFile(File file, String keyword) throws IOException
	{
		String text = extractContentsFromFile(file);
		QS qs = QS.compile(keyword);
		return qs.isExist(text);
	}

	/**
	 * get a list of files which are containing keyword.
	 * @param dirPath - target directory
	 * @param keyword - a keyword which you want to know.
	 * @return List<File> - a list of files which are containing keyword. 
	 * @throws IOException
	 */
	public static List<File> getFileListContainsKeywordFromDirectory(String dirPath, String keyword) throws IOException
	{
		if(dirPath == null)
			throw new NullPointerException("Please input file name.");

		File target = new File(dirPath);
		if(target.isDirectory() == false)
			throw new RuntimeException("The path which you input isn't Directory.");

		File[] fileList = target.listFiles();
		int len = fileList.length;

		List<File> result = new ArrayList<File>();

		//if a file contains keyword, add to list
		for(int i = 0; i < len; i++){
			if(fileList[i].isFile() && isContainsKeywordFromFile(fileList[i].getAbsolutePath(), keyword))
				result.add(fileList[i]);			
		}

		return result;
	}
	
	/**
	 * get a list of files which are containing keyword.
	 * @param dirPath - target directory
	 * @param keyword - a keyword which you want to know.
	 * @param recursive - recursive mode.
	 * @return List<File> - a list of files which contain keyword.
	 * @throws IOException
	 */
	public static List<File> getFileListContainsKeywordFromDirectory(String dirPath, String keyword, boolean recursive) throws IOException
	{
		if(recursive == false) 
			getFileListContainsKeywordFromDirectory(dirPath, keyword);

		if(dirPath == null)
			throw new NullPointerException("Please input file name.");

		List<File> result = new ArrayList<File>(); // return files which contain keyword.

		Queue<File> dirqueue = new LinkedList<File>(); // directory queue.
		dirqueue.add(new File(dirPath));

		File target;
		/**
		 * It is recursive.
		 * searching through files with Queue.
		 */
		while((target = dirqueue.poll()) != null){
			if(target.isDirectory() == false)
				throw new RuntimeException("The path which you input isn't Directory.");

			File[] filesInDirectory = target.listFiles();
			int len = filesInDirectory.length;

			for(int i = 0; i < len; i++){
				if(filesInDirectory[i].isDirectory()){
					dirqueue.add(filesInDirectory[i]);	//add directory file to queue.
				}else if(filesInDirectory[i].isFile()){
					if(isContainsKeywordFromFile(filesInDirectory[i], keyword))
						result.add(filesInDirectory[i]);	//add a success file to list
				}else{
					//will not execute this block?
					LOG.info("unexpected file in java system.");
				}
			}
		}

		return result;
	}

}