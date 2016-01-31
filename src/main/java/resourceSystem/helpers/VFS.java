package resourceSystem.helpers;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by stalker on 31.01.16.
 */
public class VFS {//просто обход и возврать всех имен файлов(и директорий) в директории

    public Iterator<String> getIterator(String startDir){
        return new FileIterator(startDir);
    }

    private class FileIterator implements Iterator<String>{
        Queue<File> files = new LinkedList<>();

        public FileIterator(String path){
            files.add(new File(path));
        }
        @Override
        public boolean hasNext(){
            return !files.isEmpty();
        }

        @Override
        public String next(){
            File file = files.peek();
            if(file != null && file.isDirectory()){
                for(File subFile : file.listFiles()){
                    files.add(subFile);
                }
            }

            return files.poll().getAbsolutePath();
        }
    }
}
