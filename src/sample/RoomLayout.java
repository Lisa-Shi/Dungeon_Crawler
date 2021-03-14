package sample;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.LinkedList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RoomLayout {
    private Room room;
    private int width;
    private int height;
//    private LinkedList<ExitTile> exits;
//    private LinkedList<WallTile> layout;

    public RoomLayout(Room room) {
        this.room = room;
        this.width = room.getWidth();
        this.height = room.getHeight();
//        this.exits = exits;
//        this.layout = layout;
    }

    public static void designH1(Room room) {
        /*
        design
            ::          ::

            ::    :::   ::

            ::          ::
         */
        int width = room.getWidth();
        int height = room.getHeight();
        centerSquare(room, width / 2, height / 2, 4 , 4);

        //left
        for (int i = 1; i < 6; i += 2) {
            centerSquare(room, width / 6, i * height / 6, 3, 2);
        }

        //right
        for (int i = 1; i < 6; i += 2) {
            centerSquare(room, 5 * width / 6, i * height / 6, 3, 2);
        }
    }

    public static void designStar2(Room room) {
        /*
        design
               :::    :::
            :::    :::   :::
               :::    :::

         */
        int width = room.getWidth();
        int height = room.getHeight();
        for (int i = 1; i < 6; i += 1) {
            int odd = i % 2;
            int even = (i + 1) % 2;
            //odd columns, or middle row
            centerSquare(room, i * width / 6, height / 2, 3 * odd , 3);

            //even columns, or top row
            centerSquare(room, i * width / 6, height / 4, 3 * (even) , 3);

            //even columns, or bottom row
            centerSquare(room, i * width / 6, 3 * height / 4, 3 * (even) , 3);
        }
    }

    public static void designV3(Room room) {
        /*
        design
        ::          ::
           ::    ::
              ::
         */

        int width = room.getWidth();
        int height = room.getHeight();
        //middle
        for (int i = 1; i < 4; i += 1) {
            //squares in odd columns
            centerSquare(room, i * width / 6, i * height / 4, 2, 2);
            centerSquare(room, width - i * width / 6, i * height / 4, 2, 2);
        }
    }

    public static void designHex4(Room room) {
        /*
        design
                 3::1  4::1
              2::2        5::2
           1::3              6::3
              2::4        5::4
                 3::5  4::5
         */
        int width = room.getWidth();
        int height = room.getHeight();
        int countx = 3;
        for (int i = 1; i <= 5; i++) {
            int x = 7 - Math.abs(countx);
            centerSquare(room, Math.abs(countx) * width / 7, (i) * height / 6, 2, 2);
            centerSquare(room, x * width / 7, (i) * height / 6, 2, 2);
            countx--;
            if (countx == 0) {
                countx = -2;
            }
        }
    }

    public static void centerSquare(Room room, int x, int y, int width, int height) {
        rightQuad(room, x - width / 2, y - height / 2, width, height);
    }

    public static void rightQuad(Room room, int x, int y, int width, int length) {
        for (int i = 0; i < length; i++) {
            flatBar(room, x, y + i, width);
        }
    }

    private static void flatBar(Room room, int x, int y, int width) {
        for (int i = 0; i < width; i++) {
            room.add(new WallTile(room, x + i, y));
        }
    }

    private static void verticalBar(Room room, int x, int y, int length) {
        for (int i = 0; i < length; i++) {
            room.add(new WallTile(room, x, y + i));
        }
    }


}
