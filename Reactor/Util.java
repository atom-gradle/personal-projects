package Reactor;

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.*;

public class Util {

    public static String readAllContent(String path0) {
    try {
        Path path = Paths.get(path0);
        FileChannel fileChannel = FileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
        fileChannel.read(buffer);
        return buffer.toString();
    } catch(Exception e) {
        return "";
    }
    }
}
