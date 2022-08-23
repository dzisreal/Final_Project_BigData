package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "hoand68-readcsv");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.80.28:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //Create the producer

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        //Create filename
//        LocalDate today = LocalDate.now();
//
//        String[] lstToday = today.format(DateTimeFormatter.ISO_DATE).split("-");
//        String[]lstYesterday = today.minusDays(1).format(DateTimeFormatter.ISO_DATE).split("-");
//        String fileName = lstYesterday[0] + lstYesterday[1] +  lstYesterday[2] + "_" + lstToday[0] + lstToday[1] + lstToday[2];


        //Read csv file
         try {
            File data = new File("data\\" +  "2020_now.csv");
            Scanner scanner = new Scanner(data);
            //skip header
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //get id from line
                String id = line.split(",")[0];
                ProducerRecord<String, String> record = new ProducerRecord<>("hoand68_data", id, line);
                producer.send(record);
                TimeUnit.SECONDS.sleep(1);
            }
            scanner.close();
            //Close the producer
            producer.flush();

        }
        catch (FileNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        producer.close();



    }
}
