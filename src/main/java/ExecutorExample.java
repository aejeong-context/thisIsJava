import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorExample {
  public static void main(String[] args) {
      // 1개의 스레드를 사용하는 스레드풀 생성
      ExecutorService singleThread = Executors.newSingleThreadExecutor();
      // 스레드를 제한 없이 사용하는 스레드풀 생성
      ExecutorService cachedThread = Executors.newCachedThreadPool();
      // 3개의 스레드를 사용하는 스레드풀 생성
      ExecutorService fixedThread = Executors.newFixedThreadPool(3);
      // CPU 코어의 수만큼 최대 스레드를 사용하는 스레드풀 생성
      ExecutorService maxFixedThread = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

      for (int i=0;i<10;i++){
          Runnable runnable = new Runnable() {
              @Override
              public void run() {
                  ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) fixedThread;
                  int poolSize = threadPoolExecutor.getPoolSize();
                  String threadName = Thread.currentThread().getName();
                  System.out.println("총 스레드 개수 : "+poolSize+"작업 스레드 이름 :" + threadName);

                  //예외 발생 시킴
                  int value = Integer.parseInt("삼");
              }
          };
          // 작업 처리 요청
          fixedThread.submit(runnable);
          try {
              Thread.sleep(10);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      fixedThread.shutdown();
  }
}
