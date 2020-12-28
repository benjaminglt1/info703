package fr.usmb.m1isc.compilation.tp;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import java_cup.runtime.Symbol;

public class Main {

	public static void main(String[] args) throws Exception  {
		 LexicalAnalyzer yy;
		 if (args.length > 0)
		        yy = new LexicalAnalyzer(new FileReader(args[0])) ;
		    else {
		    	//ex1
		    	//Reader in0 = new StringReader("let prixHt = input;\r\nlet prixTtc = prixHt * 119 / 100;\r\noutput prixTtc ."); 
		    	
		    	//ex2
		    	//Reader in0 = new StringReader("let a = input;\r\nlet b = input;\r\nwhile (0 < b)\n\rdo (let aux=(a mod b); let a=b; let b=aux );\r\noutput a\n\r.");
		    	
		    	//autres tests
		    	//Reader in0 = new StringReader("let a = 0 < 1;\r\noutput a .");
		    	//Reader in0 = new StringReader("let aux = ((3 * 3) mod 3);\r\noutput aux\n\r.");
		    	Reader in0 = new StringReader("let a = 4;\r\nlet b = 3;\r\nif (a < b) and (a < 5)\n\rthen (output b)\n\relse (output a).");
		    	yy = new LexicalAnalyzer(in0);
		    }
		@SuppressWarnings("deprecation")
		parser p = new parser (yy);
		Symbol r = p.parse( );
		
		Arbre a = (Arbre) r.value;
		
		System.out.println(a);
		
		a.convertToAsm("testAnd.asm");
		System.out.println("conversion vers machine à registre terminée");
	}

}
