package org.fuqinqin.code.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MainTest {

    public static void main(String[] args) {
        /**
         * part 1 认证
         * */
        /*//用户名，数据库，密码
        MongoCredential mongoCredential = MongoCredential.createMongoCRCredential("stu", "stu", "stu".toCharArray());
        //IP port
        ServerAddress serverAddress = new ServerAddress("192.168.227.130", 27017);

        MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(mongoCredential));*/

        /**
         * part 2 无验证
         * */
        MongoClient mongoClient = new MongoClient("192.168.227.130", 27017);

        MongoDatabase stu = mongoClient.getDatabase("stu");
        System.out.println(stu.getName());
        System.out.println("connect MongoDB database....");

        MongoCollection<Document> mongoCollection =  stu.getCollection("stu");
        System.out.println(mongoCollection.getDocumentClass());

        //插入文档
        /*Document document = new Document();
        document.append("name", "wangda");
        document.append("age", 344);
        document.append("address", new Document().append("province", "上海").append("city", "上海"));
        mongoCollection.insertOne(document);
        System.out.println("插入成功。。。。");*/

        //批量插入
        /*List<Document> documentList = new ArrayList<Document>();
        for(int i=0;i<100;i++){
            Document document = new Document();
            document.put("name", "wangda_"+i);
            document.put("age", i);
            Document d = new Document();
            d.put("province", "sichuan_"+i);
            d.put("city", "chengdu_"+i);
            document.put("address", d);
            documentList.add(document);
        }
        mongoCollection.insertMany(documentList);*/

        //修改文档
//        mongoCollection.updateMany(Filters.eq("age", 25), new Document("$set", new Document("age", 888)));

        // 检索文档
        FindIterable<Document> iterable = mongoCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()){
            Document document = cursor.next();
            System.out.println(document.toJson());
        }

        System.out.println(mongoCollection.count());

    }

}
