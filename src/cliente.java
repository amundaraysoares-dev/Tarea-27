import com.sun.security.jgss.GSSUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class cliente {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        List<String> palabras = new ArrayList<>();
        System.out.println("Escribe un palabra o escribe salir");

        while (true){

            String palabra = teclado.nextLine();

            if (palabra.equals("salir") || palabra.equals("Salir") || palabra.equals(" ")) {

                break;
            }else {
                palabras.add(palabra);

            }
        }

        String paquete = String.join(" ",palabras);

        try (DatagramSocket socket= new DatagramSocket()){

            socket.setSoTimeout(2000);

            InetAddress server = InetAddress.getByName("localhost");

            byte[] datos = paquete.getBytes(StandardCharsets.UTF_8);
            byte[] buffer = new byte[1024];

            int contador = 0;
            boolean confrmacion = false;

            while(!confrmacion && contador <= 3){
                socket.send(new DatagramPacket(datos,datos.length,server,5555));

                DatagramPacket resultado = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(resultado);
                    System.out.println(new String(buffer,0,resultado.getLength(),StandardCharsets.UTF_8));

                    confrmacion = true;
                } catch (SocketTimeoutException e) {

                    contador++;
                    System.out.println("server no encontrado");
                }




            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }





    }
}