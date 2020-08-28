import java.util.concurrent.*;

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
  // ... loop
  // (.notifyAll: Unlock + notifyAll + Lock)
  static void produce(int n) throws InterruptedException {
    String id = "produce"+n;
    synchronized (items) {
      log(id+": items="+items[0]);
      items[0] += n;
      items.notifyAll();
      log(id+": new items="+items[0]);
    }
  }

  // Consumer n:
  // 1. Lock on items
  // 2. Wait until atleast "n" items present
  // 3. Consume "n" items
  // 4. Unlock on items
  // ... loop
  // (.wait: Unlock + wait + Lock)
  static void consume(int n) throws InterruptedException {
    String id = "consume"+n;
    synchronized (items) {
      log(id+": items="+items[0]);
      while(items[0]-n < 0) {
        log(id+": too few items="+items[0]);
        items.wait();
      }
      items[0] -= n;
      log(id+": new items="+items[0]);
    }
  }

  static void producer(int n) {
    new Thread(() -> {
      try {
      while(true) {
        produce(n);
        Thread.sleep(random());
      }
      }
      catch(InterruptedException e) {}
    }).start();
  }

  static void consumer(int n) {
    new Thread(() -> {
      try {
      while(true) {
        consume(n);
        Thread.sleep(random());
      }
      }
      catch(InterruptedException e) {}
    }).start();
  }

  // 1. Start N producers and consumers.
  public static void main(String[] args) {
    items = new int[] {0};
    for(int i=1; i<=N; i++) {
      producer(i);
      consumer(i);
    }
  }
  static long random() {
    return (long) (Math.random()*1000);
  }
  static void log(String x) {
    System.out.println(x);
  }
}
