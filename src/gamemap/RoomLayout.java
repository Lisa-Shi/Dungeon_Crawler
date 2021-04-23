package gamemap;

import gameobjects.tiles.WallTile;

public class RoomLayout {

    /**
     * Decides which design to use based on room id.
     *
     * @param room to design
     * @return the name of the design
     */
    public static String design(Room room) {
        int designID = room.getRoomId() % 4;
        String methodName = "design" + designID;

        if (designID == 0) {
            design0(room);
            return methodName;
        } else if (designID == 1) {
            design1(room);
            return methodName;
        } else if (designID == 2) {
            design2(room);
            return methodName;
        } else if (designID == 3) {
            design3(room);
            return methodName;
        } else {
            return null;
        }
    }
    public static String design(Room room, boolean isChallenge){
        if(isChallenge){
            design3(room);
            return "design3";
        }
        return null;
    }
    /**
     * Design 0 in the shape of an H.
     * design:
     *          ::          ::
     *                :::
     *          ::    :::   ::
     *                :::
     *          ::          ::
     *
     * @param room to add layout
     */
    private static void design0(Room room) {
        int width = room.getWidth();
        int height = room.getHeight();
        centerQuad(room, width / 2, height / 2, 4, 4);

        //left
        for (int i = 1; i < 6; i += 2) {
            centerQuad(room, width / 6, i * height / 6, 3, 2);
        }

        //right
        for (int i = 1; i < 6; i += 2) {
            centerQuad(room, 5 * width / 6, i * height / 6, 3, 2);
        }
    }

    /**
     * Design 1 in the sape of a star.
     * design
     *            :::    :::
     *         :::    :::   :::
     *            :::    :::
     *
     * @param room to add layout
     */
    private static void design1(Room room) {
        int width = room.getWidth();
        int height = room.getHeight();
        for (int i = 1; i < 6; i += 1) {
            int odd = i % 2;
            int even = (i + 1) % 2;
            //odd columns, or middle row
            centerQuad(room, i * width / 6, height / 2, 3 * odd, 3);

            //even columns, or top row
            centerQuad(room, i * width / 6, height / 4, 3 * (even), 3);

            //even columns, or bottom row
            centerQuad(room, i * width / 6, 3 * height / 4, 3 * (even), 3);
        }
    }

    /**
     * Design 2 in  the shape of a V.
     *
     *         ::          ::
     *            ::    ::
     *               ::
     * @param room to add layout
     */
    private static void design2(Room room) {
        int width = room.getWidth();
        int height = room.getHeight();
        //middle
        for (int i = 1; i < 4; i += 1) {
            //squares in odd columns
            centerQuad(room, i * width / 6, i * height / 4, 2, 2);
            centerQuad(room, width - i * width / 6, i * height / 4, 2, 2);
        }
    }

    /**
     * Design 3 in the shape of < >
     * x::y
     * x - denotes x / 7
     *     the x-coordinate of the block's center
     *
     * y - denotes y / 6
     *     the y-coordinates of the block's center
     *
     *                3::1  4::1
     *             2::2        5::2
     *          1::3              6::3
     *             2::4        5::4
     *                3::5  4::5
     * @param room to add layout
     */
    private static void design3(Room room) {
        int width = room.getWidth();
        int height = room.getHeight();
        int countx = 3;
        for (int i = 1; i <= 5; i++) {
            int x = 7 - Math.abs(countx);
            centerQuad(room, Math.abs(countx) * width / 7, (i) * height / 6, 2, 2);
            centerQuad(room, x * width / 7, (i) * height / 6, 2, 2);
            countx--;
            if (countx == 0) {
                countx = -2;
            }
        }
    }

    /**
     * Adding a quadrilateral at the center of the given coordinate
     * @param room to design
     * @param x coordinate of center
     * @param y coordinate of center
     * @param width of block
     * @param height of block
     */
    private static void centerQuad(Room room, int x, int y, int width, int height) {
        quad(room, x - width / 2, y - height / 2, width, height);
    }

    /**
     * Adding a quadrilateral with a given top left vertex
     * @param room to design
     * @param x coordinate of left vertex
     * @param y coordinate of right vertex
     * @param width of block
     * @param height of block
     */
    private static void quad(Room room, int x, int y, int width, int height) {
        for (int i = 0; i < height; i++) {
            flatBar(room, x, y + i, width);
        }
    }

    /**
     * Adds a flat bar
     * shape:
     *    ....
     * @param room to design
     * @param x coordinate left most
     * @param y coordinate of block
     * @param width of block
     */
    private static void flatBar(Room room, int x, int y, int width) {
        for (int i = 0; i < width; i++) {
            room.add(new WallTile(room, x + i, y));
        }
    }

    /**
     * Adds a vertical bar
     * shape:
     *    :
     *    :
     * @param room to design
     * @param x coordinate left most
     * @param y coordinate of block
     * @param length of block
     */
    private static void verticalBar(Room room, int x, int y, int length) {
        for (int i = 0; i < length; i++) {
            room.add(new WallTile(room, x, y + i));
        }
    }

}
