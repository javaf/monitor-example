A **monitor** is a synchronization approach that
allows threads to *wait until a condition* is
satisfied while enforcing **mutual exclusion**. A
classic example of this would be the
**producer-consumer problem** where the consumer
not only has mutual exclusive access to a list
of items, but also needs to wait if there are
insufficient items on the list. Monitors in
Java are provided using the `synchronized`
keyword along with the `wait` and `notify`,
`notifyAll` methods for the object.

The example here defines an empty list (with
just an integer) and create 4 producers and 4
consumers that produce/consume 1-4 items at a
time. If there are insufficient items for a
consumer, it waits until they are available.

> **Course**: [Concurrent Data Structures], Monsoon 2020\
> **Taught by**: Prof. Govindarajulu Regeti

[Concurrent Data Structures]: https://github.com/iiithf/concurrent-data-structures

```java
producer(n):
1. Lock on items
2. Produce "n" items
3. Notify all consumers
4. Unlock on items
... loop
(.notifyAll: Unlock + notifyAll + Lock)
```

```java
consumer(n):
1. Lock on items
2. Wait until atleast "n" items present
3. Consume "n" items
4. Unlock on items
... loop
(.wait: Unlock + wait + Lock)
```

```bash
## OUTPUT
4 producers and 4 consumers ...
P1: items=0
P1: new items=1
P4: items=1
P4: new items=5
C4: items=5
C4: new items=1
C3: items=1
C3: too few items=1
C2: items=1
C2: too few items=1
P3: items=1
P3: new items=4
C1: items=4
C1: new items=3
P2: items=3
P2: new items=5
C2: new items=3
C3: new items=0
C2: items=0
C2: too few items=0
C4: items=0
C4: too few items=0
C3: items=0
C3: too few items=0
P4: items=0
P4: new items=4
C2: new items=2
C3: too few items=2
C4: too few items=2
C1: items=2
C1: new items=1
P3: items=1
P3: new items=4
C3: new items=1
C4: too few items=1
C3: items=1
C3: too few items=1
P1: items=1
P1: new items=2
C4: too few items=2
C3: too few items=2
...
```

See [Main.java] for code, and [repl.it] for output.

[Main.java]: https://repl.it/@wolfram77/monitor-example#Main.java
[repl.it]: https://monitor-example.wolfram77.repl.run


### references

- [Synchronization 2: Monitors and Condition Variables :: Jacob Schrum](https://www.youtube.com/watch?v=15Q8PILXkQ0)
- [Java.lang.Object.wait() Method](https://www.tutorialspoint.com/java/lang/object_wait.htm)

![](https://ga-beacon.deno.dev/G-G1E8HNDZYY:v51jklKGTLmC3LAZ4rJbIQ/github.com/javaf/monitor-example)
![](https://ga-beacon.deno.dev/G-G1E8HNDZYY:v51jklKGTLmC3LAZ4rJbIQ/github.com/moocf/monitor-example.java)
