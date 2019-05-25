/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appParking;

import java.util.Scanner;
import clientes.ClienteDAO;

/**
 *
 * @author FranAragon13
 */
public class Parking {
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Bienvenido a HINOBEPA SL, empresa que se dedica a la gestión de parkings de todo el mundo");
        System.out.println("Si usted es ya un cliente abonado, pulse 1\n"
                + "Si usted es un cliente no abonado, pulse 2\n"
                + "Si usted quiere darse de alta como abonado, pulse 3");
        int tipoCliente = teclado.nextInt();
        
        switch (tipoCliente) {
            case 1:  System.out.println("Bienvenido al portal de gestión de su abono del parking, por favor, introduzca su nombre completo: ");
                     String nombreAbonado = teclado.nextLine();
                     System.out.println("Bienvenido " + nombreAbonado + ", ¿Qué desea hacer?");
                     System.out.println("1. Retirar vehículo del parking");
                     System.out.println("2. Introducir vehículo en el parking");
                     int eleccionAbonado = teclado.nextInt();
                     switch(eleccionAbonado){
                            case 1:
                                System.out.println("Retirando vehículo del parking");
                                return clientes.ClienteDAO.;
                               
                            case 2:
                                System.out.println("Apagando luces del salón");
                                return Comando.APAGAR_LUCES_SALON;
                                
                            case 3:
                                System.out.println("Subiendo persiana del salón");
                                return Comando.SUBIR_PERSIANA_SALON;
                                
                            case 4:
                                System.out.println("Bajando persiana del salón");
                                return Comando.BAJAR_PERSIANA_SALON;
                                
                            case 5:
                                System.out.println("Dejando la persiana del salón a media altura");
                                return Comando.MEDIA_PERSIANA_SALON;
                                
                            case 6:
                                System.out.println("Conectando a camara de vigilancia del salón");
                                return Comando.CONSULTAR_VIGILANCIA_SALON;
                                
                        }
                     
                     
            break;
            case 2:  monthString = "February";
                     break;
            case 3:  monthString = "March";
                     break;
            case 4:  monthString = "April";
                     break;
            case 5:  monthString = "May";
                     break;
            case 6:  monthString = "June";
                     break;
            case 7:  monthString = "July";
                     break;
            case 8:  monthString = "August";
                     break;
            case 9:  monthString = "September";
                     break;
            case 10: monthString = "October";
                     break;
            case 11: monthString = "November";
                     break;
            case 12: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        System.out.println(monthString);
    }
}
    }
    
}
