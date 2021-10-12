import javax.annotation.processing.Completion;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallbackExample {

    // 스레드풀 선언
    private ExecutorService executorService;

    // 스레드 풀에 스레드 갯수 초기화
    public CallbackExample(){
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    // V - Integer A - Void 으로 콜백메서드를 가진 객체 생성
    private CompletionHandler<Integer,Void> callback =
            new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    // 작업이 정상 처리 되었을 때 호출
                    System.out.println("completed() 실행 : " +result);
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    // 예외가 발생 했을 경우 호출
                    System.out.println("failed() 실행 : " + exc.toString());
                }
            };

    public void doWork(final String x, final String y){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    int intX = Integer.parseInt(x);
                    int intY = Integer.parseInt(y);
                    int result = intX+intY;
                    // 정상처리 콜백함수 호출
                    callback.completed(result,null);
                }catch (NumberFormatException e){
                    // 예외 콜백함수 호출
                    callback.failed(e,null);
                }
            }
        };
        // 스레드풀에게 작업 요청
        executorService.submit(task);

    }
    public void finish(){
        executorService.shutdown();
    }


  public static void main(String[] args) {
    CallbackExample example = new CallbackExample();
    example.doWork("3","3");
    // 에러 발생
    example.doWork("3","삼");
    example.finish();
  }
}
