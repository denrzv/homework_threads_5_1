import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    private long getNthFibonacci(int n) {
        long a = 0, b = 1, c;
        if (n == 0)
            return a;
        for (long i = 2; i <= n; i++)
        {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Сервер запущен " + serverSocket.getInetAddress().getHostAddress() + ":" +
                    serverSocket.getLocalPort());
            System.out.println("Новое подключение от клиента " + socket.getInetAddress().getHostAddress());

            String line;
            while ((line = in.readLine()) != null && !Thread.interrupted()) {
                if (line.equals("end")) {
                    throw new InterruptedException();
                } else if (line.chars().allMatch(Character::isDigit)) {
                    System.out.println("Идёт подсчёт данных...");
                    long nth = getNthFibonacci(Integer.parseInt(line));
                    out.println("Результат вычислений -> " + nth);
                } else {
                    out.println("Введены некорректные данные для подсчёта");
                }
            }
        } catch (IOException e) {
                System.err.println("Ошибка в работе сервера");
        } catch (InterruptedException e) {
                System.out.println("Сервер завершает работу");
                Thread.currentThread().interrupt();
        }
    }
}
