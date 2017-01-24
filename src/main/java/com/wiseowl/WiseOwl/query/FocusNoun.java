package com.wiseowl.WiseOwl.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class FocusNoun {
	public static void main(String args[]) throws IOException
	{
		String wordnetDir = System.getProperty("wordnet.dir");
		//wordnetDir="WordNet-3.0/dict/";
		String question="Who is Abraham Lincoln?";
		AnswerTypeContextGenerator atcg=new AnswerTypeContextGenerator(new File(wordnetDir));
		String q=null;
	    String modelsDirProp = System.getProperty("model.dir");
	   // modelsDirProp="opennlp-models/";
	    File modelsDir = new File(modelsDirProp);
	    InputStream chunkerStream = new FileInputStream(
	        new File(modelsDir,"en-chunker.bin"));
	    ChunkerModel chunkerModel = new ChunkerModel(chunkerStream);
	    ChunkerME chunker = new ChunkerME(chunkerModel);
	    InputStream posStream = new FileInputStream(
	        new File(modelsDir,"en-pos-maxent.bin"));
	    POSModel posModel = new POSModel(posStream);
	    POSTaggerME tagger =  new POSTaggerME(posModel);
	    Parser parser = new ChunkParser(chunker, tagger);
	    
	    Parse query = ParserTool.parseLine(question,parser,1)[0];
		String[] context=atcg.getContext(query);
		for(int i=0;i<context.length;i++)
		{
			if(context[i].startsWith("hw=") || context[i].startsWith("mw="))
			{
				System.out.println(context[i].substring(3));
			}
		}
	}
	public String[] getFocusNoun(String question) throws IOException
	{
		String wordnetDir = System.getProperty("wordnet.dir");
		wordnetDir="WordNet-3.0/dict/";
		AnswerTypeContextGenerator atcg=new AnswerTypeContextGenerator(new File(wordnetDir));
		String q=null;
	    String modelsDirProp = System.getProperty("model.dir");
	    modelsDirProp="opennlp-models/";
	    File modelsDir = new File(modelsDirProp);
	    InputStream chunkerStream = new FileInputStream(
	        new File(modelsDir,"en-chunker.bin"));
	    ChunkerModel chunkerModel = new ChunkerModel(chunkerStream);
	    ChunkerME chunker = new ChunkerME(chunkerModel);
	    InputStream posStream = new FileInputStream(
	        new File(modelsDir,"en-pos-maxent.bin"));
	    POSModel posModel = new POSModel(posStream);
	    POSTaggerME tagger =  new POSTaggerME(posModel);
	    Parser parser = new ChunkParser(chunker, tagger);
	    
	    Parse query = ParserTool.parseLine(question,parser,1)[0];
		String[] context=atcg.getContext(query);
		String[] focus=new String[2];
		int p=0;
		for(int i=0;i<context.length;i++)
		{
			if(context[i].startsWith("hw=") || context[i].startsWith("mw="))
			{
				//System.out.println(context[i].substring(3));
				focus[p++]=context[i].substring(3);
			}
		}
		return focus;
	}
}
