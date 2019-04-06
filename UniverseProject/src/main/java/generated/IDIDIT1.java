package generated;
public class IDIDIT1 implements Runnable {
public IDIDIT1(){}
private  String Description ="Fdsfsdfsf";
private  int  Pula =111;
private  boolean Marja =true;
private  int Distanta =232;
public IDIDIT1( String Description ,  int  Pula ,  boolean Marja ,  int Distanta  ){
this.Description =  " Fdsfsdfsf " ;
this.Pula =111;
this.Marja =true;
this.Distanta =232;
}
    public void run() {
        System.out.println("The planet IDIDIT1 was created");
new Thread(() -> {
                int rTime = 0;
                while (rTime !=this.Distanta) {
                    try {
                        Thread.sleep(1000);
                        rTime++;
                    } catch (InterruptedException ie) {
                        System.out.println(ie.toString());
                    }
                } System.out.println("This planet IDIDIT1 was distroyed");
            }).start();    }
}
