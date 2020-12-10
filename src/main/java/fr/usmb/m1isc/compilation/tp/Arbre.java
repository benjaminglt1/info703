package fr.usmb.m1isc.compilation.tp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Arbre {
	String racine;
	Arbre fd;
	Arbre fg;
	
	
	public Arbre(String val){
		
		racine = val;
		fd = null;
		fg = null;
	}
	
	public Arbre() {
		racine = null;
		fd = null;
		fg = null;
	}
	
	public Arbre(String val, Arbre e1, Arbre e2) {
		racine = val;
		fd = e2;
		fg = e1;
	}
	
	public void setRac(String val) {
		racine = val;
	}
	
	public void setfd(String val) {
		fd = new Arbre(val);
	}
	
	public void setfg(String val) {
		fg = new Arbre(val);
	}
	
	 public String toString() {
	        if (fg!=null) {
	            if (fd!=null) {
	                return("("+racine+" "+fg.toString()+" "+fd.toString()+")");
	            }
	            else {
	                return("("+racine+" "+fg.toString()+")");
	            }
	        }
	        else {
	            if(fd!=null) {
	                return("("+racine+" "+fd.toString()+")");
	            }
	            else {
	                return(racine);
	            }
	        }
	    }
	 
	 public void parcourData(PrintWriter f) {
		 if (racine == "LET") { 
			 if (fg!=null) {
				 f.println("	"+fg.racine+" DD");
			 }
		 }
		 if (fg!=null) {
	            if (fd!=null) {
	                fg.parcourData(f);
	                fd.parcourData(f);
	            }
	            else {
	                fg.parcourData(f);
	                
	            }
	        }
	        else {
	            if(fd!=null) {
	                fd.parcourData(f);
	            }
	            
	        }
	 }
	 
	 public boolean estfeuille() {
		 return ((fg == null) && (fd == null));
	 }
	 public void parcourOp(PrintWriter f) {
		 switch(racine) {
			 case ";":
			 		fg.parcourOp(f);
			 		if(!fd.estfeuille()) {
			 			fd.parcourOp(f);
			 		}
			 		
			 		break;
			 		
		 	default:
		 		if(fg.estfeuille()&& fd.estfeuille()) {
		 			ecrire(fg.racine,racine,fd.racine,f);
		 		}else {
		 			ecrire(fg.racine,racine,fd.toString(),f);
		 			
		 		}
		 			
		 }
		 
		 
	 }
	 

	 public void ecrire(String g,String op, String d,PrintWriter f) {
		 switch(op) {
		 	case "LET":
		 		System.err.println("let");
		 		f.println("    mov eax,"+ d);
		 		f.println("    mov "+ g +", eax");
		 		break;
		 		
		 	case "+":
		 		System.err.println("+");
		 		f.println("    mov eax,"+ g);
		 		f.println("    add eax,"+d);
		 		break;
				 
		 	case "-":
		 		System.err.println("-");
		 		f.println("    mov eax,"+ g);
		 		f.println("    sub eax,"+d);
		 		break;
				 
		 	case "*":
		 		System.err.println("*");
		 		f.println("    mov eax,"+ g);
		 		f.println("    mul eax,"+d);
		 		break;
				 
		 	case "/":
		 		System.err.println("/");
		 		f.println("    mov eax,"+ g);
		 		f.println("    div eax,"+d);
		 		break;
		 	
		 }
	 }
	 
	 public void convertToAsm(String fichier) throws FileNotFoundException, UnsupportedEncodingException {
		 PrintWriter f = new PrintWriter("t.asm", "UTF-8");
		 
		 f.println("DATA SEGMENT");
		 //les variables
		 parcourData(f);
		 f.println("DATA ENDS");
		 
		 f.println("CODE SEGMENT");
		 //le code
		 	parcourOp(f);
		 f.println("CODE ENDS");
		 
		 f.close();
		 
	 }
}
