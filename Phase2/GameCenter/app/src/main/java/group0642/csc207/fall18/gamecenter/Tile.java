package group0642.csc207.fall18.gamecenter;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    private static final int BLANK = R.drawable.tile_25;

    private static final int[] tileImg = {BLANK, R.drawable.tile_1, R.drawable.tile_2,
            R.drawable.tile_3, R.drawable.tile_4, R.drawable.tile_5, R.drawable.tile_6,
            R.drawable.tile_7, R.drawable.tile_8, R.drawable.tile_9, R.drawable.tile_10,
            R.drawable.tile_11, R.drawable.tile_12, R.drawable.tile_13, R.drawable.tile_14,
            R.drawable.tile_15, R.drawable.tile_16, R.drawable.tile_17, R.drawable.tile_18,
            R.drawable.tile_19, R.drawable.tile_20, R.drawable.tile_21, R.drawable.tile_22,
            R.drawable.tile_23, R.drawable.tile_24, BLANK};

    private static final int[] flowerImg = {BLANK, R.drawable.ftile_1, R.drawable.ftile_2,
            R.drawable.ftile_3, R.drawable.ftile_4, R.drawable.ftile_5, R.drawable.ftile_6,
            R.drawable.ftile_7, R.drawable.ftile_8, R.drawable.ftile_9, R.drawable.ftile_10,
            R.drawable.ftile_11, R.drawable.ftile_12, R.drawable.ftile_13, R.drawable.ftile_14,
            R.drawable.ftile_15, R.drawable.ftile_16, R.drawable.ftile_17, R.drawable.ftile_18,
            R.drawable.ftile_19, R.drawable.ftile_120, R.drawable.ftile_21, R.drawable.ftile_22,
            R.drawable.ftile_23, R.drawable.ftile_24, BLANK};

    private static final int[] deathwingImg = {BLANK, R.drawable.deathwingtile_1, R.drawable.deathwingtile_2,
            R.drawable.deathwingtile_3, R.drawable.deathwingtile_4, R.drawable.deathwingtile_5, R.drawable.deathwingtile_6,
            R.drawable.deathwingtile_7, R.drawable.deathwingtile_8, R.drawable.deathwingtile_9, R.drawable.deathwingtile_10,
            R.drawable.deathwingtile_11, R.drawable.deathwingtile_12, R.drawable.deathwingtile_13, R.drawable.deathwingtile_14,
            R.drawable.deathwingtile_15, R.drawable.deathwingtile_16, R.drawable.deathwingtile_17, R.drawable.deathwingtile_18,
            R.drawable.deathwingtile_19, R.drawable.deathwingtile_20, R.drawable.deathwingtile_21, R.drawable.deathwingtile_22,
            R.drawable.deathwingtile_23, R.drawable.deathwingtile_24, BLANK};

    private static final int[] illidanImg = {BLANK, R.drawable.illidantile_1, R.drawable.illidantile_2,
            R.drawable.illidantile_3, R.drawable.illidantile_4, R.drawable.illidantile_5, R.drawable.illidantile_6,
            R.drawable.illidantile_7, R.drawable.illidantile_8, R.drawable.illidantile_9, R.drawable.illidantile_10,
            R.drawable.illidantile_11, R.drawable.illidantile_12, R.drawable.illidantile_13, R.drawable.illidantile_14,
            R.drawable.illidantile_15, R.drawable.illidantile_16, R.drawable.illidantile_17, R.drawable.illidantile_18,
            R.drawable.illidantile_19, R.drawable.illidantile_20, R.drawable.illidantile_21, R.drawable.illidantile_22,
            R.drawable.illidantile_23, R.drawable.illidantile_24, BLANK};

    private static final int[] jainaImg = {BLANK, R.drawable.jainatile_1, R.drawable.jainatile_2,
            R.drawable.jainatile_3, R.drawable.jainatile_4, R.drawable.jainatile_5, R.drawable.jainatile_6,
            R.drawable.jainatile_7, R.drawable.jainatile_8, R.drawable.jainatile_9, R.drawable.jainatile_10,
            R.drawable.jainatile_11, R.drawable.jainatile_12, R.drawable.jainatile_13, R.drawable.jainatile_14,
            R.drawable.jainatile_15, R.drawable.jainatile_16, R.drawable.jainatile_17, R.drawable.jainatile_18,
            R.drawable.jainatile_19, R.drawable.jainatile_20, R.drawable.jainatile_21, R.drawable.jainatile_22,
            R.drawable.jainatile_23, R.drawable.jainatile_24, BLANK};

    private static final int[] leaderImg = {BLANK, R.drawable.leadertile_1, R.drawable.leadertile_2,
            R.drawable.leadertile_3, R.drawable.leadertile_4, R.drawable.leadertile_5, R.drawable.leadertile_6,
            R.drawable.leadertile_7, R.drawable.leadertile_8, R.drawable.leadertile_9, R.drawable.leadertile_10,
            R.drawable.leadertile_11, R.drawable.leadertile_12, R.drawable.leadertile_13, R.drawable.leadertile_14,
            R.drawable.leadertile_15, R.drawable.leadertile_16, R.drawable.leadertile_17, R.drawable.leadertile_18,
            R.drawable.leadertile_19, R.drawable.leadertile_20, R.drawable.leadertile_21, R.drawable.leadertile_22,
            R.drawable.leadertile_23, R.drawable.leadertile_24, BLANK};

    private static final int[] malfurionImg = {BLANK, R.drawable.malfuriontile_1, R.drawable.malfuriontile_2,
            R.drawable.malfuriontile_3, R.drawable.malfuriontile_4, R.drawable.malfuriontile_5, R.drawable.malfuriontile_6,
            R.drawable.malfuriontile_7, R.drawable.malfuriontile_8, R.drawable.malfuriontile_9, R.drawable.malfuriontile_10,
            R.drawable.malfuriontile_11, R.drawable.malfuriontile_12, R.drawable.malfuriontile_13, R.drawable.malfuriontile_14,
            R.drawable.malfuriontile_15, R.drawable.malfuriontile_16, R.drawable.malfuriontile_17, R.drawable.malfuriontile_18,
            R.drawable.malfuriontile_19, R.drawable.malfuriontile_20, R.drawable.malfuriontile_21, R.drawable.malfuriontile_22,
            R.drawable.malfuriontile_23, R.drawable.malfuriontile_24, BLANK};

    private static final int[] medivhImg = {BLANK, R.drawable.medivhtile_1, R.drawable.medivhtile_2,
            R.drawable.medivhtile_3, R.drawable.medivhtile_4, R.drawable.medivhtile_5, R.drawable.medivhtile_6,
            R.drawable.medivhtile_7, R.drawable.medivhtile_8, R.drawable.medivhtile_9, R.drawable.medivhtile_10,
            R.drawable.medivhtile_11, R.drawable.medivhtile_12, R.drawable.medivhtile_13, R.drawable.medivhtile_14,
            R.drawable.medivhtile_15, R.drawable.medivhtile_16, R.drawable.medivhtile_17, R.drawable.medivhtile_18,
            R.drawable.medivhtile_19, R.drawable.medivhtile_20, R.drawable.medivhtile_21, R.drawable.medivhtile_22,
            R.drawable.medivhtile_23, R.drawable.medivhtile_24, BLANK};

    private static final int[] thrallImg = {BLANK, R.drawable.thralltile_1, R.drawable.thralltile_2,
            R.drawable.thralltile_3, R.drawable.thralltile_4, R.drawable.thralltile_5, R.drawable.thralltile_6,
            R.drawable.thralltile_7, R.drawable.thralltile_8, R.drawable.thralltile_9, R.drawable.thralltile_10,
            R.drawable.thralltile_11, R.drawable.thralltile_12, R.drawable.thralltile_13, R.drawable.thralltile_14,
            R.drawable.thralltile_15, R.drawable.thralltile_16, R.drawable.thralltile_17, R.drawable.thralltile_18,
            R.drawable.thralltile_19, R.drawable.thralltile_20, R.drawable.thralltile_21, R.drawable.thralltile_22,
            R.drawable.thralltile_23, R.drawable.thralltile_24, BLANK};

    private static final int[] tyrandeImg = {BLANK, R.drawable.tyrandetile_1, R.drawable.tyrandetile_2,
            R.drawable.tyrandetile_3, R.drawable.tyrandetile_4, R.drawable.tyrandetile_5, R.drawable.tyrandetile_6,
            R.drawable.tyrandetile_7, R.drawable.tyrandetile_8, R.drawable.tyrandetile_9, R.drawable.tyrandetile_10,
            R.drawable.tyrandetile_11, R.drawable.tyrandetile_12, R.drawable.tyrandetile_13, R.drawable.tyrandetile_14,
            R.drawable.tyrandetile_15, R.drawable.tyrandetile_16, R.drawable.tyrandetile_17, R.drawable.tyrandetile_18,
            R.drawable.tyrandetile_19, R.drawable.tyrandetile_20, R.drawable.tyrandetile_21, R.drawable.tyrandetile_22,
            R.drawable.tyrandetile_23, R.drawable.tyrandetile_24, BLANK};

    private static final int[] velenImg = {BLANK, R.drawable.velentile_1, R.drawable.velentile_2,
            R.drawable.velentile_3, R.drawable.velentile_4, R.drawable.velentile_5, R.drawable.velentile_6,
            R.drawable.velentile_7, R.drawable.velentile_8, R.drawable.velentile_9, R.drawable.velentile_10,
            R.drawable.velentile_11, R.drawable.velentile_12, R.drawable.velentile_13, R.drawable.velentile_14,
            R.drawable.velentile_15, R.drawable.velentile_16, R.drawable.velentile_17, R.drawable.velentile_18,
            R.drawable.velentile_19, R.drawable.velentile_20, R.drawable.velentile_21, R.drawable.velentile_22,
            R.drawable.velentile_23, R.drawable.velentile_24, BLANK};

    private static final int[] arthasImg = {BLANK, R.drawable.arthastile_1, R.drawable.arthastile_2,
            R.drawable.arthastile_3, R.drawable.arthastile_4, R.drawable.arthastile_5, R.drawable.arthastile_6,
            R.drawable.arthastile_7, R.drawable.arthastile_8, R.drawable.arthastile_9, R.drawable.arthastile_10,
            R.drawable.arthastile_11, R.drawable.arthastile_12, R.drawable.arthastile_13, R.drawable.arthastile_14,
            R.drawable.arthastile_15, R.drawable.arthastile_16, R.drawable.arthastile_17, R.drawable.arthastile_18,
            R.drawable.arthastile_19, R.drawable.arthastile_20, R.drawable.arthastile_21, R.drawable.arthastile_22,
            R.drawable.arthastile_23, R.drawable.arthastile_24, BLANK};

    private static final int[] carImg = {BLANK, R.drawable.cartile_1, R.drawable.cartile_2,
            R.drawable.cartile_3, R.drawable.cartile_4, R.drawable.cartile_5, R.drawable.cartile_6,
            R.drawable.cartile_7, R.drawable.cartile_8, R.drawable.cartile_9, R.drawable.cartile_10,
            R.drawable.cartile_11, R.drawable.cartile_12, R.drawable.cartile_13, R.drawable.cartile_14,
            R.drawable.cartile_15, R.drawable.cartile_16, R.drawable.cartile_17, R.drawable.cartile_18,
            R.drawable.cartile_19, R.drawable.cartile_20, R.drawable.cartile_21, R.drawable.cartile_22,
            R.drawable.cartile_23, R.drawable.cartile_24, BLANK};

    private static final int[] elfImg = {BLANK, R.drawable.elftile_1, R.drawable.elftile_2,
            R.drawable.elftile_3, R.drawable.elftile_4, R.drawable.elftile_5, R.drawable.elftile_6,
            R.drawable.elftile_7, R.drawable.elftile_8, R.drawable.elftile_9, R.drawable.elffftile_10,
            R.drawable.elftile_11, R.drawable.elftile_12, R.drawable.elftile_13, R.drawable.elftile_14,
            R.drawable.elftile_15, R.drawable.elftile_16, R.drawable.elftile_17, R.drawable.elftile_18,
            R.drawable.elffftile_19, R.drawable.elftile_20, R.drawable.elftile_21, R.drawable.elftile_22,
            R.drawable.elftile_23, R.drawable.elftile_24, BLANK};

    private static final int[][] imagesArray = {tileImg, flowerImg, deathwingImg, illidanImg, jainaImg,
            leaderImg, malfurionImg, medivhImg, thrallImg, tyrandeImg, velenImg, arthasImg, carImg, elfImg};

    private static int[] images;

    static int bg;

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    static void setImages(int n) {
        if (n < 0)
            n = 0;
        if (n > 13)
            n = 13;
        Tile.images = imagesArray[n];
        Tile.bg = n;
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    Tile(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the id representing background
     */
    Tile(int backgroundId) {
        id = backgroundId + 1;
        int fakeId = id;
        // Auto-adjusting
        if (Tile.bg > 0) {
            if (Board.NUM_ROWS == 3){
                fakeId += ((int) (((id - 1)/ 3))) * 2;
            }
            if (Board.NUM_ROWS == 4){
                fakeId += ((int) (((id - 1)/ 4)));
            }
        }
        // Back to normal
        background = images[fakeId];
        if (id == Board.NUM_COLS * Board.NUM_ROWS)
            background = images[0];
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
