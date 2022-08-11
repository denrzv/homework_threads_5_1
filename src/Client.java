import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;
    private final long START_DELAY = 1000;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);

             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            Thread.sleep(START_DELAY);
            System.out.println("Введите номер N-го члена ряда Фибоначчи для подсчёта. Для выхода наберите 'end'.");
            String msg;

            while (scanner.hasNext()) {
                msg = scanner.nextLine();
                out.println(msg);
                if (msg.equals("end")) {
                    System.out.println("Клиент завершает работу");
                    break;
                }
                System.out.println(in.readLine());
            }

        } catch (UnknownHostException e) {
            System.err.println("Неверно задан адрес сервера!");
        } catch (IOException e) {
            System.err.println("Ошибка в работе клиента!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
