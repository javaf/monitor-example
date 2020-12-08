// A monitor is a synchronization approach that
// allows threads to wait until a condition is
// satisfied while enforcing mutual exclusion. A
// classic example of this would be the
// producer-consumer problem where the consumer
// not only has mutual exclusive access to a list
// of items, but also needs to wait if there are
// insufficient items on the list. Monitors in
// Java are provided using the “synchronized”
// keyword along with the “wait” and “notify”,
// “notifyAll” methods for the object.

// The example here defines an empty list (with
// just an integer) and create 4 producers and 4
// consumers that produce/consume 1-4 items at a
// time. If there are insufficient items for a
// consumer, it waits until they are available.

class Main {
  static int[] items;
  static int N = 4;
  // items: items[0] stores no. of items
  // N: no. of producers and consumers
  // (items has to be an object for .wait())


  // Producer n:
  // 1. Lock on items
  // 2. Produce "n" items
  // 3. Notify all consumers
  // 4. Unlock on items
  // 5. Wait some time.
  // ... loop
  // (.notifyAll: Unlock + notifyAll + Lock)
  static Thread producer(int n) {
    String id = "P"+n;
    return new Thread(() -> {
      while (true) {
        synchronized (items) { // 1
          log(id+": items="+items[0]);
          items[0] += n;       // 2
          log(id+": new items="+items[0]);
          items.notifyAll();   // 3
        } // 4
        sleep(); // 5
      }
    });
  }


  // Consumer n:
  // 1. Lock on items
  // 2. Wait until atleast "n" items present
  // 3. Consume "n" items
  // 4. Unlock on items
  // 5. Wait some time.
  // ... loop
  // (.wait: Unlock + wait + Lock)
  static Thread consumer(int n) {
    String id = "C"+n;
    return new Thread(() -> {
      try {
      while (true) {
        synchronized (items) {    // 1
          log(id+": items="+items[0]);
          while(items[0]-n < 0) { // 2
            log(id+": too few items="+items[0]);
            items.wait();         // 2
          }
          items[0] -= n; // 3
          log(id+": new items="+items[0]);
        } // 4
        sleep(); // 5
      }
      }
      catch (InterruptedException e) {}
    });
  }


  // 1. Start N producers and consumers.
  public static void main(String[] args) {
    log(N+" producers and "+N+" consumers ...");
    items = new int[] {0};
    for(int i=1; i<=N; i++) {
      producer(i).start();
      consumer(i).start();
    }
  }
  

  static void sleep() {
    sleep(1000 * Math.random());
  }

  static void sleep(double t) {
    try { Thread.sleep((long)t); }
    catch (InterruptedException e) {}
  }

  static void log(String x) {
    System.out.println(x);
  }
}
