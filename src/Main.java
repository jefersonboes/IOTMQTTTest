
/**
 *
 * @author Jeferson
 */
public class Main {

    public static void main(String[] args) {

        new Thread(() -> {
            Receiver.test();
        }).start();

        new Thread(() -> {
            Sender.test();
        }).start();

    }
}
