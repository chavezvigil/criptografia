package edu.udb.cri.utils;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Analysis {

	private int numPosicionIni = 0;
	private int numPosicionFin = 0;	
	private ArrayList<String> arrLisSilabas;	
	private String strTrisilabaA;
	private String strTrisilabaB;   
	private int numRepeticiones = 0;
	private boolean isAnalizada = false;
	private ArrayList<Integer> ArrayMcd;

	
	public  ArrayList<String> searchStringTri (String inputString,  int sizeString) {	
    	try {
    		arrLisSilabas = new ArrayList<String>();
    		 String strTrisilaba = new String();
    		 arrLisSilabas.add(0,"AAA");
        		
        		//Obteniendo la cadena de 3 silabas a buscar
        		for (int a=0; a<inputString.length()-2; a++) {
        			strTrisilabaA = inputString.substring(a,a+sizeString);
        		
        			//Verificar si la cadena fue analisada estara dentro de arrayList  					
        			for ( int I=0 ; I <arrLisSilabas.size() ; I++ ) {
        				if (arrLisSilabas.get(I).contains(strTrisilabaA) ) {
        					isAnalizada = true;
        					break;
        				}else {        					
	        					isAnalizada = false; 	        					
	        				}	                			
        				}
        			
        			//Buscar la cadena de 3 silabas dentro de cadena ingresada
        			if(isAnalizada == false) {
	        			for (int b=0; b<inputString.length()-2; b++) {
	        				strTrisilabaB = inputString.substring(b,b+sizeString);
	        				if(strTrisilabaA.contains(strTrisilabaB)) {
	        					numRepeticiones = numRepeticiones+1;
	        					strTrisilaba = strTrisilabaA;
	        					if(numRepeticiones==2) {				
	        						numPosicionIni= b+sizeString;	        						
	        					}else if(numRepeticiones==3) {
	        						numPosicionFin = b;
	        					}			
	        				}
	        			} 
	            			arrLisSilabas.add(strTrisilaba.toString() +","+ numRepeticiones +"," +(numPosicionFin-numPosicionIni) );
	            			numRepeticiones = 0;
	            			numPosicionIni = 0;
	            			numPosicionFin = 0;  
        			}        			     			
        		}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}    	
    	return arrLisSilabas;
	}
	
	public  ArrayList<String> searchStringBis (String inputString,  int sizeString) {	
    	try {
    		arrLisSilabas = new ArrayList<String>();
    		 String strTrisilaba = new String();
    		 arrLisSilabas.add(0,"AA");
        		
        		//Obteniendo la cadena de 3 silabas a buscar
        		for (int a=0; a<inputString.length()-1; a++) {
        			strTrisilabaA = inputString.substring(a,a+sizeString);
        		
        			//Verificar si la cadena fue analisada estara dentro de arrayList  					
        			for ( int I=0 ; I <arrLisSilabas.size() ; I++ ) {
        				if (arrLisSilabas.get(I).contains(strTrisilabaA) ) {
        					isAnalizada = true;
        					break;
        				}else {        					
	        					isAnalizada = false; 	        					
	        				}	                			
        				}
        			
        			//Buscar la cadena de 3 silabas dentro de cadena ingresada
        			if(isAnalizada == false) {
	        			for (int b=0; b<inputString.length()-1; b++) {
	        				strTrisilabaB = inputString.substring(b,b+sizeString);
	        				if(strTrisilabaA.contains(strTrisilabaB)) {
	        					numRepeticiones = numRepeticiones+1;
	        					strTrisilaba = strTrisilabaA;
	        					if(numRepeticiones==2) {				
	        						numPosicionIni= b+sizeString;	        						
	        					}else if(numRepeticiones==3) {
	        						numPosicionFin = b;
	        					}			
	        				}
	        			} 
	            			arrLisSilabas.add(strTrisilaba.toString() +","+ numRepeticiones +"," +(numPosicionFin-numPosicionIni) );
	            			numRepeticiones = 0;
	            			numPosicionIni = 0;
	            			numPosicionFin = 0;  
        			}        			     			
        		}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}
    	
    	return arrLisSilabas;
	}
	
	public int getMcd(ArrayList<String> arrLista, int sizeSilaba) {
		int A, menor;
		ArrayMcd = new ArrayList<Integer>();
		
		//Crear arreglo de enteros para encontrar MCD
		if(sizeSilaba==3) {
				for ( int I=1 ; I <arrLista.size() ; I++ ) { 
					int valMostrar = Integer.parseInt(arrLista.get(I).substring(4,5)) ;
					int mcdValor;
					if(valMostrar >= 3) {
						mcdValor = Integer.parseInt(arrLista.get(I).substring(6,arrLista.get(I).length())) ;
						ArrayMcd.add(mcdValor);
					}
				}  
		}else {
			for ( int I=1 ; I <arrLista.size() ; I++ ) { 
				int valMostrar = Integer.parseInt(arrLista.get(I).substring(3,4)) ;
				int mcdValor;
				if(valMostrar >= 3) {
					mcdValor = Integer.parseInt(arrLista.get(I).substring(5,arrLista.get(I).length())) ;
					ArrayMcd.add(mcdValor);
				}
			}  
		}
        boolean no_es_mcd=false,probar_siguiente=true;        
        //Primero obtengo el menor número del array para probar a dividir
        menor=ArrayMcd.get(0);
        for ( A=0 ; A < ArrayMcd.size() ; A++ ){
            if (ArrayMcd.get(A) < menor){
                menor=ArrayMcd.get(A);
            }
        }
        //Obteniendo la variable "menor" el menor número del array      
        while (probar_siguiente==true){     
            //Ahora comprobar si "menor" es divisible por todos los números del array
            for ( A=0 ; A < ArrayMcd.size() && no_es_mcd==false; A++){
                if (ArrayMcd.get(A) % menor != 0) {
                    no_es_mcd=true;
                }
            }
            //Si no lo es, tendré que probar con el siguiente número, que será menor-1
            if (no_es_mcd){
                menor --;
                no_es_mcd=false;
            } else{
                //En caso de que todos los números sean divisible por menor, salir del bucle principal while se encontro mcd
                probar_siguiente=false;
            }
        }                    
        return menor;
	}

}
