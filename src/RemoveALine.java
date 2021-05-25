import java.io.*;

public class RemoveALine {
    public static void main(String[] args) {
        try {
            File inputFile = new File("R:\\JavaFolders\\IO\\ProgramsDoneByMe\\RemovingLine\\Original.txt");
            File tempFile = new File("R:\\JavaFolders\\IO\\ProgramsDoneByMe\\RemovingLine\\temporary.txt");
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("Hey")) {
                    bw.write(line);
                    bw.write("\n");
                    bw.flush();
                }
            }
            bw.close();
            br.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename file");
            }

        } catch (IOException ex) {
            System.out.println("Error: IOException Caught");
        }
    }
}
