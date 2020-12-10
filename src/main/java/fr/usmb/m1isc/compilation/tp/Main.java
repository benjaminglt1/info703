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
		    	Reader in0 = new StringReader("let prixHt = 200;\r\nlet prixTtc = prixHt * 119 / 100 ."); 
		    	yy = new LexicalAnalyzer(in0);
		    }
		@SuppressWarnings("deprecation")
		parser p = new parser (yy);
		Symbol r = p.parse( );
		
		Arbre a = (Arbre) r.value;
		
		System.out.println(a);
		
		a.convertToAsm("t.asm");
		
	}

}
