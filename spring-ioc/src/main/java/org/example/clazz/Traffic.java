package org.example.clazz;

import org.example.myenum.Signal;

public class Traffic {
    Signal color = Signal.RED;

    public void change() {
        switch (color) {
            case RED:color = Signal.YELLOW;
            break;
            case BLUE: color = Signal.RED;
            break;
            case YELLOW: color = Signal.BLUE;
            break;
        }
    }

    @Override
    public String toString() {
        return "The light is : " + color;
    }

    public static void main(String[] args) {
        Traffic traffic = new Traffic();
        for (int i = 0; i < 7; i++) {
            System.out.println(traffic);
            traffic.change();
        }
    }
}

