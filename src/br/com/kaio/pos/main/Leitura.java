package br.com.kaio.pos.main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;


public class Leitura {
	
	File arquivoXML = null;
	
	public File recuperarArquivo(){
		if(arquivoXML==null){
			arquivoXML = new File("D:\\temp\\arquivo.xml");
			if(!arquivoXML.exists()){
				FileWriter writer = null;

				try {
		
					writer = new FileWriter(arquivoXML,true);
					PrintWriter saida = new PrintWriter(writer,true);
					saida.append("<xml version=\"1.0\" encoding=\"UTF-8\">\n");
					saida.close();
					writer.close();
		
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return arquivoXML;
	}
	
	public void lucroBanco() throws ElementNotFoundException, IOException{
		// defines the browser we are going to use
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
		//lucros bancarios
		HtmlPage url = webClient.getPage("http://www.feebpr.org.br/lucroban.htm");
		
		// 1 trimestre 2011
		HtmlTable pTrimestre2011 = (HtmlTable)url.getElementById("table22");
		HtmlTable pTrimestre2010 = (HtmlTable)url.getElementById("table16");
		HtmlTable pTrimestre2009 = (HtmlTable)url.getElementById("table12");
		
		
		
		StringBuffer sb = new StringBuffer();
		
		FileWriter writer = new FileWriter(recuperarArquivo(),true);
		PrintWriter saida = new PrintWriter(writer,true);
		
		int cont=0, i=0, linha =0;
		
		for(final HtmlTableRow rPT2011 : pTrimestre2011.getRows()){
			if(cont!=0 && cont!=1 ){
				sb.append("<banco>\n");
			}
			for(final HtmlTableCell cPT2011 : rPT2011.getCells()){
				if(cont!=0 && cont!=1 ){
					if(i==0){
						sb.append("<nome>");
						sb.append(cPT2011.asText());
						sb.append("</nome>\n");
					}else
					if(i==1){
						sb.append("<ano>");
						sb.append(cPT2011.asText());
						sb.append(" 2011");
						sb.append("</ano>\n");
					}else
					if(i==2){
						sb.append("<lucro>");
						sb.append(cPT2011.asText());
						sb.append("</lucro>\n");
					}
					i++;
				}
			}
			if(cont!=0 && cont!=1 ){
				sb.append("</banco>");
			}
			cont++;
			i=0;
		}
		

		cont=i=0;
		
		for (final HtmlTableRow rPT2010 : pTrimestre2010.getRows()) {
			if(linha>39){
				sb.append("\n<banco>\n");
				for (final HtmlTableCell cPT2010 : rPT2010.getCells()) {
					if(i==0){
						sb.append("<nome>");
						sb.append(cPT2010.asText());
						sb.append("</nome>\n");
					}else
					if(i==1){
						sb.append("<ano>");
						sb.append(cPT2010.asText());
						sb.append(" 2010");
						sb.append("</ano>\n");
					}else
					if(i==2){
						sb.append("<lucro>");
						sb.append(cPT2010.asText());
						sb.append("</lucro>\n");
					}
					i++;
				}
			}
			if(linha>39){
				sb.append("</banco>");
			}
			i=0;
			linha++;
		}
		
		
		cont=i=linha=0;
		
		for (final HtmlTableRow rPT2009 : pTrimestre2009.getRows()) {
			if(linha>38){
				sb.append("\n<banco>\n");
				for (final HtmlTableCell cPT2010 : rPT2009.getCells()) {
					if(i==0){
						sb.append("<nome>");
						sb.append(cPT2010.asText());
						sb.append("</nome>\n");
					}else
					if(i==1){
						sb.append("<ano>");
						sb.append(cPT2010.asText());
						sb.append(" 2009");
						sb.append("</ano>\n");
					}else
					if(i==2){
						sb.append("<lucro>");
						sb.append(cPT2010.asText());
						sb.append("</lucro>\n");
					}
					i++;
				}
			}
			if(linha>38){
				sb.append("</banco>");
			}
			i=0;
			linha++;
		}
		
		
		saida.println(sb.toString());
		
		System.out.println("Fim do arquivo!");
		
		
		saida.close();
		writer.close();
		
	}
	
	public static void main(String[] args) {
		
		Leitura l = new Leitura();
		try {
			l.lucroBanco();
		} catch (ElementNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
