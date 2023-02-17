import client.Client;
import entity.Record;
import org.junit.Test;
import server.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestSearchTransferRecords {
    @Test
    public void testTransfer() throws IOException, InterruptedException {
        serverStart();
        Client client = getClient();
        boolean flag1 = client.transfer(12345678, 13128310, 100);
        boolean flag2 = client.transfer(123, 13128310, 100);
        boolean flag3 = client.transfer(123, 456, 100);
        boolean flag4 = client.transfer(12345678, 13128310, -100);

        System.out.println(flag1);
        System.out.println(flag2);
        System.out.println(flag3);
        System.out.println(flag4);
    }

    @Test
    public void testSearchTransferRecords() throws IOException, InterruptedException {
        serverStart();
        Client client = getClient();
        List<Record> records1 = client.getTransferRecords(41239812);
        List<Record> records2 = client.getTransferRecords(13128310);
        List<Record> records3 = client.getTransferRecords(12345678);
        List<Record> records4 = client.getTransferRecords(1);

        System.out.println(records1);
        System.out.println(records2);
        System.out.println(records3);
        System.out.println(records4);
    }

    private void serverStart() throws IOException, InterruptedException {
        new Thread(()->{
            try {
                Server Server = new Server(12345);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.currentThread().sleep(1000);
    }

    private Client getClient() throws IOException {
        return new Client("localhost", 12345);
    }
}
