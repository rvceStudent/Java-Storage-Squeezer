Storage Squeezer 🛠️
A High-Performance Java System Utility for Windows Disk Optimization

📌 Overview
Storage Squeezer is a lightweight, command-line tool designed to solve the problem of limited disk space. Built specifically for Windows environments, it identifies and removes duplicate files by analyzing their actual content (binary data) rather than just their filenames.

This project was developed under the constraint of low local disk space, leading to a focus on a "zero-dependency" architecture and memory-efficient processing.

✨ Key Features
Deep Content Analysis: Uses MD5 Hashing (Java Cryptography Architecture) to create unique digital fingerprints for files, ensuring 100% accuracy in duplicate detection.

High-Speed Scanning: Leverages Java NIO.2 (Files.walkFileTree) for efficient recursive directory traversal.

Smart Filtering: Automatically ignores files smaller than 1MB to focus on high-impact storage savings.

Safety First: Includes a reporting phase that calculates potential savings before any deletion occurs.

Windows Optimized: Designed to handle Windows file permissions and system paths gracefully.

🚀 Technical Stack
Language: Java 21+

API: Java NIO (Non-blocking I/O), Java Security (MessageDigest)

Environment: Windows OS, Command Line Interface (CLI)

🛠️ How to Run
Clone the repository:
git clone https://github.com/your-username/storage-squeezer.git

Compile the program:
javac Squeezer.java

Run the utility:
java Squeezer

Input: When prompted, provide the full Windows path of the folder you wish to scan (e.g., C:\Users\Name\Downloads).

📈 Future Enhancements
[ ] Multi-threading: Implementation of Virtual Threads (Project Loom) for even faster I/O.

[ ] Recycle Bin Integration: Moving files to the Trash instead of permanent deletion.

[ ] GUI: A JavaFX dashboard for visual storage mapping.

📄 License
This project is open-source and available under the MIT License.
