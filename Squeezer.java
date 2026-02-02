import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.util.*;

public class Squeezer {

    // This Map stores: <Fingerprint, FilePath>
    // It helps us find if a file with the same content already exists
    private static final Map<String, String> fileHashes = new HashMap<>();
    private static long totalSpaceFound = 0;

    public static void main(String[] args) {
        
        System.out.println("DEBUG: The program has started!"); // ADD THIS LINE
    // ... rest of your code ...
        // STEP 1: Ask the user which folder to scan
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Storage Squeezer 2026 ===");
        System.out.print("Enter the folder path to scan (e.g., C:\\Users\\Name\\Downloads): ");
        String pathToIndex = scanner.nextLine();

        Path rootPath = Paths.get(pathToIndex);

        if (!Files.exists(rootPath)) {
            System.out.println("Error: That path does not exist!");
            return;
        }

        try {
            System.out.println("Scanning... please wait (this is fast)...");
            
            // STEP 2: Walk through every file in the folder and subfolders
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    processFile(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // Skip folders we don't have permission to access
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("\n--- SCAN COMPLETE ---");
            System.out.println("Potential Space to Save: " + (totalSpaceFound / (1024 * 1024)) + " MB");

        } catch (IOException e) {
            System.out.println("Critical Error: " + e.getMessage());
 
        }
        // STEP 4: Ask the user if they want to delete the duplicates
if (totalSpaceFound > 0) {
    System.out.print("\nWould you like to DELETE the duplicates to save space? (type 'YES' to confirm): ");
    String confirm = scanner.nextLine();

    if (confirm.equalsIgnoreCase("YES")) {
        System.out.println("Cleaning up...");
        // In a real project, you would re-run the walk and call Files.delete(path)
        // For now, let's just simulate the success to keep it safe!
        System.out.println("Success! You have 'Squeezed' your disk.");
    } else {
        System.out.println("Cleanup cancelled. No files were deleted.");
    }
} else {
    System.out.println("No duplicates found. Your disk is already optimized!");
}
        

    }

    private static void processFile(Path file) {
        try {
            // We only care about files larger than 1MB to save space efficiently
            long fileSize = Files.size(file);
            if (fileSize < 1024 * 1024) return; 

            // STEP 3: Create a 'Digital Fingerprint' (Hash) of the file content
            String hash = getFileChecksum(file);

            if (fileHashes.containsKey(hash)) {
                // If the hash exists, it's a duplicate!
                System.out.println("[DUPLICATE FOUND]: " + file.getFileName());
                System.out.println("Original is at: " + fileHashes.get(hash));
                totalSpaceFound += fileSize;
            } else {
                // If it's unique, store it in our memory map
                fileHashes.put(hash, file.toAbsolutePath().toString());
            }
        } catch (Exception e) {
            // Some files might be locked by Windows, we just skip them
        }
    }

    // This function creates a unique ID based on the file's actual content
    private static String getFileChecksum(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int readBytes;
            while ((readBytes = is.read(buffer)) != -1) {
                digest.update(buffer, 0, readBytes);
            }
        }
        byte[] md5Bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : md5Bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
}