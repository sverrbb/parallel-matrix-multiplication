# Parallel Matrix Multiplication

This project was done as a compulsory assignment in the course IN3030 - Efficient Parallel Programming.

The program implements and runs three variants of Matrix Multiplication A x B:
 * one using a classic algorithm, multiplying each row in A with each column in B
 * one where B is transposed
 * one where A is transposed.
For each version i have implemented a sequential and a parallel version.

The point of the program is to show that simple techniques of changing the order of access to memory can have a substantial change in the performance of the problem we want to solve, highlighting the importance of writing cache friendly code.

The project includes a report with measurements taken and the resulting speedup, in addition to a short explanation of how i implemented the solutions and a user guide of how to run the program.
