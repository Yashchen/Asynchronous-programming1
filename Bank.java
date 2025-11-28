import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Bank {
    private final Semaphore atms; 
    private volatile boolean open; 

    public Bank(int atmCount) {
        this.atms = new Semaphore(atmCount);
        this.open = true;
    }

    public void useATM(String clientName) {
        if (!open) {
            System.out.println(clientName + ": Банк вже зачинений. Я піду додому.");
            return;
        }

        try {
            System.out.println(clientName + " чекає на вільний банкомат.");
            if (atms.tryAcquire(5, TimeUnit.SECONDS)) {
                System.out.println(clientName + " користується банкоматом.");
                Thread.sleep(3000);
                System.out.println(clientName + " закінчив операцію і звільнив банкомат.");
                atms.release();
            } else {
                System.out.println(clientName + ": Занадто довго чекати.");
            }
        } catch (InterruptedException e) {
            System.out.println(clientName + ": Виникла помилка під час зняття грошей.");
            Thread.currentThread().interrupt();
        }
    }

    public void closeBank() {
        open = false;
        System.out.println("\nБанк зачинено! Нові клієнти не можуть користуватись банкоматами.\n");
    }

    public boolean isOpen() {
        return open;
    }
}