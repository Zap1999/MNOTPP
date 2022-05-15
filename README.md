# Methods and Technologies of Parallel Programming

## Lab 1

### Task
Implement sequential and parallel multithreaded processing of independent tasks (for example, parameter sweep -
instances of one task for different parameter values are solved). Implement examples of three types of tasks:
- CPU-bound - complex calculations with a small amount of data;
- Memory-bound - work with data stored in memory;
- IO-bound - work with data stored on disk.
Measure the dependence of execution time on the number of threads.

### Results
![Lab 1 Results](./Lab1/src/main/resources/res/chart.jpeg)
We can see that multithreading does not always stand for performance increase. In this chart we see that
increasing the number of threads for an operation execution increased the performance only for the CPU-bound operation,
meanwhile the performance of IO- and Memory-bound operations decreased with the number of threads increasing.

## Lab 2

### Task
The function f: A → B is specified, as well as the list of values of type A. Apply the function to each element of the
list. Then find and return the minimum element of the resulting list (according to the specified comparison criteria),
as well as its index.

### Results
![Lab 2 Results](./Lab2/src/main/resources/res/chart.jpeg)
We can see that multithreading dramatically increased the performance of the task, as it can be paralleled easily.

