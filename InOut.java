package ru.geekbrains.lesson3;

import java.io.*; // input output
import java.nio.*; // new io
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InOut {
    public static void main(String[] args) {
        final File file = new File("C:\\mydir\\myfile.txt"); //создание инстанса файла (не путать с созданием реального файла локально)
        String filename = "mydir" + File.separator + "myfile.txt"; // так принято разделять путь к файлу вместо "//"
        final File file2 = new File("mydir", "myfile.txt"); // родительский и дочерний путь к файлу
        final File file1 = new File("123.txt");
        System.out.println(file1.getAbsolutePath()); // выводит в консоль абсолютный путь к файлу
        System.out.println(file.exists()); // проверяет, существует ли реально тот файл
        System.out.println("file1.getParent() = " + file1.getParent()); // возвращает путь к файлу, если указывать абсолютный путь
        System.out.println("file.getName() = " + file.getName()); // возвращает имя файла
        System.out.println("file.isFile() = " + file.isFile());
        System.out.println("file.isDirectory() = " + file.isDirectory()); // проверка файл или директория
        File dir = file.getParentFile(); // получение директории файла
        for (String s : dir.list()) {
            System.out.println(s); // покажет список файлов в директории
        }
        final boolean mkdir = file.mkdir(); // создание директории
        final boolean mkdir1 = file.mkdirs(); // создание сразу нескольких директорий одна в другой

        InOut inOut = new InOut();
        inOut.fileWalker(new File("C:\\Java\\course3"));

    }


    private void fileWalker(File file) { // проходит по указанному пути и выводит все файлы
        for (File child : file.listFiles()) {
            if (child.isDirectory()) {
                fileWalker(child);
            } else {
                System.out.println(child.getAbsolutePath());
            }
        }
    }

    private void sameFileWalker() { // делает тоже что и метод fileWalker
        final Path start = Paths.get("C:", "Java", "course3");
        List<Path> result = new ArrayList<>();
        try (final Stream<Path> walk = Files.walk(start)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.forEach(System.out::println);
    }

    private void toByteArray() {
        byte[] arr = {66, 67, -1, -2, -3};
        final ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        int x;
        while ((x = bais.read()) != -1) {
            System.out.println(x);
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(66);
        baos.write(67);
        baos.write(300);
        final byte[] bytes = baos.toByteArray();
        System.out.println(Arrays.toString(bytes));
    }

    private void inOutputStream() {
        try(final OutputStream fos = new BufferedOutputStream(new FileOutputStream("demo.txt"))) { // создание и запись в файл данных
            // Buffered считывает сразу весь файл, а не по байтам
            fos.write("Java".getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(final InputStream fis = new BufferedInputStream(new FileInputStream("demo.txt"))) { // чтение с файла
            int x;
            while ((x = fis.read()) != -1) {
                System.out.println(x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void DataInOutputStream() throws FileNotFoundException { // запись и чтение данных
        try(final DataOutputStream dos = new DataOutputStream(new FileOutputStream("3.txt"))) {
            dos.writeInt(2000);
            dos.writeFloat(2000.12f);
            dos.writeBoolean(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(final DataInputStream dis = new DataInputStream(new FileInputStream("3.txt"))) {
            dis.readInt();
            dis.readFloat();
            dis.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void classToByte() { // запись и чтение объектов (классов) в байтах (сериализация)
        byte[] catBytes = null;
        try(final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            final Cat cat = new Cat("Барсик", 7);
            objectOutputStream.writeObject(cat);
            catBytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(catBytes);
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            final Cat o = (Cat) objectInputStream.readObject();
            System.out.println(o.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void piped() { //
        try(final PipedInputStream in = new PipedInputStream();
            final PipedOutputStream out = new PipedOutputStream(in)) {
            for (int i = 0; i < 10; i++) {
                out.write(i);
            }
            int x;
            while ((x = in.read()) != -1) {
                System.out.println(x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sequence() { // считывает с двух файлов и записывает в третий
        try(final SequenceInputStream seq = new SequenceInputStream(new FileInputStream("1.txt"), new FileInputStream("2.txt"));
            final FileOutputStream fos = new FileOutputStream("3.txt")) {
            int x;
            while ((x = seq.read()) != -1) {
                fos.write(x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stringWriteRead() { // символьная запись и чтение файлов (строковая)
        try(final BufferedWriter writer = new BufferedWriter(new FileWriter("demo1.txt"))) {
            for (int i = 0; i < 20; i++) {
                writer.write("Java");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(final BufferedReader reader = new BufferedReader(new FileReader("demo1.txt"))) {
            String str;
            while ((str = reader.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void randomAccess() { // чтение 4-го символа в файле и запись (замена) его
        try(final RandomAccessFile raf = new RandomAccessFile("demo.txt", "rw")) {
            raf.seek(3);
            System.out.println((char) raf.read());
            raf.write('A');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
