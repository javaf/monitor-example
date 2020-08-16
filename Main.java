import java.util.concurrent.*;

class Main {
  static int[] items;
  static int N = 4;

  static void produce(int n) throws InterruptedException {
    String id = "produce"+n;
    synchronized (items) {
      log(id+": items="+items[0]);
      items[0] += n;
      items.notifyAll();
      log(id+": new items="+items[0]);
    }
  }

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
