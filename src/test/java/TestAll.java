import client.Client;
import entity.Record;
import org.junit.Test;
import server.Server;

import java.io.IOException;
import java.util.List;


public class TestAll {
    @Test
    public void testTransfer() throws IOException, InterruptedException {
        serverStart();
        Client client = getClient();
        boolean flag1 = client.transfer(12345678, 13128310, 100);
        boolean flag2 = client.transfer(0, 41239812, 100);
        boolean flag3 = client.transfer(21839122,0,100);
        boolean flag4 = client.transfer(123, 12345678, 100);
        boolean flag5 = client.transfer(123, 456, 100);
        boolean flag6 = client.transfer(12345678, 13128310, -100);
        boolean flag7 = client.transfer(12345678, 13128310, 100000);

        System.out.println(flag1);
        System.out.println(flag2);
        System.out.println(flag3);
        System.out.println(flag4);
        System.out.println(flag5);
        System.out.println(flag6);
        System.out.println(flag7);
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

    @Test
    public void testRegister()throws IOException,InterruptedException{
        serverStart();
        Client client=getClient();
        boolean flag1=client.register(10086,"123","工商银行",310105,"蔡徐坤");
        boolean flag2=client.register(12345678,"123","银行",11111,"好人");

        System.out.println(flag1);
        System.out.println(flag2);
    }

    @Test
    public void testUpdatePassword()throws IOException,InterruptedException{
        serverStart();
        Client client=getClient();
        boolean flag1=client.updatePassword(10086,"35esa");
        boolean flag2=client.updatePassword(1,"password");

        System.out.println(flag1);
        System.out.println(flag2);
    }

    @Test
    public void testDeleteCard()throws IOException,InterruptedException{
        serverStart();
        Client client=getClient();
        boolean flag1=client.deleteCard(10086);
        boolean flag2=client.deleteCard(1234);
        boolean flag3=client.deleteCard(12345678);

        System.out.println(flag1);
        System.out.println(flag2);
        System.out.println(flag3);
    }

    private void serverStart() throws InterruptedException {
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
