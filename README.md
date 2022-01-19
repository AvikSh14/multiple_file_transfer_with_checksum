# multiple_file_transfer_with_checksum
This java socket program is to transfer multiple files concurrently from client to server. Integrity verification is also added.

Steps to run the program:

1. Generate some test files by running GenerateFile class. This will generate 100 files each 10MB size.
2. Then run the Server class.
3. Finally run the client class from terminal. For example : java Client client 4. (here: client is the name of the folder where test files are created).
When all files are transferred, this will print total time in milliseconds in console.
