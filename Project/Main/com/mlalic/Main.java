package com.mlalic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
            int switcher = readFromStream(new FileInputStream(new File("init.txt")));
            switch(switcher) {
                case 1:
                    com.mlalic.console.Main.start();
                    break;
                case 2:
                    com.mlalic.gui.Main.start();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readFromStream(InputStream in) {
        try (Scanner s = new Scanner(in)){
            return s.nextInt();
        }
    }
}
