A monitor is a synchronization approach that allows threads to wait until a condition is satisfied while enforcing mutual exclusion. A classic example of this would be the producer-consumer problem where the consumer not only has mutual exclusive access to a list of items, but also needs to wait if there are insufficient items on the list. Monitors in Java are provided using the “synchronized” keyword along with the “wait” and “notify”, “notifyAll” methods for the object. 

The example here defines an empty list (with just an integer) and create 4 producers and 4 consumers that produce/consume 1-4 items at a time. If there are insufficient items for a consumer, it waits until they are available.

See [Main.java] for code, and [repl.it] for output.

[Main.java]: https://repl.it/@wolfram77/monitor-example#Main.java
[repl.it]: https://monitor-example.wolfram77.repl.run


### references

- [Synchronization 2: Monitors and Condition Variables :: Jacob Schrum](https://www.youtube.com/watch?v=15Q8PILXkQ0)
- [Java.lang.Object.wait() Method](https://www.tutorialspoint.com/java/lang/object_wait.htm)
