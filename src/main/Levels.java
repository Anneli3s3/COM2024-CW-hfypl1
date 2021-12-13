package main;

import parts.Brick;

import java.awt.*;


public class Levels extends Wall {

    private static final int LEVELS_COUNT = 9;

    public Levels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {
        super(drawArea, brickCount, lineCount, brickDimensionRatio, ballPos);
    }

    /**
     * makeGameLevel will create the look of the game level
     * @param drawArea dimension of the brick
     * @param brickCnt stores the brick count
     * @param lineCnt stores the line count
     * @param brickSizeRatio ratio of brick size
     * @param typeA brick type A
     * @param typeB brick type B
     * @return the brick
     */
    private static Brick[] makeGameLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */

        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  Wall.makeBrick(p,brickSize,typeA) : Wall.makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = Wall.makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * makeLevels make the playable levels in the game
     * @param drawArea dimension of brick
     * @param brickCount stores the brick count
     * @param lineCount stores line count
     * @param brickDimensionRatio the ratio of the brick's dimension
     * @return array of bricks forming the brick wall
     */
    public static Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY, CLAY);
        tmp[1] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,CEMENT);
        tmp[5] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL, TITANIUM);
        tmp[6] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT, TITANIUM);
        tmp[7] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio, DIAMOND, CEMENT);
        tmp[8] = makeGameLevel(drawArea,brickCount,lineCount,brickDimensionRatio, DIAMOND, DIAMOND);
        return tmp;
    }


}