package edu.udb.cri.utils;

import java.util.ArrayList;

public class Analysis {

	public int Distancia = 0;
	public int numPosicionIni = 0;
	public int numPosicionFin = 0;	
	private ArrayList<String> arrLisSilabas;
	
public  ArrayList<String> searchString (String inputString,  int sizeString) {	
		String strSilabaA;
		String strSilabaB;
		String cadena = null;
		int numRepeticiones = 0;

		arrLisSilabas = new ArrayList<String>();
		
		for (int A=0;  A<inputString.length(); A++) {
			strSilabaA = inputString.substring(A, A+sizeString);
			
			for (int B=0;  B<inputString.length();  B++) {
				strSilabaB = inputString.substring(B, B+sizeString);
				
				if(strSilabaA.contains(strSilabaB)) {
					numRepeticiones = numRepeticiones+1; 
					
					if(numRepeticiones == 2) { 						
						numPosicionIni= B+sizeString;
						cadena = strSilabaA;
					}else if(numRepeticiones==3) {
						numPosicionFin = B;
						Distancia = numPosicionFin - numPosicionIni;
					} 
					
				}
				B=B + 2;
			}
			
			//Agregando al arreglo cadenas encontradas mas de 2 veces
			if(numRepeticiones >= 2) {
				System.out.println(cadena +" : "+ numRepeticiones );
				arrLisSilabas.add(cadena.toString() +" : "+ numRepeticiones);
			}
			
			A=A+2;
			numRepeticiones =0;
			numPosicionIni =0;
			numPosicionIni=0;
			Distancia=0;
			cadena = null ;
		}
		return arrLisSilabas;
	}

}
