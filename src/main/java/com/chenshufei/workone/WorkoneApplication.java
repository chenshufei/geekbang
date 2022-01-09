package com.chenshufei.workone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@SpringBootApplication
public class WorkoneApplication extends ClassLoader{

    public static void main(String[] args) {
        SpringApplication.run(WorkoneApplication.class, args);

        try{
            Object hello = new WorkoneApplication().findClass("Hello").newInstance();
            Class<?> aClass = hello.getClass();
            Method method = aClass.getMethod("hello");
            Object obj1 = method.invoke(hello);
            System.out.println(obj1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected Class<?> findClass(String name) {
        byte[] myClass = getMyClass();
        int a = myClass.length;
        return defineClass(name,myClass,0,a);
    }

    public static byte[] getMyClass(){
        // String path = "Hello.xlass";  src/main/resources/Hello.xlass
        byte[] byteArray = new byte[]{};
        File f = new File("src/main/resources/Hello.xlass");

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {

            }
            byteArray = byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return translate(byteArray);
    }

    // byte 转换
    public static byte[] translate(byte[] array){
        for (int i = 0; i < array.length; i++) {
            array[i] =(byte)(255- array[i]);
        }
        return array;
    }

}
