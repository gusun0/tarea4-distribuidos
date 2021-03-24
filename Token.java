import java.io.*;
import java.net.*;
import java.lang.*;

class TokenFinal
{
	static DataInputStream entrada;
	static DataOutputStream salida;
	static boolean primera_vez = true;
	static String ip;
	static int nodo;
	static int token = 0;
	static int contador = 0;

	static class Worker extends Thread
	{
		public void run()
		{
			//Algoritmo 1
			try
			{
				//1. Declarar la variable servidor de tipo ServerSocket.
				ServerSocket servidor;
				//2. Asignar a la variable servidor el objeto: new ServerSocket(50000).
				servidor = new ServerSocket(50000);
				//3. Declarar la variable cenexion de tipo Socket.
				Socket conexion;
				//4. Asignar a la variable conexion el objeto servidor.accept().
				conexion = servidor.accept();
				//5. Asignar a la variable entrada el objeto new DataInputStream(conexion.getInputStream()).
				entrada = new DataInputStream(conexion.getInputStream());
				//System.out.println("ERROR AQUI creeado");
			}	
			catch (Exception e)
			{
				//System.out.println("ERROR AQUI");
				e.printStackTrace();
			}				
		}
	}

	public static void main (String[] args) throws Exception
	{
		if (args.length != 2)
		{
			System.err.println("\nSe solicita no. de nodo e ip.");
			System.exit(1);
		}

		nodo = Integer.valueOf(args[0]); //El primer parametro es el número del nodo.
		ip = args[1]; // El segundo parametro es la IP del siguiente nodo en el anillo.
		//Algoritmo 2
		//1. Declarar la variable W de tipo Worker.
		Worker w;
		//2. Asignar a la variable w el objeto new Worker().
		w = new Worker();
		//3. Invoca el método w.start().
		w.start();
		//4. Declarar la variable conexion de tipo Socket. Asignar null a la variable conexion.
		Socket conexion = null;
		//5. en un ciclo
		while(true) 
		{
			//5.1 En un bloque Try:
			//System.out.println("ERROR AQUI");
			try
			{
				//5.1.1 Asignar a la variable conexion el objeto Socket(ip, 50000).
				conexion = new Socket(ip, 50000);
				//5.1.2 Ejecutar break para salir del ciclo.
				break;
			}
			//5.2 En el bloque catc
			catch(Exception e)
			{
				//5.2.1 Invocar el método Thread.sleep(500).
				Thread.sleep(500);
			}
		}
		//System.out.println("ERROR AQUI");
		//6. Asignar a la variable salida el objeto newDataOutputStream(conexion.getOutputStream()).
		salida = new DataOutputStream(conexion.getOutputStream());
		//7. Invocar el método w.join().
		w.join();
		//8. En un ciclo:
		while(true)
		{
			//8.1 Si la variable nodo es cero:
			if (nodo == 0 && contador < 1000)
			{
				//8.1.1 Si la variable primera_vez es true:
				if (primera_vez == true)
				{
					
					//8.1.1.1 Asignar false a la variable primera_vez.
					primera_vez = false;
					//8.1.1.2 Asignar 1 a la variable token.
					token = 1;
				}
				//8.1.2 Si la variable primera_vez es false.
				else//(primera_vez == false)
				{
					//8.1.2.1 Asignar a la variable token el resultado del método entrada.readInt().
					token = entrada.readInt();
					//8.1.2.2 Incrementar la variable contador.
					contador ++;
					//8.1.2.3 Desplegar las variables nodo, contador y token.
					System.out.println("El valor del nodo es: " + nodo);
					System.out.println("El valor del contador:" + contador);
					System.out.println("El valor del token:" + token);
				}
			}
			//8.2 Si la variable nodo no es 0.
			else// (nodo != 0) 
			{
				//8.2.1 Asignar a la variable token el resultado del método entrada.readInt().
				token = entrada.readInt();
				//8.2.2 Incrementar la variable contador
				contador ++;
				//8.2.3 Desplegar las variables nodo, contador y token
				System.out.println("El valor del nodo:" + nodo);
				System.out.println("El valor del contador:" + contador);
				System.out.println("El valor del token:" + token);
			}
			//8.3 Si la variable nodo es cero y la variable contador es igual a 1000.
			if (nodo == 0 && contador == 1000) 
			{
				//System.out.println("ERROR AQUI, 1010");
				//8.3.1 Salir del ciclo
				break;
			}
			salida.writeInt(token);
		}
		//8.4 Invocar el método salida.wirteInt(token).
	}
}