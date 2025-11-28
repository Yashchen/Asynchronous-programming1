package pr1;

public class BankSimulation {
    public static void main(String[] args) {
        Bank bank = new Bank(3);

        Thread[] clients = new Thread[8];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Thread(new Client("Клієнт " + (i + 1), bank));
            clients[i].start();
        }

        try {
            Thread.sleep(8000);
            bank.closeBank();
        } catch (InterruptedException e) {
            System.out.println("Помилка при завершенні роботи банку.");
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new Client("Пізній клієнт " + (i + 1), bank)).start();
        }
    }
}