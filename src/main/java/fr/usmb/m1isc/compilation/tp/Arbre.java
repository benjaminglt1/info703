package fr.usmb.m1isc.compilation.tp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Arbre {
	String racine;
	Arbre fd;
	Arbre fg;
	ArrayList<String> var = new ArrayList<String>();
	
	
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
				 
				 //ne fonctionne pas - quand des var se repetent elles s'ajoutent aux var data
				 var.add(fg.racine);
				 //System.err.println(var.toString());
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
	 
	 

	 public void ecrire(String g,String op, String d,PrintWriter f) {
		 boolean gm = g.contains("+") || g.contains("-") || g.contains("*") || g.contains("/") || g.contains("INPUT") || g.contains("OUTPUT") || g.contains("%");
		 boolean dm = d.contains("+") || d.contains("-") || d.contains("*") || d.contains("/") || d.contains("INPUT") || d.contains("OUTPUT") || d.contains("%");
		 
		 System.out.println(g+op+d);
		 switch(op) {
		 	case "LET":
		 		System.err.println("let");
		 		if(dm) {
		 			if(d == "INPUT") {
		 				System.err.println("input");
		 				f.println("    in eax");
		 			}else {
		 				f.println("    pop eax");
		 			}
		 			
		 		}else {
		 			f.println("    mov eax, "+ d);
		 		}
		 		
		 		
		 		f.println("    mov "+ g +", eax");
		 		f.println("    push eax");
		 		break;
		 		
		 	case "+":
		 		System.err.println("+");
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
		 		f.println("    add eax, ebx");
		 		f.println("    push eax");
		 		break;
				 
		 	case "-":
		 		System.err.println("-");
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
		 		
		 		
		 		f.println("    div eax, ebx");
		 		f.println("    push eax");
		 		break;
		 	
		 	case "<":
		 		ecrire(g,"-", d, f);
		 		f.println("    pop eax");
		 		f.println("    jle faux_gt_1");
		 		f.println("    mov eax,1");
		 		f.println("    jmp sortie_gt_1");
		 		break;
		 		
		 	case ">":
		 		ecrire(d,"-", g, f);
		 		f.println("    pop eax");
		 		f.println("    jle faux_gt_1");
		 		f.println("    mov eax,1");
		 		f.println("    jmp sortie_gt_1");
		 		break;
		 		
		 	case "OUTPUT":
		 		System.err.println("output");
		 		f.println("    pop eax");
		 		f.println("    out eax");
		 		break;
		 		
		 	case "%":
		 		System.err.println(g+"mod"+d);
		 		f.println("    mov eax, "+d);
		 		f.println("    push eax");
		 		f.println("    mov eax, "+g);
		 		f.println("    pop ebx");
 				f.println("    mov ecx,eax");
				f.println("    div ecx,ebx");
				f.println("    mul ecx,ebx");
				f.println("    sub eax,ecx");
				f.println("    mov aux, eax");
				f.println("    mov eax, aux");
				f.println("    push eax");
		 		break;
		 		
		 	default:
		 		System.err.println(g+";"+d);
		 		
		 		
		 }
		
	 }
	 
	 public void code(Arbre g, String r, Arbre d, PrintWriter f) {
		 if(r == "OUTPUT") {
			 ecrire(g.racine,r," ",f);
			 
		 }else if(r == "WHILE") {
			 System.err.println(d.fg+"while"+d.fd);
			 f.println("debut_while_1:");
		 	 //code condition
			 	ecrire(g.fg.racine,g.racine,g.fd.racine,f);
			 f.println("faux_gt_1:");
			 //code si condition fausse 
		 	 f.println("vrai_gt_1:");
		 	 //code si condition vraie
		 		f.println("    jz sortie_while_1");
		 	 	code(d.fg,d.racine,new Arbre(" "),f);
		 	 	f.println("    jmp debut_while_1");
		 	 f.println("sortie_while_1:");
		 }else if(g.estfeuille() && d.estfeuille()) {
			 ecrire(g.racine,r,d.racine,f);
			 
		 }else if(g.estfeuille() && !d.estfeuille()){
			 code(d.fg,d.racine,d.fd,f);
			 ecrire(g.racine,r,d.racine,f);
		 }else if(!g.estfeuille() && d.estfeuille()) {
			 code(g.fg,g.racine,g.fd,f);
			 ecrire(g.racine,r,d.racine,f);
		 }else {
			 
			 code(g.fg,g.racine,g.fd,f);
			 if(d != null) {
				 code(d.fg,d.racine,d.fd,f);
			 }
			 
			 
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
