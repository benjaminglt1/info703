package fr.usmb.m1isc.compilation.tp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		 boolean gm = g.contains("+") || g.contains("-") || g.contains("*") || g.contains("/");
		 
		 boolean dm = d.contains("+") || d.contains("-") || d.contains("*") || d.contains("/");
		 //System.err.println(dm);
		 switch(op) {
		 	case "LET":
		 		System.err.println("let");
		 		if(dm) {
		 			f.println("    pop eax");
		 		}else {
		 			f.println("    mov eax, "+ d);
		 		}
		 		
		 		f.println("    mov "+ g +", eax");
		 		f.println("    push eax");
		 		break;
		 		
		 	case "+":
		 		System.err.println("+");
		 		
		 		f.println("    pop ebx");
		 		f.println("    pop eax");
		 		f.println("    add eax, ebx");
		 		f.println("    push eax");
		 		break;
				 
		 	case "-":
		 		System.err.println("-");
		 		f.println("    pop ebx");
		 		f.println("    pop eax");
		 		f.println("    sub eax, ebx");
		 		f.println("    push eax");
		 		break;
				 
		 	case "*":
		 		System.err.println("*");
		 		if(dm) {
		 			f.println("    pop eax");
		 		}else {
		 			f.println("    mov eax, "+g);
		 		}
		 		
		 		if(gm) {
		 			f.println("    pop ebx");
		 		}else {
		 			f.println("    mov ebx, "+d);
		 		}
		 		
		 		f.println("    mul eax, ebx");
		 		f.println("    push eax");
		 		break;

		 	case "/":
		 		System.err.println("/");
		 		if(dm) {
		 			f.println("    pop ebx");
		 		}else {
		 			f.println("    mov ebx, "+d);
		 		}if(gm) {
		 			f.println("    pop eax");
		 		}else {
		 			f.println("    mov eax, "+g);
		 		}
		 		
		 		
		 		f.println("    div ebx, eax");
		 		f.println("    mov eax, ebx");
		 		f.println("    push eax");
		 		break;
		 	
		 	case "<":
		 		ecrire(g,"-", d, f);
		 		f.println("    pop eax");
		 		f.println("    jl vrai");
		 		f.println("    push 0");
		 		f.println("    jmp fin");
		 		f.println("    vrai : push 1");
		 		f.println("    fin :");
		 		break;
		 		
		 	case ">":
		 		ecrire(d,"-", g, f);
		 		f.println("    pop eax");
		 		f.println("    jl vrai");
		 		f.println("    push 0");
		 		f.println("    jmp fin");
		 		f.println("    vrai : push 1");
		 		f.println("    fin :");
		 		break;
		 }
		
	 }
	 
	 public void code(Arbre g, String r, Arbre d, PrintWriter f) {
		 if(g.estfeuille() && d.estfeuille()) {
			 ecrire(g.racine,r,d.racine,f);
			 
		 }else if(g.estfeuille() && !d.estfeuille()){
			 code(d.fg,d.racine,d.fd,f);
			 ecrire(g.racine,r,d.racine,f);
		 }else if(!g.estfeuille() && d.estfeuille()) {
			 code(g.fg,g.racine,g.fd,f);
			 ecrire(g.racine,r,d.racine,f);
		 }else {
			 code(g.fg,g.racine,g.fd,f);
			 code(d.fg,d.racine,d.fd,f);
			 
			 ecrire(g.racine,r,d.racine,f);
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
		 	//parcourOp(f);
		 	code(fg,racine,fd,f);
		 f.println("CODE ENDS");
		 
		 f.close();
		 
		 
	 }
}
