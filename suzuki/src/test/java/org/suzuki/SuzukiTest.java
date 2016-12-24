package org.suzuki;

public class SuzukiTest {

    //TODO make it a test
    public void launch() {
        Suzuki suzuki = new Suzuki(2);  //TODO arg

        suzuki.executeLocked(() -> System.out.println("Doing resource"));
    }

}