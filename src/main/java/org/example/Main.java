package org.example;

public class Main {
    public static void main(String[] args) {
        MyStringBuilder sb = new MyStringBuilder("Hello");
        sb.append(" World");
        System.out.println("После append:   " + sb);

        sb.undo();
        System.out.println("После undo:     " + sb);

        MyStringBuilder sb2 = new MyStringBuilder("ABCDEFG");
        sb2.delete(2, 5);
        System.out.println("После delete:   " + sb2);

        MyStringBuilder sb3 = new MyStringBuilder("Hello");
        sb3.setCharAt(1, 'a');
        System.out.println("После setChar:  " + sb3);

        MyStringBuilder sb4 = new MyStringBuilder("A");
        sb4.append("B").append("C").append("D");
        System.out.println("До undo:        " + sb4);
        sb4.undo();
        System.out.println("undo #1:        " + sb4);
        sb4.undo();
        System.out.println("undo #2:        " + sb4);
        sb4.undo();
        System.out.println("undo #3:        " + sb4);

        MyStringBuilder sb5 = new MyStringBuilder("X");
        sb5.undo();
        System.out.println("Пустой undo:    " + sb5);
    }
}