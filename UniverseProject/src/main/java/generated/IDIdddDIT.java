package generated;
public class IDIdddDIT implements Runnable {
public IDIdddDIT(){}
private  String Description ="Fdsfsdfsf";
private  boolean  Marja =true;
private  int Distanta =232;
public IDIdddDIT( String Description ,  boolean  Marja ,  int Distanta  ){
this.Description =  " Fdsfsdfsf " ;
this.Marja =true;
this.Distanta =232;
}
    public void run() {
        System.out.println("The planet IDIdddDIT was created");
new Thread(() -> {
                int rTime = 0;
                while (rTime !=this.Distanta) {
                    try {
                        Thread.sleep(1000);
                        rTime++;
                    } catch (InterruptedException ie) {
                        System.out.println(ie.toString());
                    }
                } System.out.println("This planet IDIdddDIT was distroyed");
            }).start();    }
}
