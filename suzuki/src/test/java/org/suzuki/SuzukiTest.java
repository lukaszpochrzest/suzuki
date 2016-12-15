package org.suzuki;

public class SuzukiTest {

    public void launch() {
        Suzuki suzuki = new Suzuki();

        suzuki.executeLocked(() -> System.out.println("Doing resource"));
    }

}