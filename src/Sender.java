
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Jeferson
 */
public class Sender {

    static String topic = "MYTopic:IOT:Test";
    static String broker = "tcp://iot.eclipse.org:1883";

    public static void test() {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient client = new MqttClient(broker, "sender_test", persistence);

            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable thrwbl) {
                    System.out.println("connectionLost");
                }

                @Override
                public void messageArrived(String string, MqttMessage mm) throws Exception {
                    System.out.println("messageArrived " + string + mm.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken imdt) {
                    System.out.println("deliveryComplete");
                }
            });

            client.connect();

            for (int i = 1; i <= 10; i++) {
                MqttMessage message = new MqttMessage(("Test message " + i).getBytes());
                client.publish(topic, message);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }

            client.disconnect();
        } catch (MqttException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
