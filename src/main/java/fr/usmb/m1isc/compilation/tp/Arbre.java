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
	int cpt = 0;
	
	
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
	 
	 public void data(PrintWriter f,ArrayList<String> var) {
		 if (racine == "LET") { 
			 if (fg!=null) {
				 System.out.println(var.toString());
				 if(!var.contains(fg.racine)) {
					 f.println("	"+fg.racine+" DD");
					 var.add(fg.racine);
				 }
				 System.out.println(var.toString());
				 
				 
			 }
			 
		 }
		 if (fg!=null) {
	            if (fd!=null) {
	                fg.data(f,var);
	                fd.data(f,var);
	            }
	            else {
	                fg.data(f,var);
	                
	            }
	        }
	        else {
	            if(fd!=null) {
	                fd.data(f,var);
	            }
	            
	        }
	 }
	 
	 public boolean estfeuille() {
		 return ((fg == null) && (fd == null));
	 }
	 
	 public boolean estOperateur(String r) {
		 return r.contains("+") || r.contains("-") || r.contains("*") || r.contains("/") || r.contains("INPUT") || r.contains("OUTPUT") || r.contains("%");
	 }
	 
	 public void code(Arbre g, String r, Arbre d, PrintWriter file) {
		 boolean gm = false;
		 boolean dm = false;
		 
		 System.err.println(g + " - " + d);
		 if(g != null && d != null && !r.equals("WHILE") && !r.equals("IF") && !r.equals("|") && !r.equals("&") && !r.equals("!")){
			 gm = estOperateur(g.racine);
			 dm = estOperateur(d.racine);
			 
			 if (!g.estfeuille()) {
				 if (!d.estfeuille()) {
						 code(g.fg,g.racine,g.fd,file);
						 code(d.fg,d.racine,d.fd,file);
					 
			         
			     }else{
			    	 code(g.fg,g.racine,g.fd,file);	                
			     }
			 }else {
			     if(!d.estfeuille()) {
			         code(d.fg,d.racine,d.fd,file);
			     }
			 }
		}	 
		 System.err.println("r = "+r);
		 switch(r) {
		 	case "LET":
		 		System.err.println("let");
		 		if(dm) {
		 			if(d.racine == "INPUT") {
		 				System.err.println("input");
		 				file.println("    in eax");
		 			}else {
		 				file.println("    pop eax");
		 			}
		 			
		 		}else {
		 			file.println("    mov eax, "+ d.racine);
		 		}
		 		
		 		
		 		file.println("    mov "+ g.racine +", eax");
		 		file.println("    push eax");
		 		break;
		 		
		 	case "+":
		 		System.err.println("plus");
		 		if(dm) {
		 			file.println("    pop eax");
		 		}else {
		 			file.println("    mov eax, "+g.racine);
		 		}
		 		
		 		if(gm) {
		 			file.println("    pop ebx");
		 		}else {
		 			file.println("    mov ebx, "+d.racine);
		 		}
		 		file.println("    add eax, ebx");
		 		file.println("    push eax");
		 		break;
				 
		 	case "-":
		 		System.err.println("moins");
		 		if(dm) {
		 			file.println("    pop eax");
		 		}else {
		 			file.println("    mov eax, "+g.racine);
		 		}
		 		
		 		if(gm) {
		 			file.println("    pop ebx");
		 		}else {
		 			file.println("    mov ebx, "+d.racine);
		 		}
		 		file.println("    sub eax, ebx");
		 		file.println("    push eax");
		 		break;
				 
		 	case "*":
		 		System.err.println("mul");
		 		if(dm) {
		 			file.println("    pop eax");
		 		}else {
		 			file.println("    mov eax, "+g.racine);
		 		}
		 		
		 		if(gm) {
		 			file.println("    pop ebx");
		 		}else {
		 			file.println("    mov ebx, "+d.racine);
		 		}
		 		file.println("    mul eax, ebx");
		 		file.println("    push eax");
		 		break;

		 	case "/":
		 		System.err.println("div");
		 		if(dm) {
		 			file.println("    pop eax");
		 		}else {
		 			file.println("    mov eax, "+d.racine);
		 		}
		 		
		 		if(gm) {
		 			file.println("    pop ebx");
		 		}else {
		 			file.println("    mov ebx, "+g.racine);
		 		}
		 		file.println("    div ebx, eax");
		 		file.println("    push ebx");
		 		break;
		 	
		 	case "<=":
		 		System.err.println("plusPetitEqlQue");
		 		code(g,"-", d, file);
		 		file.println("    pop eax");
		 		file.println("    jle vrai_gt_"+cpt);
		 		file.println("    mov eax, 0");
				file.println("    jmp fin_gt_"+cpt);
				file.println("vrai_gt_"+cpt+":");
		 		file.println("    mov eax, 1");
		 		file.println("fin_gt_"+cpt+":");
		 		break;
		 		
		 	case "<":
		 		System.err.println("plusPetitQue");
		 		code(g,"-", d, file);
		 		file.println("    pop eax");
		 		file.println("    jl vrai_gt_"+cpt);
		 		file.println("    mov eax, 0");
		 		file.println("    jmp fin_gt_"+cpt);
		 		file.println("vrai_gt_"+cpt+":");
		 		file.println("    mov eax, 1");
		 		file.println("fin_gt_"+cpt+":");
				 break;
				 
			case ">=":
		 		System.err.println("plusGrdEqlQue");
		 		code(d,"-", g, file);
		 		file.println("    pop eax");
		 		file.println("    jle vrai_gt_"+cpt);
		 		file.println("    mov eax, 0");
				file.println("    jmp fin_gt_"+cpt);
				file.println("vrai_gt_"+cpt+":");
		 		file.println("    mov eax, 1");
		 		file.println("fin_gt_"+cpt+":");
		 		break;
		 		
		 	case ">":
		 		System.err.println("plusGrdQue");
		 		code(d,"-", g, file);
		 		file.println("    pop eax");
		 		file.println("    jl vrai_gt_"+cpt);
		 		file.println("    mov eax, 0");
		 		file.println("    jmp fin_gt_"+cpt);
		 		file.println("vrai_gt_"+cpt+":");
		 		file.println("    mov eax, 1");
		 		file.println("fin_gt_"+cpt+":");
		 		break;
		 	
		 	case "OUTPUT":
		 		System.err.println("output");
		 		file.println("    mov eax, "+g.racine);
		 		file.println("    out eax");
		 		break;
		 		
		 	case "%":
		 		System.err.println("mod");
		 		file.println("    mov eax, "+d.racine);
		 		file.println("    push eax");
		 		file.println("    mov eax, "+g.racine);
		 		file.println("    pop ebx");
 				file.println("    mov ecx,eax");
				file.println("    div ecx,ebx");
				file.println("    mul ecx,ebx");
				file.println("    sub eax,ecx");
				file.println("    push eax");
		 		break;
		 	
		 	case "WHILE":
		 		System.err.println("while");
		 		cpt++;
		 		int cptcour = cpt;
		 		file.println("debut_while_"+cptcour+":");
			 	 //code condition
				 	code(g.fg,g.racine,g.fd,file);
				 file.println("faux_gt_"+cptcour+":");
				 //code si condition fausse 
			 	 file.println("sortie_gt_"+cptcour+":");
			 	 //code si condition vraie
			 		file.println("    jz sortie_while_"+cptcour);
			 	 	code(d.fg,d.racine,new Arbre(" "),file);
			 	 	file.println("    jmp debut_while_"+cptcour);
			 	 
			 	 file.println("sortie_while_"+cptcour+":");
		 		break;
		 	
		 	case "IF":
		 		System.err.println("if");
		 		System.out.println(d);
		 		cpt++;
		 		int cptc = cpt;
		 		file.println("debut_if_"+cptc+":");
			 	 //code condition
				 	code(g.fg,g.racine,g.fd,file);
				 file.println("sortie_gt_"+cptc+":");
				 //code si condition fausse 
				 code(d.fd.fg,d.fd.racine,new Arbre(" "),file);
				 
				 file.println("    jmp sortie_if_"+cptc);
			 	 file.println("faux_gt_"+cptc+":");
			 	 //code si condition vraie
			 	code(d.fg,d.racine,new Arbre(" "),file);
			 	 	
			 	 file.println("sortie_if_"+cptc+":");
		 		break;
		 		
		 	case "!":
		 		System.err.println("not");
		 		code(g.fg,"-", g.fg, file);
		 		file.println("    pop eax");
		 		file.println("    jle valide_gt_"+cpt);
		 		break;
		 		
		 	case "&":
		 		System.err.println("and");
		 		code(g.fg,"-", g.fd, file);
		 		file.println("    pop eax");
		 		file.println("    jle faux_gt_"+cpt);
		 		file.println("    jmp sortie_gt_"+cpt);
		 		
		 		code(d.fg,"-", d.fd, file);
		 		file.println("    pop eax");
		 		file.println("    jz valide_gt_"+cpt);
		 		file.println("    jmp invalide_gt_"+cpt);
		 		
		 		file.println("invalide_gt_"+cpt+":");
		 		file.println("    jmp sortie_gt_"+cpt);
		 		
		 		
		 		file.println("valide_gt_"+cpt+":");
		 		file.println("    mov eax,1");
		 		file.println("    jmp faux_gt_"+cpt);
		 		break;
		 		
		 	case "|":
		 		System.err.println("OR");
		 		code(g.fg,"-", g.fd, file);
		 		file.println("    pop eax");
		 		file.println("    jle faux_gt_"+cpt);
		 		
		 		code(d.fg,"-", d.fd, file);
		 		file.println("    pop eax");
		 		file.println("    jle faux_gt_"+cpt);
		 		file.println("    mov eax,1");
		 		file.println("    jmp sortie_gt_"+cpt);
		 		
		 		break;
		 		
		 	default:
		 		System.err.println("default");
		 		
		 		
		 }

        
		 
		 
}

	 public void convertToAsm(String fichier) throws FileNotFoundException, UnsupportedEncodingException {
		 PrintWriter f = new PrintWriter(fichier, "UTF-8");
		 
		 f.println("DATA SEGMENT");
		 //G�n�ration des variables
		 	data(f,var);
		 f.println("DATA ENDS");
		 
		 f.println("CODE SEGMENT");
		 //G�n�ration du code
		 	code(fg,racine,fd,f);
		 f.println("CODE ENDS");
		 
		 f.close();
		 
		 
	 }
}
