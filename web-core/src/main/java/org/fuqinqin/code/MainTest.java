package org.fuqinqin.code;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MainTest {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase stu = mongoClient.getDatabase("stu");
        System.out.println("connect MongoDB database....");

        MongoCollection<Document> mongoCollection =  stu.getCollection("stu");
        System.out.println(mongoCollection.count());

        //插入文档
        /*Document document = new Document();
        document.append("name", "wangda");
        document.append("age", 344);
        document.append("address", new Document().append("province", "上海").append("city", "上海"));
        mongoCollection.insertOne(document);
        System.out.println("插入成功。。。。");*/

        //修改文档
        mongoCollection.updateMany(Filters.eq("age", 25), new Document("$set", new Document("age", 888)));

        // 检索文档
        FindIterable<Document> iterable = mongoCollection.find();
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()){
            Document document = cursor.next();
            System.out.println(document.toJson());
        }

    }

}
