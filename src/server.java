import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
public class server {
    public static  void main(String[] args){
        try(DatagramSocket socket =new DatagramSocket(5555)) {

            System.out.println("conectado");
            byte[] buffer =new byte[1024];
            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);
                String palabra = new String(paquete.getData(), 0, paquete.getLength(), StandardCharsets.UTF_8);

                String[] palabras = palabra.split(" ");

                String ganador = "";

                for (String p : palabras) {
                    p = p.trim();
                    if (p.length() > ganador.length()) {
                        ganador = p;
                    }
                }

                String resultado = "La palabra ganadora es: "+ ganador ;
                byte[] respuesta = (resultado).getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(respuesta,respuesta.length,paquete.getAddress(),paquete.getPort()));

            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }














}
