package MultiThreadedServer;

public class ThreadLimitTest {
    public static void main(String[] args) {
        int count = 0;
        try {
            while (true) {
                Thread t = new Thread(() -> {
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {}
                });
                t.start();
                count++;
                System.out.println("Thread #" + count + " started");
            }
        } catch (Throwable t) {
            System.err.println("Max threads: " + count);
            t.printStackTrace();
        }
    }
}
