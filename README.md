# Excel File Analysis

This repository contains a Java program (Bluejay.java) designed to analyze an Excel file with specific columns. The analysis includes identifying employees who have:

- Worked for 7 consecutive days
- Worked shifts with less than 10 hours between them but greater than 1 hour
- Worked for more than 14 hours in a single shift

## Usage

1. **Bluejay.java:** The Java program to perform the analysis. The file assumes the input data is in a CSV format and processes the specified columns.

2. **Output.txt:** The output file containing the results of the analysis. Each identified scenario is logged in this file.

## How to Run

1. Ensure you have Java installed on your machine.
2. Modify the `filePath` variable in Bluejay.java to point to your Excel file.
3. Compile and run Bluejay.java.

```bash
javac Bluejay.java
java Bluejay
